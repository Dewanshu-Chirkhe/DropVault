package com.dewanshu.dropvault.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyFileResponse {

    private String downloadCode;
    private String originalFilename;
    private Long fileSize;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Integer downloadCount;
}