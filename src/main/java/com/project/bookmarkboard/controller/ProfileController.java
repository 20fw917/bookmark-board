package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.CustomUserDetails;
import com.project.bookmarkboard.dto.User;
import com.project.bookmarkboard.mapper.UserMapper;
import com.project.bookmarkboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/update")
    public String getProfileUpdatePage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        final User user = userMapper.findByInternalId(customUserDetails.getUserInternalId());
        user.setPassword(null);

        model.addAttribute("toModifyUser", user);

        return "profile/update";
    }

    @PostMapping("/update")
    public String postProfileUpdatePage(@AuthenticationPrincipal CustomUserDetails customUserDetails, User user) {
        log.info("User Update Request Received");
        log.info("Received User: " + user);

        if(customUserDetails.getUserInternalId() != user.getInternalId()) {
            log.warn("This Request isn't valid.");
            return "redirect:/";
        }

        userService.updateUser(user);

        return "redirect:/";
    }
}
