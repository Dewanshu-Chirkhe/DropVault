package com.dewanshu.dropvault.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {

    private final Path storagePath;

    public FileStorageService(
            @Value("${storage.location}") String storageLocation
    ) {
        this.storagePath = Paths.get(storageLocation);
    }

    public String saveFile(MultipartFile file, String storedFilename) throws IOException {

        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);
        }

        Path destination = storagePath.resolve(storedFilename);

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return storedFilename;
    }

    public Path loadFile(String storedFilename) {
        return storagePath.resolve(storedFilename).normalize();
    }

    public void deleteFile(String storedFilename) throws IOException {
        Files.deleteIfExists(storagePath.resolve(storedFilename));
    }
}