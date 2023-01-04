package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.bookmark.BookmarkViewPagination;
import com.project.bookmarkboard.dto.folder.FolderView;
import com.project.bookmarkboard.dto.user.CustomUserDetails;
import com.project.bookmarkboard.dto.folder.FolderViewPagination;
import com.project.bookmarkboard.service.BookmarkViewService;
import com.project.bookmarkboard.service.FolderViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class MainController {
    private final FolderViewService folderViewService;
    private final BookmarkViewService bookmarkViewService;

    @GetMapping({"/"})
    public String getMain(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                          @RequestParam(value = "my_folder", required = false, defaultValue = "1") int myFolderPageNum,
                          @RequestParam(value = "suggest_folder", required = false, defaultValue = "1") int suggestFolderPageNum,
                          Model model) {
        if(customUserDetails != null) {
            final FolderViewPagination folderViewPagination = folderViewService.getAllByOwnerAndIsStaredOrderById(customUserDetails.getUserInternalId(), myFolderPageNum, true);

            model.addAttribute("myFolderPagination", folderViewPagination.getPagination());
            model.addAttribute("myFolderItems", folderViewPagination.getFolderViewList());
            log.debug("myFolderPagination: " + folderViewPagination.getPagination());
            log.debug("myFolderItems: " + folderViewPagination.getFolderViewList());
        }

        final FolderViewPagination suggestFolderViewPagination = folderViewService.getSuggestFolder(customUserDetails == null ? null : customUserDetails.getUserInternalId(), suggestFolderPageNum, true);

        model.addAttribute("suggestFolderPagination", suggestFolderViewPagination.getPagination());
        if(customUserDetails == null) {
            model.addAttribute("suggestFolderItems", suggestFolderViewPagination.getFolderViewList());
        } else {
            final List<FolderView> suggestFolderViewList = folderViewService.getLikeStatus(suggestFolderViewPagination.getFolderViewList(), customUserDetails.getUserInternalId());
            model.addAttribute("suggestFolderItems", suggestFolderViewList);
        }

        log.debug("suggestFolderPagination: " + suggestFolderViewPagination.getPagination());
        log.debug("suggestFolderItems: " + suggestFolderViewPagination.getFolderViewList());

        return "main";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(name = "fail", required = false) boolean isFail) {
        model.addAttribute("fail", isFail);

        return "user/login";
    }

    // GET 요청으로도 Logout이 가능하게 Mapping
    @GetMapping("/logout")
    public String getLogout(HttpServletRequest request, HttpServletResponse response) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/";
    }

    @GetMapping("/search")
    public String getSearch(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                            @RequestParam(name = "keyword") String keyword,
                            @RequestParam(name = "folder_page", required = false, defaultValue = "1") int folderPageNum,
                            @RequestParam(name = "folder_current_user_only", required = false, defaultValue = "false") boolean folderCurrentUserOnly,
                            @RequestParam(name = "bookmark_page", required = false, defaultValue = "1") int bookmarkPageNum,
                            @RequestParam(name = "bookmark_current_user_only", required = false, defaultValue = "false") boolean bookmarkCurrentUserOnly,
                            Model model) {
        model.addAttribute("keyword", keyword.trim());
        model.addAttribute("folderPageNum", folderPageNum);
        model.addAttribute("folderCurrentUserOnly", folderCurrentUserOnly);
        model.addAttribute("bookmarkPageNum", bookmarkPageNum);
        model.addAttribute("bookmarkCurrentUserOnly", bookmarkCurrentUserOnly);

        FolderViewPagination folderViewPagination;
        if(customUserDetails != null) {
            folderViewPagination = folderViewService.getSearchResult(keyword, customUserDetails.getUserInternalId(), folderCurrentUserOnly, folderPageNum);
        } else {
            folderViewPagination = folderViewService.getSearchResult(keyword, null, folderCurrentUserOnly, folderPageNum);
        }
        model.addAttribute("folderPagination", folderViewPagination.getPagination());
        model.addAttribute("folderItems", folderViewPagination.getFolderViewList());
        log.debug("folderPagination: " + folderViewPagination.getPagination());
        log.debug("folderItems: " + folderViewPagination.getFolderViewList());

        BookmarkViewPagination bookmarkViewPagination;
        if(customUserDetails != null) {
            bookmarkViewPagination = bookmarkViewService.getSearchResult(keyword, customUserDetails.getUserInternalId(), folderCurrentUserOnly, folderPageNum);
        } else {
            bookmarkViewPagination = bookmarkViewService.getSearchResult(keyword, null, folderCurrentUserOnly, folderPageNum);
        }
        model.addAttribute("bookmarkPagination", bookmarkViewPagination.getPagination());
        model.addAttribute("bookmarkItems", bookmarkViewPagination.getBookmarkViewList());
        log.debug("bookmarkPagination: " + bookmarkViewPagination.getPagination());
        log.debug("bookmarkItems: " + bookmarkViewPagination.getPagination());

        return "search/list";
    }
}
