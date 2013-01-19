package com.zimmem.gae.wiki.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class Page {

    @RequestMapping("/page")
    public RedirectView main(@RequestParam("id") long id, HttpServletRequest request) {
        RedirectView view = new RedirectView(
                                             ServletUriComponentsBuilder.fromContextPath(request).path("/articles/id-{id}").build().expand(id).toUriString());
        view.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return view;

    }

}
