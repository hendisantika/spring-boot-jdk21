package id.my.hendisantika.springbootjdk21.controller;

import id.my.hendisantika.springbootjdk21.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jdk21
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 25/08/25
 * Time: 04.33
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private FileStorageService fileStorageService;

    @PostMapping("/upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String filename = fileStorageService.saveFile(file, "image");
            return ResponseEntity.ok("Image uploaded successfully: " + filename);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload image: " + e.getMessage());
        }
    }

    @PostMapping("/upload/pdf")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            String filename = fileStorageService.saveFile(file, "pdf");
            return ResponseEntity.ok("PDF uploaded successfully: " + filename);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload PDF: " + e.getMessage());
        }
    }

    @GetMapping("/download/image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            byte[] fileData = fileStorageService.getFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(fileData);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/download/pdf/{filename}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String filename) {
        try {
            byte[] fileData = fileStorageService.getFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(fileData);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
