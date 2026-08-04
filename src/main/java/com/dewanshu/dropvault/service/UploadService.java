package com.dewanshu.dropvault.service;

import com.dewanshu.dropvault.entity.Upload;
import com.dewanshu.dropvault.entity.enums.UploadStatus;
import com.dewanshu.dropvault.repository.UploadRepository;
import com.dewanshu.dropvault.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final UploadRepository uploadRepository;
    private final FileStorageService fileStorageService;

    public Upload uploadFile(MultipartFile file) throws IOException {

        String code = generateUniqueCode();

        String extension = "";

        String originalFilename = file.getOriginalFilename();

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String storedFilename = UUID.randomUUID() + extension;

        fileStorageService.saveFile(file, storedFilename);

        Upload upload = Upload.builder()
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

        return uploadRepository.save(upload);
    }

    private String generateUniqueCode() {

        String code;

        do {
            code = CodeGenerator.generateCode();
        } while (uploadRepository.existsByDownloadCode(code));

        return code;
    }
}