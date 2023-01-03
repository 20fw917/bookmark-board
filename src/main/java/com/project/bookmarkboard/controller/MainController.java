package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.folder.FolderView;
import com.project.bookmarkboard.dto.user.CustomUserDetails;
import com.project.bookmarkboard.dto.folder.FolderViewPagination;
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
    public String getSearch() {
        return "search/list";
    }
}
