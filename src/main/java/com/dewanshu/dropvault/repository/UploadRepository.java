package com.dewanshu.dropvault.repository;

import com.dewanshu.dropvault.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UploadRepository extends JpaRepository<Upload, UUID> {

    Optional<Upload> findByDownloadCode(String downloadCode);

    boolean existsByDownloadCode(String downloadCode);
}