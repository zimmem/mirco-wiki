package com.zimmem.gae.wiki.controller;

import java.io.IOException;
import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KeepAlive {

    @RequestMapping("/keep_alive")
    public void keep(Writer writer) throws InterruptedException {
        try {
            Thread.sleep(600000);
            writer.write(String.valueOf(System.currentTimeMillis()));
        } catch (IOException e) {
        }
    }
}
