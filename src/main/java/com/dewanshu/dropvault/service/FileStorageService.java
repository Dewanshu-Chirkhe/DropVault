package com.dewanshu.dropvault.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {

    private static final Path STORAGE_PATH = Paths.get("storage");

    public String saveFile(MultipartFile file, String storedFilename) throws IOException {

        if (!Files.exists(STORAGE_PATH)) {
            Files.createDirectories(STORAGE_PATH);
        }

        Path destination = STORAGE_PATH.resolve(storedFilename);

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return storedFilename;
    }
}