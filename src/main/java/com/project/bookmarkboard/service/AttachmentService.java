package com.project.bookmarkboard.service;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@Log4j2
public class AttachmentService {
    @Value("${attachment.directory-path}")
    private String directoryPath;

    public String saveFile(MultipartFile multipartFile, String type) throws IOException {
        log.info("Attachment Save Process Start");
        Path directory = Paths.get(directoryPath).toAbsolutePath().normalize();
        Files.createDirectories(directory);

        final String originalFileName = Objects.requireNonNull(multipartFile.getOriginalFilename());
        log.debug("originalFileName: " + originalFileName);
        // File 확장자
        final String extension = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
        log.debug("extension: " + extension);
        final UUID uuid = UUID.randomUUID();

        final String fileName = StringUtils.cleanPath(type + "_" + uuid + extension);
        log.debug("MODIFIED File Name: " + fileName);

        Path targetPath = directory.resolve(fileName).normalize();

        // 파일이 이미 존재하는지 확인하여 존재한다면 오류를 발생하고 없다면 저장한다.
        Assert.state(!Files.exists(targetPath), fileName + " File Already exists.");

        BufferedImage thumbnailImage = resizeImage(multipartFile);
        File savedFile = new File(targetPath.toString());
        ImageIO.write(thumbnailImage, "png", savedFile);

        return fileName;
    }

    public void deleteImage(String imageName) {
        Path directory = Paths.get(directoryPath).toAbsolutePath().normalize();
        Path targetPath = directory.resolve(imageName).normalize();
        File file = new File(targetPath.toString());

        if(file.exists()){
            if(file.delete()){
                log.info("The File '" + file + "' deleted successfully.");
            }
        } else{
            log.info("The File '" + file + "' is not exists.");
        }
    }

    private BufferedImage resizeImage(MultipartFile multipartFile) throws IOException {
        InputStream in = multipartFile.getInputStream();
        BufferedImage originalImage = ImageIO.read(in);
        BufferedImage thumbnailImage = Thumbnails.of(originalImage).size(300, 300).asBufferedImage();
        in.close();

        return thumbnailImage;
    }
}
