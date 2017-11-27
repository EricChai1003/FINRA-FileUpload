package com.example.demo.controller;

import com.example.demo.entity.FileData;
import com.example.demo.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class FileUploadController {

    @Autowired
    private final FileUploadService uploadService;

    @Autowired
    public FileUploadController(FileUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping("/")
    public String listUploadFiles(Model model) throws IOException {
        model.addAttribute("files", uploadService.loadAll());
        return "uploadForm";
    }

    @PostMapping("/api/upload")
    public String uploadFile(@RequestParam("file")MultipartFile uploadFile,
                             RedirectAttributes redirectAttributes) {
        if (uploadFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No File is Chosen!");
        } else {
            uploadService.save(uploadFile);
            redirectAttributes.addFlashAttribute("message", "File is successfully uploaded.");
        }
        return "redirect:/";
    }

    @GetMapping("api/get")
    public String getFileDetails(@RequestParam("fileName") String fileName,
            RedirectAttributes redirectAttributes) throws FileNotFoundException {
        if (uploadService.getFile(fileName) == null) {
            throw new FileNotFoundException("File not Found!");
        } else {
            FileData file = uploadService.getFile(fileName);
            String[] fileInfoTemp = {
                    "fileID: " + file.getFileId().toString(),
                    "fileName: " + file.getFileName(),
                    "filePath: " + file.getFilePath(),
                    "fileSize: " + file.getFileSize().toString() + " bytes"
            };
            List<String> fileInfo = new ArrayList<>(Arrays.asList(fileInfoTemp));
        }
        return "redict:/";
    }
}
