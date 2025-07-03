package com.sistema.avaliacao.service.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadImagem(String path, MultipartFile file) throws IOException;
}