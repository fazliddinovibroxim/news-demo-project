package com.example.news_project.dto.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseAttachmentDto {
    private String serverName;
    private String contentType;
    private long size;
    private String fileUrl;
    private UUID ownerId;
}