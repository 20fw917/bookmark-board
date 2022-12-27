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
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class FolderService {
    private final AttachmentService attachmentService;
    private final FolderMapper folderMapper;
    private final FolderItemMapper folderItemMapper;

    @Transactional
    public void insertFolder(FolderRequestDTO folderRequestDTO) throws IOException  {
        final String renamedFileName = attachmentService.saveFile(folderRequestDTO.getFolderThumbnail(), "folder_thumbnail");
        folderRequestDTO.setThumbnail(renamedFileName);

        log.debug("RECEIVED folderRequestDTO: "  + folderRequestDTO);
        final FolderDTO folderDTO = folderRequestDTO.getFolderDTO();
        folderMapper.insertFolder(folderDTO);

        List<FolderItemDTO> toAddFolderItemDTOList = new ArrayList<FolderItemDTO>();
        for(String itemId : folderRequestDTO.getCheckedItem()) {
            FolderItemDTO folderItemDTO = new FolderItemDTO(Long.parseLong(itemId), folderDTO.getId());
            toAddFolderItemDTOList.add(folderItemDTO);
        }

        log.debug("toAddFolderItemDTOList: " + toAddFolderItemDTOList);
        folderItemMapper.insertFolderItem(toAddFolderItemDTOList);
    }

    @Transactional
    public boolean deleteFolder(FolderDTO folderDTO) {
        if(folderDTO.getThumbnail() != null) {
            attachmentService.deleteImage(folderDTO.getThumbnail());
        }
        folderMapper.deleteById(folderDTO.getId());

        return true;
    }
}
