package com.example.demo.service;

import com.example.demo.dao.FileUploadDAO;
import com.example.demo.entity.FileData;
import com.example.demo.utility.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final Path baseLoc = Paths.get(FileUploadUtil.FILE_LOCATION);

    @Autowired
    private FileUploadDAO fileUploadDAO;

    @Override
    public FileData save(MultipartFile uploadFile) {
        FileData file = FileUploadUtil.convertToFileData(uploadFile);
        FileData result = fileUploadDAO.save(file);
        try {
            FileUploadUtil.saveUploadedFile(uploadFile, file);
        } catch (IOException e) {
            throw new RuntimeException("Fail to save file", e);
        }
        return result;
    }

    @Override
    public FileData getFile(String fileName) {
        return fileUploadDAO.findByFileName(fileName);
    }

    @Override
    public List<String> loadAll() {
        try {
            List<String> paths = Files.walk(this.baseLoc, 1)
                    .filter(path -> !path.equals(this.baseLoc))
                    .map(path -> this.baseLoc.relativize(path))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            return paths;
        } catch(IOException e) {
            throw new RuntimeException("Fail to read stored file", e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(baseLoc.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(baseLoc);
        } catch(IOException e) {
            throw new RuntimeException("Fail to initialize storage", e);
        }
    }
}
