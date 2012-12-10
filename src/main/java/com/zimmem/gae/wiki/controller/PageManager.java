package com.zimmem.gae.wiki.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petebevin.markdown.MarkdownProcessor;
import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.service.WikiPageService;

@Controller
public class PageManager {

    @Autowired
    private WikiPageService wikpagePageService;

    @ResponseBody
    @RequestMapping(value = "/post_page", method = RequestMethod.POST)
    public WikiPage postPage(WikiPage wikiPage) throws IOException {
        MarkdownProcessor process = new MarkdownProcessor();
        wikiPage.setHtml(process.markdown(wikiPage.getWiki()));
        wikpagePageService.saveWikiPage(wikiPage);
        return wikiPage;
    }

    @RequestMapping(value = "/edit_page", method = RequestMethod.GET)
    public String editPage(Map<String, Object> model, @RequestParam("id") long id) {
        WikiPage page = wikpagePageService.findWikiPage(id);
        model.put("wikiPage", page);
        return "/pageForm";
    }

    @RequestMapping(value = "/create_page", method = RequestMethod.GET)
    public String createPage(Map<String, Object> model,
                             @RequestParam(value = "parentId", required = false, defaultValue = "0") long parentId) {
        WikiPage page = new WikiPage();
        page.setParentId(parentId);
        model.put("wikiPage", page);
        return "/pageForm";
    }

    private String parseFirstLine(String text) {
        int index = text.indexOf("\r\n");
        if (index < 0) {
            index = text.indexOf("\n");
        }

        if (index < 0) {
            return text;
        }
        return text.substring(0, index);

    }

    public static void main(String[] args) {
        String text = "\n\n    sdfsf\n    sdfsdf\n\n";
        Pattern pattern = Pattern.compile("(?:\\n\\n|\\A)((?:(?:[ ]{4}).*\\n+)+)((?=^[ ]{0,4}\\S)|\\Z)", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);
       if(matcher.find()){
        System.out.println(matcher.group());
       }
        MarkdownProcessor processor = new MarkdownProcessor();
        String markdown = processor.markdown("\r\n\n    sdfsf\n    sdfsdf\n\n");
        System.out.println(markdown);
    }

}
