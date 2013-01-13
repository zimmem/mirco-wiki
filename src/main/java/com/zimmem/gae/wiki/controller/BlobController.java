package com.zimmem.gae.wiki.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.api.files.LockException;
import com.zimmem.gae.wiki.model.Attachment;
import com.zimmem.gae.wiki.repository.AttachmentRepository;
import com.zimmem.springframework.web.exception.ResourceNotFoundException;

@Controller
public class BlobController {

    private BlobstoreService     blobstoreService     = BlobstoreServiceFactory.getBlobstoreService();
    private FileService          fileService          = FileServiceFactory.getFileService();

    @Autowired
    private AttachmentRepository attachmentRepository = null;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadView(Model model) {
        model.addAttribute("upload", blobstoreService.createUploadUrl("/upload"));
        return "upload";
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Attachment uploud(HttpServletRequest req, @RequestParam("myFile") MultipartFile file)
                                                                                                      throws IOException {
        AppEngineFile gfile = fileService.createNewBlobFile(file.getContentType());
        FileWriteChannel writeChannel = fileService.openWriteChannel(gfile, true);
        OutputStream outputStream = Channels.newOutputStream(writeChannel);
        byte[] bytes = file.getBytes();
        IOUtils.write(bytes, outputStream);
        writeChannel.closeFinally();
        gfile = new AppEngineFile(gfile.getFullPath());
        Attachment attachment = new Attachment();
        BlobKey blobKey = fileService.getBlobKey(gfile);
        attachment.setBlobKey(blobKey == null ? null : blobKey.getKeyString());
        attachment.setContentType(file.getContentType());
        attachment.setGfsName(gfile.getNamePart());
        attachment.setGfsPath(gfile.getFullPath());
        String name = file.getOriginalFilename();
        Attachment old = attachmentRepository.findByName(name);
        if(old != null ){
            name = System.currentTimeMillis()  + name ;
        }
        attachment.setName(name);
        attachment.setCrateTime(new Date());
        attachment = attachmentRepository.save(attachment);
        attachment.setSize((long) bytes.length);
        return attachment;

    }

    @RequestMapping("/blob/{blobkey}/*")
    public void getBlob(@PathVariable("blobkey") String blobKey, HttpServletResponse response) throws IOException {
        blobstoreService.serve(new BlobKey(blobKey), response);
    }

    // @RequestMapping("/file/{path}/*")
    // public void getFile(String path, HttpServletResponse response) throws IOException {
    // AppEngineFile file = new AppEngineFile("/blobstore/" + path);
    // FileReadChannel readChannel = fileService.openReadChannel(file, false);
    // InputStream is = Channels.newInputStream(readChannel);
    // OutputStream out = response.getOutputStream();
    // com.zimmem.gae.wiki.lang.IOUtils.in2out(is, out);
    // }

    // @RequestMapping("/file/_id-{id}/*")
    // public void getFileById(@PathVariable("id") long id, HttpServletResponse response) throws IOException {
    // Attachment attachment = attachmentRepository.findOne(id);
    // writeAttachment(attachment, response);
    // }

    @RequestMapping("/file/{name:.*}")
    public void getFileByName(@PathVariable("name") String name, HttpServletResponse response) throws LockException,
                                                                                              IOException {
        Attachment attachment = attachmentRepository.findByName(name);
        writeAttachment(attachment, response);
    }

    private void writeAttachment(Attachment attachment, HttpServletResponse response) throws LockException, IOException {
        if (attachment == null) {
            throw new ResourceNotFoundException();
        }
        response.setContentType(attachment.getContentType());
        AppEngineFile file = new AppEngineFile(attachment.getGfsPath());
        FileReadChannel readChannel;
        try {
            readChannel = fileService.openReadChannel(file, false);
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException();
        }
        InputStream is = Channels.newInputStream(readChannel);
        OutputStream out = response.getOutputStream();
        com.zimmem.gae.wiki.lang.IOUtils.in2out(is, out);
    }
}
