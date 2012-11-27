package com.zimmem.gae.wiki.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petebevin.markdown.MarkdownProcessor;

@Controller
public class WikiApi {

    @RequestMapping("/api-wiki/render")
    @ResponseBody
    public String render(@RequestParam("wiki") String wiki) throws  IOException {
      MarkdownProcessor processor = new MarkdownProcessor();
        return processor.markdown(wiki);
    }
}
