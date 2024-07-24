package com.example.upload_file.controller;

import com.example.upload_file.message.ResponseMessage;
import com.example.upload_file.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
public class TemplateController {

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping("/")
    public String index(){
        return "uploadfile.html";
    }

    @PostMapping("/web-upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        String message = "";
        try {
            fileUploadService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/upload-status";
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/upload-status";
        }
    }

    @GetMapping("/upload-status")
    public String uploadStatus(){
        return "uploadstatus";
    }

    @GetMapping("/web-preview-file/{filename:.+}")
    public String previewFile(@PathVariable String filename, Model model){
        Resource file = fileUploadService.load(filename);
        String fileAsText = fileUploadService.loadAsText(filename);
        String fileType = fileUploadService.checkFileType(filename);

        model.addAttribute("fileName", file.getFilename());
        if(Objects.equals(fileType, "image/jpeg") || Objects.equals(fileType, "image/png")){
            model.addAttribute("fileContent", file);
        } else {
            model.addAttribute("fileContentAsText", fileAsText);
        }


        return "previewfile";
    }
}
