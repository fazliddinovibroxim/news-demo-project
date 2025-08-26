package com.example.news_project.service;

import com.example.news_project.entity.Attachment;
import com.example.news_project.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService {

//    private final Path uploadDirectory = Paths.get("src/main/resources/uploadDirectory").toAbsolutePath().normalize();

    private final AttachmentRepository attachmentRepository;
    @Value("${uploadDirectory}")
    private String uploadDirectory;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Resource download(String fileName) throws Exception {
        try {
            Path filePath = Paths.get(uploadDirectory).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found: " + fileName);
            }
        } catch (Exception ex) {
            throw new Exception("File not found: " + fileName, ex);
        }
    }

    public List<Attachment> upload(MultipartFile[] files) throws IOException {
        List<Attachment> attachments = new ArrayList<>();

        for (MultipartFile file : files) {
            // Save file to server
            String fileName = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = fileName + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'));

            Path filePath = Paths.get(uploadDirectory).resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save file metadata in database
            Attachment media = new Attachment();
            media.setServerName(fileName);
            media.setContentType(file.getContentType());// Associate file with product
            media.setSize(file.getSize());
            media.setFileUrl("/api/attachment/download/"+fileName);
            attachments.add(media);
        }
        return attachments;
    }

    public void deleteById(String id) {
        attachmentRepository.deleteById(UUID.fromString(id));
    }
}