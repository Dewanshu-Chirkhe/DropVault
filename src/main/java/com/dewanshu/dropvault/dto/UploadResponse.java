package com.dewanshu.dropvault.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UploadResponse {

    private String code;

    private String downloadUrl;

    private LocalDateTime expiresAt;
}