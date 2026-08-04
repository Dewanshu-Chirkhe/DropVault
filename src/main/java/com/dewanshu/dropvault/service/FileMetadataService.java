package com.dewanshu.dropvault.service;

import com.dewanshu.dropvault.entity.FileMetadata;
import com.dewanshu.dropvault.entity.enums.UploadStatus;
import com.dewanshu.dropvault.repository.FileMetadataRepository;
import com.dewanshu.dropvault.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileMetadataService {

    private final FileMetadataRepository fileMetadataRepository;
    private final FileStorageService fileStorageService;

    public FileMetadata uploadFile(MultipartFile file) throws IOException {

        String code = generateUniqueCode();

        String extension = "";

        String originalFilename = file.getOriginalFilename();

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String storedFilename = UUID.randomUUID() + extension;

        fileStorageService.saveFile(file, storedFilename);

        FileMetadata fileMetadata = FileMetadata.builder()
                .downloadCode(code)
                .originalFilename(originalFilename)
                .storedFilename(storedFilename)
                .mimeType(file.getContentType())
                .fileSize(file.getSize())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(1))
                .downloadCount(0)
                .status(UploadStatus.ACTIVE)
                .build();

        return fileMetadataRepository.save(fileMetadata);
    }

    private String generateUniqueCode() {

        String code;

        do {
            code = CodeGenerator.generateCode();
        } while (fileMetadataRepository.existsByDownloadCode(code));

        return code;
    }

    public FileMetadata getUpload(String code) {

        FileMetadata fileMetadata = fileMetadataRepository
                .findByDownloadCodeAndStatus(code, UploadStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Invalid download code"));

        if (fileMetadata.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("File has expired");
        }

        fileMetadata.setDownloadCount(fileMetadata.getDownloadCount() + 1);
        fileMetadataRepository.save(fileMetadata);

        return fileMetadata;
    }
}