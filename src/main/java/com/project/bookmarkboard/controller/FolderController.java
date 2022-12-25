package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.CustomUserDetails;
import com.project.bookmarkboard.dto.pagination.FolderViewBasicPagination;
import com.project.bookmarkboard.service.FolderViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequestMapping("/folder")
@RequiredArgsConstructor
@Controller
public class FolderController {
    private final FolderViewService folderViewService;

    @GetMapping("")
    public String getFolderPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                Model model) {
        final FolderViewBasicPagination folderViewPagination = folderViewService.getAllByOwnerOrderByIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), pageNum);

        model.addAttribute("pagination", folderViewPagination.getPagination());
        model.addAttribute("items", folderViewPagination.getFolderViewDTOList());
        log.debug("pagination: " + folderViewPagination.getPagination());
        log.debug("items: " + folderViewPagination.getFolderViewDTOList());

        return "folder/list";
    }
}
