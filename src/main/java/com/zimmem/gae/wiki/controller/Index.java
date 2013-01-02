package com.zimmem.gae.wiki.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zimmem.gae.wiki.repository.WikiPageRepository;

@Controller
public class Index {

    @Autowired
    private WikiPageRepository repository;

    @RequestMapping("/")
    public String index(Map<String, Object> models) {
        Pageable pageable = new PageRequest(0, 20, Direction.DESC, "modifiedTime");
        models.put("wikiPages", repository.listRootWikiPages(pageable));
        return "index";

    }
}
