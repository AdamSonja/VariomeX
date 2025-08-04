package com.variomex.variomex.controller;

import com.variomex.variomex.model.GenomeFile;
import com.variomex.variomex.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/genome")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // ✅ Get ownerId from JWT instead of trusting request param
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String ownerId = authentication.getName(); // usually username or userId

            GenomeFile savedFile = fileUploadService.uploadGenomeFile(file, ownerId);
            return ResponseEntity.ok(savedFile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }
}
