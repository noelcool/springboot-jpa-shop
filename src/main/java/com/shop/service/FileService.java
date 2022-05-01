package com.shop.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath,
                             String oriFileName,
                             byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID();
        String extension = oriFileName.substring(oriFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String uploadUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(uploadUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제했습니다");
        } else log.info("파일이 존재하지 않습니다");
    }

}
