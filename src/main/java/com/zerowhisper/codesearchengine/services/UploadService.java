package com.zerowhisper.codesearchengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

//Interacting with uploading utilities
@Service
@RequiredArgsConstructor
public class UploadService {

    private final Path uploadDir = Paths.get("uploads");


    public String unzipAndSave(MultipartFile file) {
        Path tempZipPath = uploadDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Save the zip file temporarily
            Files.copy(file.getInputStream(), tempZipPath, StandardCopyOption.REPLACE_EXISTING);

            // Extract the zip file
            try (ZipInputStream zis = new ZipInputStream(file.getInputStream())) {
                ZipEntry zipEntry;
                while ((zipEntry = zis.getNextEntry()) != null) {
                    Path extractedPath = uploadDir.resolve(zipEntry.getName());

                    if (zipEntry.isDirectory()) {
                        Files.createDirectories(extractedPath);
                    } else {
                        Files.createDirectories(extractedPath.getParent()); // Ensure parent directories exist
                        Files.copy(zis, extractedPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }

            // Clean up temporary zip file
            Files.deleteIfExists(tempZipPath);

            return "File uploaded and extracted successfully.\nFile Path: " + tempZipPath;
        } catch (IOException e) {
//            e.printStackTrace();
            return "An error occurred while processing the file.";
        }

    }
}
