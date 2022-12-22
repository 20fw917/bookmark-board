package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.BookmarkPagination;
import com.project.bookmarkboard.dto.CustomUserDetails;
import com.project.bookmarkboard.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class MyBookmarkController {
    private final BookmarkService bookmarkService;

    @GetMapping("")
    public String getMyBookmarkList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                    Model model) {
        final BookmarkPagination bookmarkPagination = bookmarkService.getAllByOwnerOrderByIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), pageNum);

        model.addAttribute("pagination", bookmarkPagination.getPagination());
        model.addAttribute("items", bookmarkPagination.getBookmarkDTOList());
        return "/bookmark/bookmark_list";
    }
}
