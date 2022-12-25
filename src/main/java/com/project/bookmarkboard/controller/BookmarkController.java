package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.BookmarkDTO;
import com.project.bookmarkboard.dto.BookmarkPagination;
import com.project.bookmarkboard.dto.CustomUserDetails;
import com.project.bookmarkboard.dto.response.BasicResponse;
import com.project.bookmarkboard.dto.response.CommonResponse;
import com.project.bookmarkboard.mapper.BookmarkMapper;
import com.project.bookmarkboard.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
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
        model.addAttribute("staredBookmarkItems", staredBookmarkPagination.getBookmarkDTOList());
        log.debug("staredBookmarkPagination: " + staredBookmarkPagination.getPagination());
        log.debug("staredBookmarkItems: " + staredBookmarkPagination.getBookmarkDTOList());

        model.addAttribute("notStaredBookmarkPagination", notStaredBookmarkPagination.getPagination());
        model.addAttribute("notStaredBookmarkItems", notStaredBookmarkPagination.getBookmarkDTOList());
        log.debug("notStaredBookmarkPagination: " + notStaredBookmarkPagination.getPagination());
        log.debug("notStaredBookmarkItems: " + notStaredBookmarkPagination.getBookmarkDTOList());

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
        log.info("Bookmark Add Request Received");

        bookmarkDTO.setOwner(customUserDetails.getUserInternalId());
        if(!isShared.equals("")) {
            bookmarkDTO.setShared(Boolean.parseBoolean(isShared));
        }

        if(!isStared.equals("")) {
            bookmarkDTO.setStared(Boolean.parseBoolean(isStared));
        }
        log.debug("Received bookmarkDTO: " + bookmarkDTO);

        if(bookmarkMapper.insertBookmark(bookmarkDTO) == 1) {
            log.info("Bookmark Insert Successfully");
        }
        return "redirect:/bookmark";
    }

    @GetMapping("/update")
    public String getUpdateBookmarkPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  @RequestParam("id") long id, Model model) {
        log.info("Bookmark Update Page Get Request Received");
        final BookmarkDTO bookmarkDTO = bookmarkMapper.getOneById(id);
        if(customUserDetails.getUserInternalId() != bookmarkDTO.getOwner()) {
            log.warn("Requested by not owner. returning the main page.");
            // 본인 것을 수정하는 것이 아니라면 메인으로 이동 처리.
            return "redirect:/";
        }

        model.addAttribute("toModifyItem", bookmarkDTO);
        model.addAttribute("isModify", true);
        return "bookmark/form";
    }

    @PostMapping("/update")
    public String postUpdateBookmarkPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         BookmarkDTO bookmarkDTO,
                                         @ModelAttribute("isShared") String isShared,
                                         @ModelAttribute("isStared") String isStared) {
        log.info("Bookmark Update Post Request Received");
        log.debug("Received bookmarkDTO: " + bookmarkDTO);
        bookmarkDTO.setOwner(customUserDetails.getUserInternalId());
        if(!isShared.equals("")) {
            bookmarkDTO.setShared(Boolean.parseBoolean(isShared));
        }

        if(!isStared.equals("")) {
            bookmarkDTO.setStared(Boolean.parseBoolean(isStared));
        }

        if(bookmarkMapper.updateBookmarkById(bookmarkDTO) == 1) {
            log.info("Bookmark Update Successfully.");
        }

        return "redirect:/bookmark";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> deleteBookmark(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                  @PathVariable long id) {
        log.info("Bookmark Delete Request Received");
        final BookmarkDTO bookmarkDTO = bookmarkMapper.getOneById(id);
        if(customUserDetails.getUserInternalId() != bookmarkDTO.getOwner()) {
            log.warn("It is different from the logged in user and the owner of the requested item. Therefore, the deletion does not proceed.");
            // 권한이 없을 경우 에러 표출
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(bookmarkMapper.deleteBookmarkById(id) == 1) {
            log.info("This request is valid and the deletion is successfully.");
            // 정상적으로 삭제가 된 경우
            return ResponseEntity.ok().body(new CommonResponse<>("true"));
        }

        // 정상적으로 진행이 안 된 경우
        return ResponseEntity.internalServerError().body(new CommonResponse<>("false"));
    }

    @PatchMapping("/update/stared/{id}")
    public ResponseEntity<? extends BasicResponse> updateStared(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                  @PathVariable long id, @RequestParam("to_modify_stared_status") boolean toModifyStaredStatus) {
        final BookmarkDTO bookmarkDTO = bookmarkMapper.getOneById(id);
        if(customUserDetails.getUserInternalId() != bookmarkDTO.getOwner()) {
            // 권한이 없을 경우 에러 표출
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(bookmarkDTO.isStared() == toModifyStaredStatus) {
            // 동일한 상태로 변경을 요청한 경우
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(bookmarkMapper.updateIsStaredById(id, toModifyStaredStatus) == 1) {
            // 정상적으로 삭제가 된 경우
            return ResponseEntity.ok().body(new CommonResponse<>("true"));
        }

        // 정상적으로 진행이 안 된 경우
        return ResponseEntity.internalServerError().body(new CommonResponse<>("false"));
    }
}
