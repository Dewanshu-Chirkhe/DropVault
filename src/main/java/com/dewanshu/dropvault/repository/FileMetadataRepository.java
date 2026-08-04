package com.dewanshu.dropvault.repository;

import com.dewanshu.dropvault.entity.FileMetadata;
import com.dewanshu.dropvault.entity.enums.UploadStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, UUID> {

    Optional<FileMetadata> findByDownloadCode(String downloadCode);

    Optional<FileMetadata> findByDownloadCodeAndStatus(String downloadCode, UploadStatus status);

    boolean existsByDownloadCode(String downloadCode);

    List<FileMetadata> findByExpiresAtBefore(LocalDateTime time);

    List<FileMetadata> findByOwnerIdOrderByCreatedAtDesc(UUID ownerId);
}