package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.bookmark.Bookmark;
import com.project.bookmarkboard.dto.bookmark.BookmarkPagination;
import com.project.bookmarkboard.dto.folder.*;
import com.project.bookmarkboard.dto.response.BasicResponse;
import com.project.bookmarkboard.dto.response.CommonResponse;
import com.project.bookmarkboard.dto.user.CustomUserDetails;
import com.project.bookmarkboard.service.BookmarkService;
import com.project.bookmarkboard.service.FolderLikeService;
import com.project.bookmarkboard.service.FolderService;
import com.project.bookmarkboard.service.FolderViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequestMapping("/folder")
@RequiredArgsConstructor
@Controller
public class FolderController {
    private final FolderViewService folderViewService;
    private final FolderService folderService;
    private final FolderLikeService folderLikeService;
    private final BookmarkService bookmarkService;

    @GetMapping("")
    public String getFolderPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                @RequestParam(value = "care_stared", required = false, defaultValue = "true") boolean careStared,
                                Model model) {
        final FolderViewPagination folderViewPagination = folderViewService.getAllByOwnerOrderByIsStaredAndIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), pageNum, careStared);

        model.addAttribute("pagination", folderViewPagination.getPagination());
        model.addAttribute("items", folderViewPagination.getFolderViewList());
        log.debug("pagination: " + folderViewPagination.getPagination());
        log.debug("items: " + folderViewPagination.getFolderViewList());

        return "folder/list";
    }

    @GetMapping("/add")
    public String getAddFolder(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                               Model model) {
        model.addAttribute("isModify", false);

        final List<Bookmark> bookmarkList = bookmarkService.getAllByOwnerOrderByIdAndStaredDesc(customUserDetails.getUserInternalId());
        log.debug("Got from DB BookmarkDTOList: " + bookmarkList);
        model.addAttribute("bookmarkList", bookmarkList);

        return "folder/form";
    }

    @PostMapping("/add")
    public String postAddFolder(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                FolderRequest folderRequest,
                                @ModelAttribute("isShared") String isShared,
                                @ModelAttribute("isStared") String isStared) throws IOException {
        log.info("Folder Add Request Received");
        folderRequest.setOwner(customUserDetails.getUserInternalId());
        if(!isShared.equals("")) {
            folderRequest.setShared(Boolean.parseBoolean(isShared));
        }

        if(!isStared.equals("")) {
            folderRequest.setStared(Boolean.parseBoolean(isStared));
        }

        log.debug("folderRequest: " + folderRequest);
        folderService.insertFolder(folderRequest);

        return "redirect:/folder";
    }

    @GetMapping("/update")
    public String getUpdateFolderPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @RequestParam("id") long id, Model model) {
        log.info("Folder Update Page Get Request Received");
        final FolderView folderView = folderViewService.getOneById(id);
        if(customUserDetails.getUserInternalId() != folderView.getOwner()) {
            log.warn("Requested by not owner. returning the main page.");
            // 본인 것을 수정하는 것이 아니라면 메인으로 이동 처리.
            return "redirect:/";
        }

        final List<Bookmark> bookmarkList = bookmarkService.getAllByOwnerOrderByIdAndStaredDesc(customUserDetails.getUserInternalId());
        log.debug("Got from DB BookmarkList: " + bookmarkList);
        model.addAttribute("bookmarkList", bookmarkList);

        List<Bookmark> alreadyAddedBookmarkList = new ArrayList<>();
        if(folderView.getItemCount() > 0) {
            alreadyAddedBookmarkList = bookmarkService.getAllByIdListOrderByIsStaredDescAndIdDesc(bookmarkService.getBookmarkIdListInFolderById(id));
        }

        log.debug("alreadyAddedBookmarkList: " + alreadyAddedBookmarkList);
        model.addAttribute("alreadyAddedBookmarkList", alreadyAddedBookmarkList);

        model.addAttribute("toModifyItem", folderView);
        log.debug("toModifyItem: " + folderView);
        model.addAttribute("isModify", true);

        return "folder/form";
    }

    @GetMapping("/detail/{id}")
    public String getFolderDetails(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                   @RequestParam(name = "page", required = false, defaultValue = "1") int pageNum,
                                   @PathVariable(name = "id") long folderId,
                                   Model model) {
        final FolderView folderViewDomain = folderViewService.getOneById(folderId);

        // 없는 폴더의 정보를 요청한 경우
        if(folderViewDomain == null) {
            return "redirect:/";
        }

        // 권한이 없는 폴더의 정보를 요청한 경우
        if(!folderViewDomain.isShared() && folderViewDomain.getOwner() != customUserDetails.getUserInternalId()) {
            return "redirect:/";
        }

        if(folderViewDomain.getItemCount() > 0) {
            BookmarkPagination pagination = bookmarkService.getAllByIdListOrderByIsStaredDescAndIdDescLimitByFromAndTo
                    (bookmarkService.getBookmarkIdListInFolderById(folderId), pageNum, folderViewDomain.getItemCount());
            model.addAttribute("bookmarkList", pagination.getBookmarkList());
            model.addAttribute("pagination", pagination.getPagination());
        }

        if(customUserDetails != null) {
            if(folderViewDomain.getOwner() != customUserDetails.getUserInternalId()) {
                final FolderView folderView = folderViewService.getLikeStatus(folderViewDomain, customUserDetails.getUserInternalId());
                model.addAttribute("folder", folderView);
            } else {
                model.addAttribute("folder", folderViewDomain);
            }
        } else {
            model.addAttribute("folder", folderViewDomain);
        }

        return "folder/detail";
    }

    @PostMapping("/update")
    public String postUpdateFolder(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                FolderRequest folderRequest,
                                @ModelAttribute("isShared") String isShared,
                                @ModelAttribute("isStared") String isStared,
                               @ModelAttribute("deleteRequest") String deleteRequest) throws IOException {
        log.info("Folder Update Post Request Received");
        log.info("Received folderRequest: " + folderRequest);

        folderRequest.setOwner(customUserDetails.getUserInternalId());
        if(!isShared.equals("")) {
            folderRequest.setShared(Boolean.parseBoolean(isShared));
        }

        if(!isStared.equals("")) {
            folderRequest.setStared(Boolean.parseBoolean(isStared));
        }

        if(!deleteRequest.equals("")) {
            folderRequest.setDeleteRequest(Boolean.parseBoolean(deleteRequest));
        }

        folderService.updateFolder(folderRequest);

        return "redirect:/folder/detail/" + folderRequest.getId();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> deleteFolder(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                  @PathVariable long id) {
        log.info("Folder Delete Request Received");
        final Folder folder = folderService.getOneById(id);
        if(customUserDetails.getUserInternalId() != folder.getOwner()) {
            log.warn("It is different from the logged in user and the owner of the requested item. Therefore, the update does not proceed.");
            // 권한이 없을 경우 에러 표출
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(folderService.deleteFolder(folder)) {
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
        log.info("Folder stared status update request received.");
        final Folder folder = folderService.getOneById(id);
        if(customUserDetails.getUserInternalId() != folder.getOwner()) {
            log.warn("It is different from the logged in user and the owner of the requested item. Therefore, the update does not proceed.");
            // 권한이 없을 경우 에러 표출
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(folder.isStared() == toModifyStaredStatus) {
            // 동일한 상태로 변경을 요청한 경우
            log.warn("This request requested a change to the same status. so this is not processed.");
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(folderService.updateIsStaredById(id, toModifyStaredStatus)) {
            // 정상적으로 변경이 된 경우
            return ResponseEntity.ok().body(new CommonResponse<>("true"));
        }

        // 정상적으로 진행이 안 된 경우
        return ResponseEntity.internalServerError().body(new CommonResponse<>("false"));
    }

    @PatchMapping("/update/shared/{id}")
    public ResponseEntity<? extends BasicResponse> updateShared(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                @PathVariable long id, @RequestParam("to_modify_shared_status") boolean toModifySharedStatus) {
        log.info("Folder shared status update request received.");
        final Folder folder = folderService.getOneById(id);

        if(customUserDetails.getUserInternalId() != folder.getOwner()) {
            log.warn("It is different from the logged in user and the owner of the requested item. Therefore, the update does not proceed.");
            // 권한이 없을 경우 에러 표출
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(folder.isShared() == toModifySharedStatus) {
            // 동일한 상태로 변경을 요청한 경우
            log.warn("This request requested a change to the same status. so this is not processed.");
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(folderService.updateIsSharedById(id, toModifySharedStatus)) {
            // 정상적으로 변경이 된 경우
            return ResponseEntity.ok().body(new CommonResponse<>("true"));
        }

        // 정상적으로 진행이 안 된 경우
        return ResponseEntity.internalServerError().body(new CommonResponse<>("false"));
    }

    @PostMapping("/copy/{id}")
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> postFolderCopyRequest(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                           @PathVariable("id") long id) throws IOException {
        log.info("Folder Copy Request Received");
        log.info("From Item ID: " + id + " / To User ID: " + customUserDetails.getUserInternalId());
        final Folder folder = folderService.getOneById(id);

        // 없는 북마크를 요청한 경우
        if(folder == null) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("NOT FOUND"));
        }

        // 본인의 북마크를 요청한 경우
        if(customUserDetails.getUserInternalId() == folder.getOwner()) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested your own folder"));
        }

        // 공유하지 않은 북마크를 요청한 경우
        if(!folder.isShared()) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested folder is not shared"));
        }

        folderService.copyFolder(id, customUserDetails.getUserInternalId());

        return ResponseEntity.ok().body(new CommonResponse<>("true"));
    }

    @PatchMapping("/like/{id}")
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> postLikeFolderRequest(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                         @PathVariable("id") long folderId, @RequestParam("to_modify_liked_status") boolean toModifyLikedStatus) {
        log.info("Folder Like Request Received");
        log.info("Item ID: " + folderId + " / User ID: " + customUserDetails.getUserInternalId());
        final Folder folder = folderService.getOneById(folderId);

        // 없는 북마크에 요청한 경우
        if(folder == null) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("NOT FOUND"));
        }

        // 본인의 북마크를 요청한 경우
        if(customUserDetails.getUserInternalId() == folder.getOwner()) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested your own folder"));
        }

        // 추천 요청인데 이미 추천한 경우
        if(folderLikeService.getCountByFolderIdAndUserId(customUserDetails.getUserInternalId(), folderId) && toModifyLikedStatus) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested Already Do Like"));
        }

        // 추천 취소 요청인데 추천하지 않은 경우
        if(!folderLikeService.getCountByFolderIdAndUserId(customUserDetails.getUserInternalId(), folderId) && !toModifyLikedStatus) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested Already Dislike"));
        }

        if(toModifyLikedStatus) {
            folderLikeService.insertFolderLike(customUserDetails.getUserInternalId(), folderId);
        } else {
            folderLikeService.deleteFolderLikeByUserIdAndFolderId(customUserDetails.getUserInternalId(), folderId);
        }

        return ResponseEntity.ok().body(new CommonResponse<>("true"));
    }
}
