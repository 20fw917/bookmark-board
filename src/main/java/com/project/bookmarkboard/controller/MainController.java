package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.CustomUserDetails;
import com.project.bookmarkboard.dto.pagination.FolderViewPagination;
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
            model.addAttribute("myFolderItems", folderViewPagination.getFolderViewDTOList());
            log.debug("myFolderPagination: " + folderViewPagination.getPagination());
            log.debug("myFolderItems: " + folderViewPagination.getFolderViewDTOList());

            final FolderViewPagination suggestFolderViewPagination = folderViewService.getAllByNotOwnerAndIsSharedOrderByLikeCountLimitByFromAndTo(customUserDetails.getUserInternalId(), suggestFolderPageNum, true);

            model.addAttribute("suggestFolderPagination", suggestFolderViewPagination.getPagination());
            model.addAttribute("suggestFolderItems", suggestFolderViewPagination.getFolderViewDTOList());
            log.debug("suggestFolderPagination: " + suggestFolderViewPagination.getPagination());
            log.debug("suggestFolderItems: " + suggestFolderViewPagination.getFolderViewDTOList());
        } else {
            final FolderViewPagination suggestFolderViewPagination = folderViewService.getAllByIsSharedOrderByLikeCount(suggestFolderPageNum, true);

            model.addAttribute("suggestFolderPagination", suggestFolderViewPagination.getPagination());
            model.addAttribute("suggestFolderItems", suggestFolderViewPagination.getFolderViewDTOList());
            log.debug("suggestFolderPagination: " + suggestFolderViewPagination.getPagination());
            log.debug("suggestFolderItems: " + suggestFolderViewPagination.getFolderViewDTOList());
        }

        return "main";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(name = "fail", required = false) boolean isFail) {
        model.addAttribute("fail", isFail);

        return "login";
    }

    @GetMapping("/logout")
    public String getLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
