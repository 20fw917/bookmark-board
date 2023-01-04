package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.bookmark.Bookmark;
import com.project.bookmarkboard.dto.bookmark.BookmarkViewPagination;
import com.project.bookmarkboard.dto.user.CustomUserDetails;
import com.project.bookmarkboard.dto.basic.BasicResponse;
import com.project.bookmarkboard.dto.response.CommonResponse;
import com.project.bookmarkboard.service.BookmarkLikeService;
import com.project.bookmarkboard.service.BookmarkService;
import com.project.bookmarkboard.service.BookmarkViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final BookmarkViewService bookmarkViewService;
    private final BookmarkLikeService bookmarkLikeService;

    @GetMapping("")
    public String getMyBookmarkList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                    @RequestParam(value = "not_stared_page", required = false, defaultValue = "1") int notStaredPageNum,
                                    @RequestParam(value = "stared_page", required = false, defaultValue = "1") int staredPageNum,
                                    Model model) {
        final BookmarkViewPagination staredBookmarkViewPagination = bookmarkViewService.getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), staredPageNum, true);
        final BookmarkViewPagination notStaredBookmarkViewPagination = bookmarkViewService.getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), notStaredPageNum, false);

        model.addAttribute("staredBookmarkPagination", staredBookmarkViewPagination.getPagination());
        model.addAttribute("staredBookmarkItems", staredBookmarkViewPagination.getBookmarkViewList());
        log.debug("staredBookmarkViewPagination: " + staredBookmarkViewPagination.getPagination());
        log.debug("staredBookmarkItems: " + staredBookmarkViewPagination.getBookmarkViewList());

        model.addAttribute("notStaredBookmarkPagination", notStaredBookmarkViewPagination.getPagination());
        model.addAttribute("notStaredBookmarkItems", notStaredBookmarkViewPagination.getBookmarkViewList());
        log.debug("notStaredBookmarkViewPagination: " + notStaredBookmarkViewPagination.getPagination());
        log.debug("notStaredBookmarkItems: " + notStaredBookmarkViewPagination.getBookmarkViewList());

        return "bookmark/list";
    }

    @GetMapping("/add")
    public String getAddBookmark(Model model) {
        model.addAttribute("isModify", false);

        return "bookmark/form";
    }

    @PostMapping("/add")
    public String postAddBookmark(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  Bookmark bookmark,
                                  @ModelAttribute("isShared") String isShared,
                                  @ModelAttribute("isStared") String isStared) {
        log.info("Bookmark Add Request Received");

        bookmark.setOwner(customUserDetails.getUserInternalId());
        if (!isShared.equals("")) {
            bookmark.setShared(Boolean.parseBoolean(isShared));
        }

        if (!isStared.equals("")) {
            bookmark.setStared(Boolean.parseBoolean(isStared));
        }
        log.debug("Received bookmark: " + bookmark);

        if (bookmarkService.insertBookmark(bookmark)) {
            log.info("Bookmark Insert Successfully");
        }
        return "redirect:/bookmark";
    }

    @GetMapping("/update")
    public String getUpdateBookmarkPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @RequestParam("id") long id, Model model) {
        log.info("Bookmark Update Page Get Request Received");
        final Bookmark bookmark = bookmarkService.getOneById(id);
        if (customUserDetails.getUserInternalId() != bookmark.getOwner()) {
            log.warn("Requested by not owner. returning the main page.");
            // 본인 것을 수정하는 것이 아니라면 메인으로 이동 처리.
            return "redirect:/";
        }

        model.addAttribute("toModifyItem", bookmark);
        model.addAttribute("isModify", true);
        return "bookmark/form";
    }

    @PostMapping("/update")
    public String postUpdateBookmarkPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         Bookmark bookmark,
                                         @ModelAttribute("isShared") String isShared,
                                         @ModelAttribute("isStared") String isStared) {
        log.info("Bookmark Update Post Request Received");
        log.debug("Received bookmark: " + bookmark);
        bookmark.setOwner(customUserDetails.getUserInternalId());
        if (!isShared.equals("")) {
            bookmark.setShared(Boolean.parseBoolean(isShared));
        }

        if (!isStared.equals("")) {
            bookmark.setStared(Boolean.parseBoolean(isStared));
        }

        if (bookmarkService.updateBookmarkById(bookmark)) {
            log.info("Bookmark Update Successfully.");
        }

        return "redirect:/bookmark";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> deleteBookmark(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                  @PathVariable long id) {
        log.info("Bookmark Delete Request Received");
        final Bookmark bookmark = bookmarkService.getOneById(id);
        if (customUserDetails.getUserInternalId() != bookmark.getOwner()) {
            log.warn("It is different from the logged in user and the owner of the requested item. Therefore, the deletion does not proceed.");
            // 권한이 없을 경우 에러 표출
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if (bookmarkService.deleteBookmarkById(id)) {
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
        log.info("Bookmark stared status update request received.");
        final Bookmark bookmark = bookmarkService.getOneById(id);
        if (customUserDetails.getUserInternalId() != bookmark.getOwner()) {
            log.warn("It is different from the logged in user and the owner of the requested item. Therefore, the update does not proceed.");
            // 권한이 없을 경우 에러 표출
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if (bookmark.isStared() == toModifyStaredStatus) {
            // 동일한 상태로 변경을 요청한 경우
            log.warn("This request requested a change to the same status. so this is not processed.");
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if (bookmarkService.updateIsStaredById(id, toModifyStaredStatus)) {
            // 정상적으로 변경이 된 경우
            return ResponseEntity.ok().body(new CommonResponse<>("true"));
        }

        // 정상적으로 진행이 안 된 경우
        return ResponseEntity.internalServerError().body(new CommonResponse<>("false"));
    }

    @PatchMapping("/update/shared/{id}")
    public ResponseEntity<? extends BasicResponse> updateShared(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                @PathVariable long id, @RequestParam("to_modify_shared_status") boolean toModifySharedStatus) {
        log.info("Bookmark shared status update request received.");
        final Bookmark bookmark = bookmarkService.getOneById(id);
        if (customUserDetails.getUserInternalId() != bookmark.getOwner()) {
            log.warn("It is different from the logged in user and the owner of the requested item. Therefore, the update does not proceed.");
            // 권한이 없을 경우 에러 표출
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if (bookmark.isShared() == toModifySharedStatus) {
            // 동일한 상태로 변경을 요청한 경우
            log.warn("This request requested a change to the same status. so this is not processed.");
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if (bookmarkService.updateIsSharedById(id, toModifySharedStatus)) {
            // 정상적으로 변경이 된 경우
            return ResponseEntity.ok().body(new CommonResponse<>("true"));
        }

        // 정상적으로 진행이 안 된 경우
        return ResponseEntity.internalServerError().body(new CommonResponse<>("false"));
    }


    @PostMapping("/search")
    @ResponseBody
    public List<Bookmark> getSearchBookmarkDTO(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @RequestParam("keyword") String keyword) {
        log.info("Bookmark Search Request Received!");
        log.info("Received Keyword(Trim): " + keyword.trim());

        final List<Bookmark> result = bookmarkService.getAllByOwnerAndKeywordOrderByIsStaredDescAndIdDesc(customUserDetails.getUserInternalId(), keyword);
        log.debug("Search Result: " + result);

        return result;
    }

    @PostMapping("/copy/{id}")
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> postCopyBookmarkRequest(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                           @PathVariable("id") long id) {
        log.info("Bookmark Copy Request Received");
        log.info("From Item ID: " + id + " / To User ID: " + customUserDetails.getUserInternalId());
        final Bookmark bookmark = bookmarkService.getOneById(id);

        // 없는 북마크를 요청한 경우
        if (bookmark == null) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("NOT FOUND"));
        }

        // 본인의 북마크를 요청한 경우
        if (customUserDetails.getUserInternalId() == bookmark.getOwner()) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested Your Own Bookmark"));
        }

        // 공유하지 않은 북마크를 요청한 경우
        if (!bookmark.isShared()) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested Bookmark is not shared"));
        }

        final Bookmark toInsertBookmark = Bookmark.builder()
                .owner(customUserDetails.getUserInternalId())
                .title(bookmark.getTitle())
                .url(bookmark.getUrl())
                .memo(bookmark.getMemo())
                .build();
        bookmarkService.insertBookmark(toInsertBookmark);

        return ResponseEntity.ok().body(new CommonResponse<>("true"));
    }

    @PatchMapping("/like/{id}")
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> postLikeFolderRequest(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                         @PathVariable("id") long bookmarkId, @RequestParam("to_modify_liked_status") boolean toModifyLikedStatus) {
        log.info("Bookmark Like Request Received");
        log.info("Item ID: " + bookmarkId + " / User ID: " + customUserDetails.getUserInternalId());
        final Bookmark bookmark = bookmarkService.getOneById(bookmarkId);

        // 없는 폴더에 요청한 경우
        if (bookmark == null) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("NOT FOUND"));
        }

        // 본인의 폴더에 요청한 경우
        if (customUserDetails.getUserInternalId() == bookmark.getOwner()) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested your own bookmark"));
        }

        final boolean likeStatus = bookmarkLikeService.getCountByBookmarkIdAndUserId(customUserDetails.getUserInternalId(), bookmarkId);

        // 추천 요청인데 이미 추천한 경우
        if (likeStatus && toModifyLikedStatus) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested Already Do Like"));
        }

        // 추천 취소 요청인데 추천하지 않은 경우
        if (!likeStatus && !toModifyLikedStatus) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested Already Dislike"));
        }

        if (toModifyLikedStatus) {
            bookmarkLikeService.insertBookmarkLike(customUserDetails.getUserInternalId(), bookmarkId);
        } else {
            bookmarkLikeService.deleteBookmarkLikeByUserIdAndFolderId(customUserDetails.getUserInternalId(), bookmarkId);
        }

        return ResponseEntity.ok().body(new CommonResponse<>("true"));
    }
}
