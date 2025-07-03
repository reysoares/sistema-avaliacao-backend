package com.sistema.avaliacao.service.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplement implements FileService {

    @Override
    public String uploadImagem(String path, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}