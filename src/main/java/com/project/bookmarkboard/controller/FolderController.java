package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.BookmarkDTO;
import com.project.bookmarkboard.dto.CustomUserDetails;
import com.project.bookmarkboard.dto.FolderDTO;
import com.project.bookmarkboard.dto.FolderRequestDTO;
import com.project.bookmarkboard.dto.pagination.FolderViewBasicPagination;
import com.project.bookmarkboard.dto.response.BasicResponse;
import com.project.bookmarkboard.dto.response.CommonResponse;
import com.project.bookmarkboard.mapper.BookmarkMapper;
import com.project.bookmarkboard.mapper.FolderMapper;
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
import java.util.List;

@Log4j2
@RequestMapping("/folder")
@RequiredArgsConstructor
@Controller
public class FolderController {
    private final FolderViewService folderViewService;
    private final FolderMapper folderMapper;
    private final BookmarkMapper bookmarkMapper;
    private final FolderService folderService;

    @GetMapping("")
    public String getFolderPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                @RequestParam(value = "care_stared", required = false, defaultValue = "true") boolean careStared,
                                Model model) {
        final FolderViewBasicPagination folderViewPagination = folderViewService.getAllByOwnerOrderByIdDescLimitByFromAndTo(customUserDetails.getUserInternalId(), pageNum, careStared);

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
        log.info("folderRequestDTO: " + folderRequestDTO);
        folderRequestDTO.setOwner(customUserDetails.getUserInternalId());
        if(!isShared.equals("")) {
            folderRequestDTO.setShared(Boolean.parseBoolean(isShared));
        }

        if(!isStared.equals("")) {
            folderRequestDTO.setStared(Boolean.parseBoolean(isStared));
        }

        folderService.insertFolder(folderRequestDTO);

        return "redirect:/folder";
    }

    @GetMapping("/update")
    public String getUpdateFolderPage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @RequestParam("id") long id, Model model) {
        log.info("Folder Update Page Get Request Received");
        final FolderDTO folderDTO = folderMapper.getOneById(id);
        if(customUserDetails.getUserInternalId() != folderDTO.getOwner()) {
            log.warn("Requested by not owner. returning the main page.");
            // 본인 것을 수정하는 것이 아니라면 메인으로 이동 처리.
            return "redirect:/";
        }

        final List<BookmarkDTO> bookmarkDTOList = bookmarkMapper.getAllByOwnerOrderByIdAndStaredDesc(customUserDetails.getUserInternalId());
        log.debug("Got from DB BookmarkDTOList: " + bookmarkDTOList);
        model.addAttribute("bookmarkList", bookmarkDTOList);

        final List<BookmarkDTO> alreadyAddedBookmarkDTOList = bookmarkMapper.getAllByIdListOrderByIsStaredDescAndIdDesc(folderService.getBookmarkIdListInFolderById(id));
        log.debug("alreadyAddedBookmarkDTOList: " + alreadyAddedBookmarkDTOList);
        model.addAttribute("alreadyAddedBookmarkDTOList", alreadyAddedBookmarkDTOList);

        model.addAttribute("toModifyItem", folderDTO);
        log.debug("toModifyItem: " + folderDTO);
        model.addAttribute("isModify", true);
        return "folder/form";
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

        return "redirect:/folder";
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
}
