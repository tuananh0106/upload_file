package com.example.upload_file;

import com.example.upload_file.service.FileUploadService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UploadFileApplication implements CommandLineRunner {
    @Resource
    private FileUploadService fileUploadService;

    public static void main(String[] args) {
        SpringApplication.run(UploadFileApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fileUploadService.init();
    }
}
