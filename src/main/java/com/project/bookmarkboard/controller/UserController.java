package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.UserDTO;
import com.project.bookmarkboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String getRegister() {
        return "user/register";
    }

    @PostMapping("/register")
    public String postRegister(UserDTO userDTO, Model model) {
        try {
            userService.insertUserAndReturnInternalId(userDTO.toUser());

            model.addAttribute("message", "회원가입에 성공하였습니다. 입력하신 정보로 로그인해주세요.");
            model.addAttribute("url", "/login");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "회원가입에 실패하였습니다. 입력하신 정보를 다시 확인해주세요.");
            model.addAttribute("url", "/user/register");
        }

        return "user/register_result";
    }

    @PostMapping("/username_check")
    @ResponseBody
    public boolean getUsernameExists(@RequestParam(name = "username") String username) {
        return userService.isUsernameExists(username);
    }

    @PostMapping("/email_check")
    @ResponseBody
    public boolean getEmailExists(@RequestParam(name = "email") String email) {
        return userService.isEmailExists(email);
    }

    @PostMapping("/nickname_check")
    @ResponseBody
    public boolean getNicknameExists(@RequestParam(name = "nickname") String nickname) {
        return userService.isNicknameExists(nickname);
    }
}
