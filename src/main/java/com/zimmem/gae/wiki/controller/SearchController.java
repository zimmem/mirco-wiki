package com.zimmem.gae.wiki.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.zimmem.gae.wiki.model.AppVar;
import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.repository.AppVarRepository;
import com.zimmem.gae.wiki.repository.WikiPageRepository;

@Controller
public class SearchController {

    private static final String INCREMENT_BUILD_LASTTIME = "increment.build.lasttime";

    private static final Logger log                      = Logger.getLogger(SearchController.class);

    @Autowired
    private WikiPageRepository  wikiPageRepository;

    @Autowired
    private AppVarRepository    appVarRepository;

    @RequestMapping("/_search/fullBuild")
    @ResponseBody
    public String fullBuild() {
        List<WikiPage> allPage = wikiPageRepository.listRootWikiPages();
        for (WikiPage wikiPage : allPage) {
            buildPageToIndex(wikiPage);
        }
        return "success";
    }

    @RequestMapping("/_search/incrementBuild")
    @ResponseBody
    public String incrementBuild() {
        Date now = new Date();
        AppVar var = appVarRepository.findOne(INCREMENT_BUILD_LASTTIME);
        String lasttime = var == null ? "0" : var.getValue();
        List<WikiPage> pages = wikiPageRepository.listLastModifyPages(new Date(Long.parseLong(lasttime)));
        if (pages != null && !pages.isEmpty()) {
            for (WikiPage wikiPage : pages) {
                buildPageToIndex(wikiPage);
            }
            log.info("build " + pages.size() + " articles into document index. ");
            updateLastBuildTime(now, var);
        }

        return "success";
    }

    @RequestMapping("/search")
    public Map<String, Object> search(@RequestParam("q") String qstr) {
        QueryOptions options = QueryOptions.newBuilder().setFieldsToSnippet("content").build();
        Query query = Query.newBuilder().setOptions(options).build(qstr);
        Results<ScoredDocument> documents = getIndex().search(query);
        List<WikiPage> pages = new ArrayList<WikiPage>(documents.getNumberReturned());
        for (ScoredDocument document : documents) {
            WikiPage page = convertFromDocument(document);
            pages.add(page);
        }
        Map<String, Object> models = new HashMap<String, Object>();
        models.put("pages", pages);
        return models;
    }

    private Index getIndex() {
        IndexSpec indexSpec = IndexSpec.newBuilder().setName("article_index").build();
        return SearchServiceFactory.getSearchService().getIndex(indexSpec);
    }

    private WikiPage convertFromDocument(ScoredDocument document) {
        WikiPage page = new WikiPage();
        page.setTitle(document.getOnlyField("title").getText());
        page.setHtml(document.getOnlyField("content").getHTML());
        page.setId(document.getOnlyField("id").getNumber().longValue());
        page.setModifiedTime(document.getOnlyField("modify_date").getDate());
        List<Field> expressions = document.getExpressions();
        page.setWiki(expressions.get(0).getHTML());
        return page;

    }

    private void buildPageToIndex(WikiPage wikiPage) {
        Builder builder = Document.newBuilder().setId("article_" + wikiPage.getId());
        builder.addField(Field.newBuilder().setName("id").setNumber(wikiPage.getId()));
        builder.addField(Field.newBuilder().setName("title").setText(wikiPage.getTitle()));
        builder.addField(Field.newBuilder().setName("creator").setText(wikiPage.getCreater().getNickname()));
        builder.addField(Field.newBuilder().setName("editor").setText(wikiPage.getEditor().getNickname()));
        builder.addField(Field.newBuilder().setName("modify_date").setDate(wikiPage.getModifiedTime()));
        builder.addField(Field.newBuilder().setName("content").setHTML(wikiPage.getHtml()));
        Document document = builder.build();
        getIndex().put(document);
    }

    private void updateLastBuildTime(Date now, AppVar var) {
        if (var == null) {
            var = new AppVar(INCREMENT_BUILD_LASTTIME, String.valueOf(now.getTime()));
        } else {
            var.setValue(String.valueOf(now.getTime()));
        }
        appVarRepository.save(var);
    }
}
