package com.dewanshu.dropvault.service;

import com.dewanshu.dropvault.entity.FileMetadata;
import com.dewanshu.dropvault.entity.User;
import com.dewanshu.dropvault.entity.enums.UploadStatus;
import com.dewanshu.dropvault.repository.FileMetadataRepository;
import com.dewanshu.dropvault.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.dewanshu.dropvault.util.AppConstants;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileMetadataService {

    private final FileMetadataRepository fileMetadataRepository;
    private final FileStorageService fileStorageService;

    public FileMetadata uploadFile(MultipartFile file, User owner) throws IOException {

        String code = generateUniqueCode();

        String extension = "";

        String originalFilename = file.getOriginalFilename();

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String storedFilename = UUID.randomUUID() + extension;

        long maxSize = owner == null
                ? AppConstants.GUEST_MAX_SIZE
                : AppConstants.USER_MAX_SIZE;

        if (file.getSize() > maxSize) {
            throw new RuntimeException("File exceeds allowed size.");
        }

        fileStorageService.saveFile(file, storedFilename);

        FileMetadata fileMetadata = FileMetadata.builder()
                .downloadCode(code)
                .originalFilename(originalFilename)
                .storedFilename(storedFilename)
                .mimeType(file.getContentType())
                .fileSize(file.getSize())
                .createdAt(LocalDateTime.now())
                .expiresAt(owner == null
                        ? LocalDateTime.now().plusHours(AppConstants.GUEST_EXPIRY_HOURS)
                        : LocalDateTime.now().plusDays(AppConstants.USER_EXPIRY_DAYS)
                )
                .downloadCount(0)
                .status(UploadStatus.ACTIVE)
                .owner(owner)
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

    public List<FileMetadata> getUserFiles(User user) {
        return fileMetadataRepository.findByOwnerIdOrderByCreatedAtDesc(user.getId());
    }

    public void deleteFile(String code, User user) throws IOException {

        FileMetadata file = fileMetadataRepository.findByDownloadCode(code)
                .orElseThrow(() -> new RuntimeException("File not found"));

        if (file.getOwner() == null) {
            throw new RuntimeException("Guest uploads cannot be deleted.");
        }

        if (!file.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot delete someone else's file.");
        }

        fileStorageService.deleteFile(file.getStoredFilename());

        fileMetadataRepository.delete(file);
    }
}