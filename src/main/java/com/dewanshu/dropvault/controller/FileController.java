package com.dewanshu.dropvault.controller;

import com.dewanshu.dropvault.dto.MyFileResponse;
import com.dewanshu.dropvault.dto.UploadResponse;
import com.dewanshu.dropvault.entity.FileMetadata;
import com.dewanshu.dropvault.entity.User;
import com.dewanshu.dropvault.service.FileMetadataService;
import com.dewanshu.dropvault.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;
    private final FileMetadataService fileMetadataService;

    @PostMapping("/upload")
    public UploadResponse upload(
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) throws IOException {

        User owner = null;

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            owner = (User) authentication.getPrincipal();
        }

        FileMetadata fileMetadata = fileMetadataService.uploadFile(file, owner);

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
                .contentType(MediaType.parseMediaType(fileMetadata.getMimeType()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileMetadata.getOriginalFilename() + "\""
                )
                .body(resource);
    }

    private MediaType resolveContentType(Path path) {
        try {
            String contentType = Files.probeContentType(path);

            if (contentType != null) {
                return MediaType.parseMediaType(contentType);
            }
        } catch (IOException ignored) {
        }

        return MediaType.APPLICATION_OCTET_STREAM;
    }

    @GetMapping("/my")
    public List<MyFileResponse> myFiles(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return fileMetadataService.getUserFiles(user)
                .stream()
                .map(file -> new MyFileResponse(
                        file.getDownloadCode(),
                        file.getOriginalFilename(),
                        file.getFileSize(),
                        file.getCreatedAt(),
                        file.getExpiresAt(),
                        file.getDownloadCount()
                ))
                .toList();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable String code,
            Authentication authentication
    ) throws IOException {

        User user = (User) authentication.getPrincipal();

        fileMetadataService.deleteFile(code, user);

        return ResponseEntity.noContent().build();
    }
}
