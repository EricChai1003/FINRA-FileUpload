package com.example.demo.dao;

import com.example.demo.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadDAO extends JpaRepository<FileData, Integer> {
    FileData findByFileName(String fileName);
}
