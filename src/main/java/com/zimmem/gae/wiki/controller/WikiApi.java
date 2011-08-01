package com.zimmem.gae.wiki.controller;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zimmem.wiki.parse.ParseException;
import com.zimmem.wiki.parse.WikiEngine;

@Controller
public class WikiApi {

    @RequestMapping("/api-wiki/render")
    @ResponseBody
    public String render(@RequestParam("wiki") String wiki) throws ParseException, IOException {
        WikiEngine engine = new WikiEngine();
        StringWriter writer = new StringWriter();
        engine.render(wiki, writer);
        return writer.toString();
    }
}
