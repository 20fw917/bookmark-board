package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.FolderDTO;
import com.project.bookmarkboard.dto.FolderItemDTO;
import com.project.bookmarkboard.dto.FolderRequestDTO;
import com.project.bookmarkboard.mapper.FolderItemMapper;
import com.project.bookmarkboard.mapper.FolderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class FolderService {
    private final AttachmentService attachmentService;
    private final FolderMapper folderMapper;
    private final FolderItemMapper folderItemMapper;

    @Transactional
    public void insertFolder(FolderRequestDTO folderRequestDTO) throws IOException  {
        if(folderRequestDTO.getFolderThumbnail().getSize() != 0) {
            final String renamedFileName = attachmentService.saveFile(folderRequestDTO.getFolderThumbnail(), "folder_thumbnail");
            folderRequestDTO.setThumbnail(renamedFileName);

            log.debug("RECEIVED folderRequestDTO: "  + folderRequestDTO);
        }

        final FolderDTO folderDTO = folderRequestDTO.getFolderDTO();
        folderMapper.insertFolder(folderDTO);

        if(folderRequestDTO.getCheckedItem() != null) {
            insertFolderItemToDB(folderRequestDTO.getCheckedItem(), folderRequestDTO.getId());
        }
    }

    @Transactional
    public boolean deleteFolder(FolderDTO folderDTO) {
        if(folderDTO.getThumbnail() != null) {
            attachmentService.deleteImage(folderDTO.getThumbnail());
        }
        folderMapper.deleteById(folderDTO.getId());

        return true;
    }

    public List<Long> getBookmarkIdListInFolderById(long folderId) {
        final List<FolderItemDTO> folderItemDTOList = folderItemMapper.getAllByParentFolderOrderByIdDesc(folderId);
        return folderItemDTOList.stream().map(FolderItemDTO::getBookmarkId).collect(Collectors.toList());
    }

    @Transactional
    public void updateFolder(FolderRequestDTO folderRequestDTO) throws IOException {
        // 1. 섬네일이 기존에서 변경된 경우 (기존 섬네일을 지워야함.)
        if(folderRequestDTO.getThumbnail() != null && folderRequestDTO.getFolderThumbnail().getSize() != 0) {
            attachmentService.deleteImage(folderRequestDTO.getThumbnail());
            folderRequestDTO.setThumbnail(null);
        }

        // 2. 섬네일 삭제를 요구한 경우
        if(folderRequestDTO.getThumbnail() != null && folderRequestDTO.isDeleteRequest()) {
            attachmentService.deleteImage(folderRequestDTO.getThumbnail());
            folderRequestDTO.setThumbnail(null);
        }

        if(folderRequestDTO.getFolderThumbnail().getSize() != 0) {
            final String renamedFileName = attachmentService.saveFile(folderRequestDTO.getFolderThumbnail(), "folder_thumbnail");
            folderRequestDTO.setThumbnail(renamedFileName);
        }

        folderMapper.updateFolderById(folderRequestDTO.getFolderDTO());
        folderItemMapper.deleteByParentFolder(folderRequestDTO.getId());

        if(folderRequestDTO.getCheckedItem() != null) {
            insertFolderItemToDB(folderRequestDTO.getCheckedItem(), folderRequestDTO.getId());
        }
    }

    private void insertFolderItemToDB(String[] toInsertItem, long folderId) {
        List<FolderItemDTO> toAddFolderItemDTOList = new ArrayList<FolderItemDTO>();

        for (String itemId : toInsertItem) {
            FolderItemDTO folderItemDTO = new FolderItemDTO(Long.parseLong(itemId), folderId);
            toAddFolderItemDTOList.add(folderItemDTO);
        }

        log.debug("toAddFolderItemDTOList: " + toAddFolderItemDTOList);
        folderItemMapper.insertFolderItem(toAddFolderItemDTOList);
    }
}
