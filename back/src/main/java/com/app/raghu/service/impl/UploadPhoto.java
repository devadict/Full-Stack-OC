package com.app.raghu.service.impl;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UploadPhoto {
    private final static String HOSTNAME = "localhost";
    private final static String SERVER_PORT = "3001";
    private final static String UPLOAD_FOLDER = "images";

    public static String uploadPicture(MultipartFile multipartFile) {
        try {
            Path uploadPath = Paths.get("src/main/resources/static", UPLOAD_FOLDER).normalize();

            // Create directory if not found
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = multipartFile.getInputStream()) {
                String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());

                Path filePath = uploadPath.resolve(filename);

                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                return getFileUrl(filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getFileUrl(String filename) {
        String url = String.format("http://%s:%s/images/%s", HOSTNAME, SERVER_PORT, filename);
        return UriComponentsBuilder.fromHttpUrl(url).toUriString();
    }
}
