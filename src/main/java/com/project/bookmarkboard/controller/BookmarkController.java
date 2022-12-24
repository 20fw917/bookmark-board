package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.BookmarkDTO;
import com.project.bookmarkboard.dto.BookmarkPagination;
import com.project.bookmarkboard.dto.CustomUserDetails;
import com.project.bookmarkboard.mapper.BookmarkMapper;
import com.project.bookmarkboard.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final BookmarkMapper bookmarkMapper;

    @GetMapping("")
    public String getMyBookmarkList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                    @RequestParam(value = "not_stared_page", required = false, defaultValue = "1") int notStaredPageNum,
                                    @RequestParam(value = "stared_page", required = false, defaultValue = "1") int staredPageNum,
                                    Model model) {
        final BookmarkPagination staredBookmarkPagination = bookmarkService.getAllByOwnerOrderByIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), staredPageNum, true);
        final BookmarkPagination notStaredBookmarkPagination = bookmarkService.getAllByOwnerOrderByIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), notStaredPageNum, false);


        model.addAttribute("staredBookmarkPagination", staredBookmarkPagination.getPagination());
        log.info("staredBookmarkPagination: " + staredBookmarkPagination.getPagination());
        model.addAttribute("staredBookmarkItems", staredBookmarkPagination.getBookmarkDTOList());
        log.info("staredBookmarkItems: " + staredBookmarkPagination.getBookmarkDTOList());

        model.addAttribute("notStaredBookmarkPagination", notStaredBookmarkPagination.getPagination());
        log.info("notStaredBookmarkPagination: " + notStaredBookmarkPagination.getPagination());
        model.addAttribute("notStaredBookmarkItems", notStaredBookmarkPagination.getBookmarkDTOList());
        log.info("notStaredBookmarkItems: " + notStaredBookmarkPagination.getBookmarkDTOList());

        return "bookmark/list";
    }

    @GetMapping("/add")
    public String getAddBookmark(Model model) {
        model.addAttribute("isModify", false);
        model.addAttribute("bookmarkDTO", new BookmarkDTO());

        return "bookmark/form";
    }

    @PostMapping("/add")
    public String postAddBookmark(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  BookmarkDTO bookmarkDTO,
                                  @ModelAttribute("isShared") String isShared,
                                  @ModelAttribute("isStared") String isStared) {
        bookmarkDTO.setOwner(customUserDetails.getUserInternalId());
        if(!isShared.equals("")) {
            bookmarkDTO.setShared(Boolean.parseBoolean(isShared));
        }

        if(!isStared.equals("")) {
            bookmarkDTO.setStared(Boolean.parseBoolean(isStared));
        }
        log.info("Received bookmarkDTO: " + bookmarkDTO);

        if(bookmarkMapper.insertBookmark(bookmarkDTO) == 1) {
            log.info("Bookmark Insert Successfully");
        }
        return "bookmark/form";
    }
}
