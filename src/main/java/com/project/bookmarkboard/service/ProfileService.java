package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.User;
import com.project.bookmarkboard.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProfileService {
    private final AttachmentService attachmentService;
    private final UserMapper userMapper;

    public void uploadProfileImage(MultipartFile multipartFile, User user) throws IOException {
        if(user.getProfileImage() != null) {
            attachmentService.deleteImage(user.getProfileImage());
            user.setProfileImage(null);
        }

        if(multipartFile.getSize() != 0) {
            final String renamedFileName = attachmentService.saveFile(multipartFile, "profile");
            user.setProfileImage(renamedFileName);
        }

        userMapper.updateProfileImageByInternalId(user);
    }

    public void deleteProfileImage(User user) {
        if(user.getProfileImage() != null) {
            attachmentService.deleteImage(user.getProfileImage());
            user.setProfileImage(null);
        }

        if(user.getProfileImage() != null && user.getProfileImage().equals("")) {
            user.setProfileImage(null);
        }

        userMapper.updateProfileImageByInternalId(user);
    }
}
