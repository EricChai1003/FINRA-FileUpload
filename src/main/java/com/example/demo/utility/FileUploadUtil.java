package com.example.demo.utility;

import com.example.demo.entity.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUploadUtil {

    public static final String FILE_LOCATION = "upload_files" + File.separator;

    public static FileData convertToFileData(MultipartFile multipartFile) {
        FileData fileData = new FileData();
        fileData.setFileName(multipartFile.getOriginalFilename());
        fileData.setFilePath(new File(FILE_LOCATION).getAbsolutePath()
            + File.separator + multipartFile.getOriginalFilename());
        fileData.setFileSize(multipartFile.getSize());
        return fileData;
    }

    public static void saveUploadedFile(MultipartFile multipartFile,
                                        FileData uploadFile) throws IOException {
        if (!new File(FILE_LOCATION).exists()) {
            new File(FILE_LOCATION).mkdir();
        }
        multipartFile.transferTo(new File(uploadFile.getFilePath()));
    }
}
