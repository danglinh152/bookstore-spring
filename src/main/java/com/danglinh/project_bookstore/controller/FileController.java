package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.service.FileService;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api")
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(MultipartFile file, String folder) throws IOException {
        return ResponseEntity.ok(fileService.fileUpload(file, folder));
    }
}
