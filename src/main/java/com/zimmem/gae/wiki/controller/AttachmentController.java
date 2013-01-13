package com.zimmem.gae.wiki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zimmem.gae.wiki.model.Attachment;
import com.zimmem.gae.wiki.repository.AttachmentRepository;


@Controller
public class AttachmentController {
    
    @Autowired
    private AttachmentRepository attachmentRepository;
    
    @RequestMapping("/attachments")
    public List<Attachment> firstPage(){
        return list(1);
    }
    
    @RequestMapping("/attachments/page-{page}")
    public List<Attachment> list(@PathVariable("page")Integer page){
        page = page == null || page <= 0 ? 0 : page - 1;
        Pageable pageable = new PageRequest(page, 12);
        Page<Attachment> attachmentPage = attachmentRepository.findAll(pageable);
        return attachmentPage.getContent();
    }
    
    @RequestMapping("/attachments/all")
    public String listAll(Model model){
        model.addAttribute(attachmentRepository.findAll());
        return "attachments";
    }
}
