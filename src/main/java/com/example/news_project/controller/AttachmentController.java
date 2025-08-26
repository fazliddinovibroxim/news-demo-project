package com.example.news_project.controller;

import com.example.news_project.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/media")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;


    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> download(@PathVariable String fileName) {
        try {
            Resource resource = attachmentService.download(fileName);
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("file not found");
        }
    }

    @PreAuthorize("hasAuthority('ADD_NEWS')")
    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> add(
            @RequestPart("files") MultipartFile[] files){
        try {
            return ResponseEntity.status(201).body(attachmentService.upload
                    (files));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("server error");
        }
    }

        @PreAuthorize("hasAuthority('DELETE_NEWS')")
        @DeleteMapping("/{id}")
        public ResponseEntity<?> delete(@PathVariable String id) {
            attachmentService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
}