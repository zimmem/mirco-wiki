package com.zimmem.gae.wiki.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShowRevision {

    @RequestMapping("/show_revision")
    public void show(@RequestParam("pageId") long pageId, @RequestParam("version") int version,
                     Map<String, Object> model) {

    }
}
