package com.example.demo.service;

import com.example.demo.entity.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    void init();
    void deleteAll();

    FileData save(MultipartFile uploadFile);

    FileData getFile(String fileName);

    List<String> loadAll();
}
