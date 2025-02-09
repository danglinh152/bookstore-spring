package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.util.error.IdInvalidException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    @Value("${storage.base-path}")
    private String basePath;

    public String fileUpload(MultipartFile file, String folder) throws IOException {
        String fileName = file.getOriginalFilename();

        // Check for allowed file extensions
        List<String> allowedExtensions = Arrays.asList(".png", ".jpg", ".jpeg", ".gif");
        if (fileName == null || fileName.isEmpty() ||
                allowedExtensions.stream().noneMatch(fileName::endsWith)) {
            throw new IOException("File extension is not allowed or file name is empty");
        }

        // Create the directory path
        File directory = new File(basePath + folder);


        // Check if the directory exists, if not, create it
        if (!directory.exists() && !directory.mkdir()) {
            throw new IOException("Failed to create directory: " + directory.getAbsolutePath());
        } else {
            System.out.println("Directory is created or already exists");
        }

        // Define the path for the file
        Path path = Paths.get(directory.getAbsolutePath(), fileName);

        // Copy the file content to the specified path
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName + " uploaded to " + folder;
    }


}
