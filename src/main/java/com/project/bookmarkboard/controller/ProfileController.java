package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.CustomUserDetails;
import com.project.bookmarkboard.dto.ProfileImageUpdateRequest;
import com.project.bookmarkboard.dto.User;
import com.project.bookmarkboard.dto.pagination.BookmarkPagination;
import com.project.bookmarkboard.dto.pagination.FolderViewPagination;
import com.project.bookmarkboard.dto.response.BasicResponse;
import com.project.bookmarkboard.dto.response.CommonResponse;
import com.project.bookmarkboard.mapper.BookmarkMapper;
import com.project.bookmarkboard.mapper.FolderMapper;
import com.project.bookmarkboard.mapper.FolderViewMapper;
import com.project.bookmarkboard.mapper.UserMapper;
import com.project.bookmarkboard.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@Log4j2
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final FolderMapper folderMapper;
    private final FolderViewService folderViewService;
    private final BookmarkMapper bookmarkMapper;
    private final BookmarkService bookmarkService;
    private final ProfileService profileService;
    private final CustomUserDetailsService customUserDetailsService;

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
        log.debug("Received User: " + user);

        if(customUserDetails.getUserInternalId() != user.getInternalId()) {
            log.warn("This Request isn't valid.");
            return "redirect:/";
        }

        userService.updateUser(user);
        log.info("User Update Success");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(customUserDetailsService.createNewAuthentication(authentication, user.getUsername()));
        log.info("Refresh current authentication info");

        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String getMyPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model,
                            @RequestParam(value = "folder_page", required = false, defaultValue = "1") int folderPageNum,
                            @RequestParam(value = "bookmark_page", required = false, defaultValue = "1") int bookmarkPageNum) {
        final User user = userMapper.findByInternalId(customUserDetails.toUserDTO().getInternalId());
        // 개인정보 필요 없으니 마스킹
        user.setUsername(null);
        user.setPassword(null);
        user.setEmail(null);
        user.setRole(null);
        log.debug("Received User: " + user);

        model.addAttribute("item", user);

        model.addAttribute("notSharedFolderCount", folderMapper.getCountByOwnerAndIsShared(customUserDetails.getUserInternalId(), false));
        model.addAttribute("sharedFolderCount", folderMapper.getCountByOwnerAndIsShared(customUserDetails.getUserInternalId(), true));

        final FolderViewPagination folderViewPagination = folderViewService.getAllByOwnerOrderByIsStaredAndIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), folderPageNum, true);
        model.addAttribute("folderPagination", folderViewPagination.getPagination());
        model.addAttribute("folderItem", folderViewPagination.getFolderViewDTOList());

        model.addAttribute("notSharedBookmarkCount", bookmarkMapper.getCountByOwnerAndIsShared(customUserDetails.getUserInternalId(), false));
        model.addAttribute("sharedBookmarkCount", bookmarkMapper.getCountByOwnerAndIsShared(customUserDetails.getUserInternalId(), true));

        final BookmarkPagination bookmarkPagination = bookmarkService.getAllByOwnerOrderByIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), bookmarkPageNum);
        model.addAttribute("bookmarkPagination", bookmarkPagination.getPagination());
        model.addAttribute("bookmarkItem", bookmarkPagination.getBookmarkDTOList());
        return "profile/profile";
    }

    @GetMapping("/{id}")
    public String getProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                             @PathVariable("id") long id, Model model,
                             @RequestParam(value = "folder_page", required = false, defaultValue = "1") int folderPageNum,
                             @RequestParam(value = "bookmark_page", required = false, defaultValue = "1") int bookmarkPageNum) {
        if(customUserDetails.getUserInternalId() == id) {
            // 본인 계정의 프로필을 요청할 경우 마이페이지로 리다이렉트
            return "redirect:/profile/mypage";
        }

        final User user = userMapper.findByInternalId(id);
        // 개인정보 필요 없으니 마스킹
        user.setUsername(null);
        user.setPassword(null);
        user.setEmail(null);
        user.setRole(null);
        log.debug("Received User: " + user);

        model.addAttribute("item", user);

        model.addAttribute("sharedFolderCount", folderMapper.getCountByOwnerAndIsShared(id, true));

        final FolderViewPagination folderViewPagination = folderViewService.getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(id, folderPageNum, true);
        model.addAttribute("folderPagination", folderViewPagination.getPagination());
        model.addAttribute("folderItem", folderViewPagination.getFolderViewDTOList());

        final int sharedBookmarkCount = bookmarkMapper.getCountByOwnerAndIsShared(id, true);
        model.addAttribute("sharedBookmarkCount", sharedBookmarkCount);

        final BookmarkPagination bookmarkPagination = bookmarkService.getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(id, bookmarkPageNum, sharedBookmarkCount, true);
        model.addAttribute("bookmarkPagination", bookmarkPagination.getPagination());
        model.addAttribute("bookmarkItem", bookmarkPagination.getBookmarkDTOList());

        return "profile/profile";
    }

    @PostMapping("/update/profile_image")
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> postProfileImageUpdate(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                          ProfileImageUpdateRequest profileImageUpdateRequest) throws IOException {
        log.info("Profile Image Update Request Received!");
        profileService.uploadProfileImage(profileImageUpdateRequest.getImage(), customUserDetails.toUserDTO());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(customUserDetailsService.createNewAuthentication(authentication, customUserDetails.getUsername()));
        log.info("Refresh current authentication info");

        return ResponseEntity.ok().body(new CommonResponse<>("true"));
    }

    @DeleteMapping("/delete/profile_image")
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> deleteProfileImage(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {
        log.info("Profile Image Delete Request Received!");
        profileService.deleteProfileImage(customUserDetails.toUserDTO());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(customUserDetailsService.createNewAuthentication(authentication, customUserDetails.getUsername()));
        log.info("Refresh current authentication info");

        return ResponseEntity.ok().body(new CommonResponse<>("true"));
    }
}
