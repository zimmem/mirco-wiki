package com.zimmem.gae.wiki.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zimmem.gae.wiki.repository.WikiPageRepository;

@Controller
public class Page {

    @Autowired
    private WikiPageRepository repository;

    @RequestMapping("/page")
    public String main(@RequestParam("id") long id, HttpServletRequest request) {
        return "redirect:"
               + ServletUriComponentsBuilder.fromContextPath(request).path("/articles/id-{id}").build().expand(id).toUriString();
    }

}
