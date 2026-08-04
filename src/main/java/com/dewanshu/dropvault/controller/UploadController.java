package com.dewanshu.dropvault.controller;

import com.dewanshu.dropvault.dto.UploadResponse;
import com.dewanshu.dropvault.entity.FileMetadata;
import com.dewanshu.dropvault.service.FileStorageService;
import com.dewanshu.dropvault.service.FileMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.nio.file.Path;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class UploadController {
    private final FileStorageService fileStorageService;
    private final FileMetadataService fileMetadataService;

    @PostMapping("/upload")
    public UploadResponse upload(@RequestParam("file") MultipartFile file) throws IOException {

        FileMetadata fileMetadata = fileMetadataService.uploadFile(file);

        return new UploadResponse(
                fileMetadata.getDownloadCode(),
                "http://localhost:8080/api/v1/files/download/" + fileMetadata.getDownloadCode(),
                fileMetadata.getExpiresAt()
        );
    }

    @GetMapping("/download/{code}")
    public ResponseEntity<Resource> download(@PathVariable String code) throws Exception {

        FileMetadata fileMetadata = fileMetadataService.getUpload(code);

        Path path = fileStorageService.loadFile(fileMetadata.getStoredFilename());

        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileMetadata.getOriginalFilename() + "\""
                )
                .body(resource);
    }
}