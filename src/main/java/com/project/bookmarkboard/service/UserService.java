package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.User;
import com.project.bookmarkboard.dto.UserRole;
import com.project.bookmarkboard.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public boolean isNicknameExists(String nickname) {
        final int result = userMapper.countByNickname(nickname);
        return result == 0;
    }
    public boolean isUsernameExists(String username) {
        final int result = userMapper.countByUsername(username);
        return result == 0;
    }

    public boolean isEmailExists(String email) {
        final int result = userMapper.countByEmail(email);
        return result == 0;
    }

    public long insertUserAndReturnInternalId(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER.name());
        userMapper.insertUser(user);

        return user.getInternalId();
    }

    public void updateUser(User user) {
        if(user.getPassword() != null) {
            if(!user.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }

        user.setRole(UserRole.USER.name());
        userMapper.updateUser(user);
    }
}
