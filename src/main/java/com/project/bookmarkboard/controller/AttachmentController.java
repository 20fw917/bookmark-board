package com.project.bookmarkboard.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Controller
@RequestMapping("/attachment")
public class AttachmentController {
    @Value("${attachment.directory-path}")
    private String filePathPrefix;

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> downloadAttachment(
            @PathVariable(name = "fileName") String fileName,
            HttpServletRequest request) throws MalformedURLException {

        Path filePath = Paths.get(filePathPrefix + fileName).toAbsolutePath().normalize();
        Resource resource = new UrlResource(filePath.toUri());

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(
                    resource.getFile().getAbsolutePath()
            );
        } catch (IOException ex) {
            log.warn("Could not determine file type.");
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\""
                )
                .body(resource);
    }
}
