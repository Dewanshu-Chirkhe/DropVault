package com.dewanshu.dropvault.controller;

import com.dewanshu.dropvault.entity.Upload;
import com.dewanshu.dropvault.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) throws IOException {

        Upload upload = uploadService.uploadFile(file);

        return Map.of(
                "code", upload.getDownloadCode(),
                "expiresAt", upload.getExpiresAt()
        );
    }
}