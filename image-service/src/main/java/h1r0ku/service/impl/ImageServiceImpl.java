package h1r0ku.service.impl;


import h1r0ku.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private String bucketName;


    private String imageUrl;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + StringUtils.getFilenameExtension(file.getOriginalFilename());
        ClassPathResource serviceAccount = new ClassPathResource("firebase.json");

        return String.format(imageUrl, fileName);
    }
}
