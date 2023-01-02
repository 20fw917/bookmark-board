package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.*;
import com.project.bookmarkboard.dto.pagination.BookmarkPagination;
import com.project.bookmarkboard.dto.pagination.FolderViewPagination;
import com.project.bookmarkboard.dto.response.BasicResponse;
import com.project.bookmarkboard.dto.response.CommonResponse;
import com.project.bookmarkboard.mapper.BookmarkMapper;
import com.project.bookmarkboard.mapper.FolderLikeMapper;
import com.project.bookmarkboard.mapper.FolderMapper;
import com.project.bookmarkboard.mapper.FolderViewMapper;
import com.project.bookmarkboard.service.BookmarkService;
import com.project.bookmarkboard.service.FolderService;
import com.project.bookmarkboard.service.FolderViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequestMapping("/folder")
@RequiredArgsConstructor
@Controller
public class FolderController {
    private final FolderViewService folderViewService;
    private final FolderMapper folderMapper;
    private final FolderViewMapper folderViewMapper;
    private final BookmarkMapper bookmarkMapper;
    private final FolderService folderService;
    private final BookmarkService bookmarkService;
    private final FolderLikeMapper folderLikeMapper;

    @GetMapping("")
    public String getFolderPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                @RequestParam(value = "care_stared", required = false, defaultValue = "true") boolean careStared,
                                Model model) {
        final FolderViewPagination folderViewPagination = folderViewService.getAllByOwnerOrderByIsStaredAndIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), pageNum, careStared);

        model.addAttribute("pagination", folderViewPagination.getPagination());
        model.addAttribute("items", folderViewPagination.getFolderViewDTOList());
        log.debug("pagination: " + folderViewPagination.getPagination());
        log.debug("items: " + folderViewPagination.getFolderViewDTOList());

        return "folder/list";
    }

    @GetMapping("/add")
    public String getAddFolder(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                               Model model) {
        model.addAttribute("isModify", false);

        final List<BookmarkDTO> bookmarkDTOList = bookmarkMapper.getAllByOwnerOrderByIdAndStaredDesc(customUserDetails.getUserInternalId());
        log.debug("Got from DB BookmarkDTOList: " + bookmarkDTOList);
        model.addAttribute("bookmarkList", bookmarkDTOList);

        return "folder/form";
    }

    @PostMapping("/add")
    public String postAddFolder(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                FolderRequestDTO folderRequestDTO,
                                @ModelAttribute("isShared") String isShared,
                                @ModelAttribute("isStared") String isStared) throws IOException {
        log.info("Folder Add Request Received");
        folderRequestDTO.setOwner(customUserDetails.getUserInternalId());
        if(!isShared.equals("")) {
            folderRequestDTO.setShared(Boolean.parseBoolean(isShared));
        }

        if(!isStared.equals("")) {
            folderRequestDTO.setStared(Boolean.parseBoolean(isStared));
        }

        log.debug("folderRequestDTO: " + folderRequestDTO);
        folderService.insertFolder(folderRequestDTO);

        return "redirect:/folder";
    }

    @GetMapping("/update")
    public String getUpdateFolderPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @RequestParam("id") long id, Model model) {
        log.info("Folder Update Page Get Request Received");
        final FolderViewDTO folderViewDTO = folderViewMapper.getOneById(id);
        if(customUserDetails.getUserInternalId() != folderViewDTO.getOwner()) {
            log.warn("Requested by not owner. returning the main page.");
            // 본인 것을 수정하는 것이 아니라면 메인으로 이동 처리.
            return "redirect:/";
        }

        final List<BookmarkDTO> bookmarkDTOList = bookmarkMapper.getAllByOwnerOrderByIdAndStaredDesc(customUserDetails.getUserInternalId());
        log.debug("Got from DB BookmarkDTOList: " + bookmarkDTOList);
        model.addAttribute("bookmarkList", bookmarkDTOList);

        List<BookmarkDTO> alreadyAddedBookmarkDTOList = new ArrayList<>();
        if(folderViewDTO.getItemCount() > 0) {
            alreadyAddedBookmarkDTOList = bookmarkMapper.getAllByIdListOrderByIsStaredDescAndIdDesc(folderService.getBookmarkIdListInFolderById(id));
        }

        log.debug("alreadyAddedBookmarkDTOList: " + alreadyAddedBookmarkDTOList);
        model.addAttribute("alreadyAddedBookmarkDTOList", alreadyAddedBookmarkDTOList);

        model.addAttribute("toModifyItem", folderViewDTO);
        log.debug("toModifyItem: " + folderViewDTO);
        model.addAttribute("isModify", true);

        return "folder/form";
    }

    @GetMapping("/detail/{id}")
    public String getFolderDetails(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                   @RequestParam(name = "page", required = false, defaultValue = "1") int pageNum,
                                   @PathVariable(name = "id") long folderId,
                                   Model model) {
        final FolderViewDTO folderViewDTO = folderViewMapper.getOneById(folderId);

        // 없는 폴더의 정보를 요청한 경우
        if(folderViewDTO == null) {
            return "redirect:/";
        }

        // 권한이 없는 폴더의 정보를 요청한 경우
        if(!folderViewDTO.isShared() && folderViewDTO.getOwner() != customUserDetails.getUserInternalId()) {
            return "redirect:/";
        }

        if(folderViewDTO.getItemCount() > 0) {
            BookmarkPagination pagination = bookmarkService.getAllByIdListOrderByIsStaredDescAndIdDescLimitByFromAndTo
                    (folderService.getBookmarkIdListInFolderById(folderId), pageNum, folderViewDTO.getItemCount());
            model.addAttribute("bookmarkList", pagination.getBookmarkDTOList());
            model.addAttribute("pagination", pagination.getPagination());
        }

        model.addAttribute("folder", folderViewDTO);

        return "folder/detail";
    }

    @PostMapping("/update")
    public String postUpdateFolder(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                FolderRequestDTO folderRequestDTO,
                                @ModelAttribute("isShared") String isShared,
                                @ModelAttribute("isStared") String isStared,
                               @ModelAttribute("deleteRequest") String deleteRequest) throws IOException {
        log.info("Folder Update Post Request Received");
        log.info("Received folderRequestDTO: " + folderRequestDTO);

        folderRequestDTO.setOwner(customUserDetails.getUserInternalId());
        if(!isShared.equals("")) {
            folderRequestDTO.setShared(Boolean.parseBoolean(isShared));
        }

        if(!isStared.equals("")) {
            folderRequestDTO.setStared(Boolean.parseBoolean(isStared));
        }

        if(!deleteRequest.equals("")) {
            folderRequestDTO.setDeleteRequest(Boolean.parseBoolean(deleteRequest));
        }

        folderService.updateFolder(folderRequestDTO);

        return "redirect:/folder/detail/" + folderRequestDTO.getId();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> deleteFolder(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                  @PathVariable long id) {
        log.info("Folder Delete Request Received");
        final FolderDTO folderDTO = folderMapper.getOneById(id);
        if(customUserDetails.getUserInternalId() != folderDTO.getOwner()) {
            log.warn("It is different from the logged in user and the owner of the requested item. Therefore, the update does not proceed.");
            // 권한이 없을 경우 에러 표출
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(folderService.deleteFolder(folderDTO)) {
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
        final FolderDTO folderDTO = folderMapper.getOneById(id);
        if(customUserDetails.getUserInternalId() != folderDTO.getOwner()) {
            log.warn("It is different from the logged in user and the owner of the requested item. Therefore, the update does not proceed.");
            // 권한이 없을 경우 에러 표출
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(folderDTO.isStared() == toModifyStaredStatus) {
            // 동일한 상태로 변경을 요청한 경우
            log.warn("This request requested a change to the same status. so this is not processed.");
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(folderMapper.updateIsStaredById(id, toModifyStaredStatus) == 1) {
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
        final FolderDTO folderDTO = folderMapper.getOneById(id);
        if(customUserDetails.getUserInternalId() != folderDTO.getOwner()) {
            log.warn("It is different from the logged in user and the owner of the requested item. Therefore, the update does not proceed.");
            // 권한이 없을 경우 에러 표출
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(folderDTO.isShared() == toModifySharedStatus) {
            // 동일한 상태로 변경을 요청한 경우
            log.warn("This request requested a change to the same status. so this is not processed.");
            return ResponseEntity.badRequest().body(new CommonResponse<>("false"));
        }

        if(folderMapper.updateIsSharedById(id, toModifySharedStatus) == 1) {
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
        final FolderDTO folderDTO = folderMapper.getOneById(id);

        // 없는 북마크를 요청한 경우
        if(folderDTO == null) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("NOT FOUND"));
        }

        // 본인의 북마크를 요청한 경우
        if(customUserDetails.getUserInternalId() == folderDTO.getOwner()) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested Your Own Bookmark"));
        }

        // 공유하지 않은 북마크를 요청한 경우
        if(!folderDTO.isShared()) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested Bookmark is not shared"));
        }

        folderService.copyFolder(id, customUserDetails.getUserInternalId());

        return ResponseEntity.ok().body(new CommonResponse<>("true"));
    }

    @PatchMapping("/like/{id}")
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> postLikeFolderRequest(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                         @PathVariable("id") long id, @RequestParam("to_modify_stared_status") boolean toModifyStaredStatus) {
        log.info("Folder Like Request Received");
        log.info("Item ID: " + id + " / User ID: " + customUserDetails.getUserInternalId());
        final FolderDTO folderDTO = folderMapper.getOneById(id);

        // 없는 북마크에 요청한 경우
        if(folderDTO == null) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("NOT FOUND"));
        }

        // 본인의 북마크를 요청한 경우
        if(customUserDetails.getUserInternalId() == folderDTO.getOwner()) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested Your Own Bookmark"));
        }

        final FolderLikeDTO requestedFolderLikeDTO = new FolderLikeDTO(customUserDetails.getUserInternalId(), id);

        // 추천 요청인데 이미 추천한 경우
        if(folderLikeMapper.getCountByFolderIdAndUserId(requestedFolderLikeDTO) == 1 && toModifyStaredStatus) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested Already Do Like"));
        }

        // 추천 취소 요청인데 추천하지 않은 경우
        if(folderLikeMapper.getCountByFolderIdAndUserId(requestedFolderLikeDTO) == 0 && !toModifyStaredStatus) {
            return ResponseEntity.badRequest().body(new CommonResponse<>("Requested Already Dislike"));
        }

        if(toModifyStaredStatus) {
            folderLikeMapper.insertFolderLike(requestedFolderLikeDTO);
        } else {
            folderLikeMapper.deleteFolderLikeById(requestedFolderLikeDTO);
        }

        return ResponseEntity.ok().body(new CommonResponse<>("true"));
    }
}
