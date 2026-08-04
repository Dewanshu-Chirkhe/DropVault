package com.dewanshu.dropvault.service;

import com.dewanshu.dropvault.entity.FileMetadata;
import com.dewanshu.dropvault.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CleanupService {

    private final FileMetadataRepository fileMetadataRepository;
    private final FileStorageService fileStorageService;

    @Scheduled(fixedRate = 60000)
    public void cleanupExpiredFiles() {

        List<FileMetadata> expiredFiles =
                fileMetadataRepository.findByExpiresAtBefore(LocalDateTime.now());

        for (FileMetadata file : expiredFiles) {
            try {

                fileStorageService.deleteFile(file.getStoredFilename());

                fileMetadataRepository.delete(file);

                log.info("Deleted expired file {}", file.getDownloadCode());

            } catch (IOException e) {
                log.error("Failed to delete {}", file.getStoredFilename(), e);
            }
        }
    }
}