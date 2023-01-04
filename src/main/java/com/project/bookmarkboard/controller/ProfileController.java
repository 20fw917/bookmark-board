package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.bookmark.BookmarkView;
import com.project.bookmarkboard.dto.folder.FolderView;
import com.project.bookmarkboard.dto.user.CustomUserDetails;
import com.project.bookmarkboard.dto.user.ProfileImageUpdateRequest;
import com.project.bookmarkboard.dto.user.User;
import com.project.bookmarkboard.dto.bookmark.BookmarkViewPagination;
import com.project.bookmarkboard.dto.folder.FolderViewPagination;
import com.project.bookmarkboard.dto.basic.BasicResponse;
import com.project.bookmarkboard.dto.response.CommonResponse;
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
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final FolderService folderService;
    private final FolderViewService folderViewService;
    private final BookmarkService bookmarkService;
    private final BookmarkViewService bookmarkViewService;
    private final ProfileService profileService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/update")
    public String getProfileUpdatePage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        final User user = userService.getOneByInternalId(customUserDetails.getUserInternalId());
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
        final User user = userService.getOneByInternalId(customUserDetails.toUserDTO().getInternalId());
        // 개인정보 필요 없으니 마스킹
        user.setUsername(null);
        user.setPassword(null);
        user.setEmail(null);
        user.setRole(null);
        log.debug("Received User: " + user);

        model.addAttribute("user", user);

        model.addAttribute("notSharedFolderCount", folderService.getCountByOwnerAndIsShared(customUserDetails.getUserInternalId(), false));
        model.addAttribute("sharedFolderCount", folderService.getCountByOwnerAndIsShared(customUserDetails.getUserInternalId(), true));

        final FolderViewPagination folderViewPagination = folderViewService.getAllByOwnerOrderByIsStaredAndIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), folderPageNum, true);
        model.addAttribute("folderPagination", folderViewPagination.getPagination());
        model.addAttribute("folderItem", folderViewPagination.getFolderViewList());

        model.addAttribute("notSharedBookmarkCount", bookmarkService.getCountByOwnerAndIsShared(customUserDetails.getUserInternalId(), false));
        model.addAttribute("sharedBookmarkCount", bookmarkService.getCountByOwnerAndIsShared(customUserDetails.getUserInternalId(), true));

        final BookmarkViewPagination bookmarkViewPagination = bookmarkViewService.getAllByOwnerOrderByIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), bookmarkPageNum);
        model.addAttribute("bookmarkPagination", bookmarkViewPagination.getPagination());
        model.addAttribute("bookmarkItem", bookmarkViewPagination.getBookmarkViewList());

        return "profile/profile";
    }

    @GetMapping("/{id}")
    public String getProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                             @PathVariable("id") long id, Model model,
                             @RequestParam(value = "folder_page", required = false, defaultValue = "1") int folderPageNum,
                             @RequestParam(value = "bookmark_page", required = false, defaultValue = "1") int bookmarkPageNum) {
        if(customUserDetails != null && customUserDetails.getUserInternalId() == id) {
            // 본인 계정의 프로필을 요청할 경우 마이페이지로 리다이렉트
            return "redirect:/profile/mypage";
        }

        final User user = userService.getOneByInternalId(id);
        // 개인정보 필요 없으니 마스킹
        user.setUsername(null);
        user.setPassword(null);
        user.setEmail(null);
        user.setRole(null);
        log.debug("Received User: " + user);

        model.addAttribute("user", user);

        model.addAttribute("sharedFolderCount", folderService.getCountByOwnerAndIsShared(id, true));

        final FolderViewPagination folderViewPagination = folderViewService.getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(id, folderPageNum, true);
        model.addAttribute("folderPagination", folderViewPagination.getPagination());
        if(customUserDetails != null) {
            final List<FolderView> folderViewList = folderViewService.getLikeStatus(folderViewPagination.getFolderViewList(), customUserDetails.getUserInternalId());
            model.addAttribute("folderItem", folderViewList);
        } else {
            model.addAttribute("folderItem", folderViewPagination.getFolderViewList());
        }

        final int sharedBookmarkCount = bookmarkService.getCountByOwnerAndIsShared(id, true);
        model.addAttribute("sharedBookmarkCount", sharedBookmarkCount);

        final BookmarkViewPagination bookmarkViewPagination = bookmarkViewService.getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(id, bookmarkPageNum, sharedBookmarkCount, true);
        model.addAttribute("bookmarkPagination", bookmarkViewPagination.getPagination());
        if(customUserDetails != null) {
            final List<BookmarkView> bookmarkViewList = bookmarkViewService.getLikeStatus(bookmarkViewPagination.getBookmarkViewList(), customUserDetails.getUserInternalId());
            model.addAttribute("bookmarkItem", bookmarkViewList);
        } else {
            model.addAttribute("bookmarkItem", bookmarkViewPagination.getBookmarkViewList());
        }
        model.addAttribute("bookmarkItem", bookmarkViewPagination.getBookmarkViewList());

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
    public ResponseEntity<? extends BasicResponse> deleteProfileImage(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("Profile Image Delete Request Received!");
        profileService.deleteProfileImage(customUserDetails.toUserDTO());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(customUserDetailsService.createNewAuthentication(authentication, customUserDetails.getUsername()));
        log.info("Refresh current authentication info");

        return ResponseEntity.ok().body(new CommonResponse<>("true"));
    }
}
