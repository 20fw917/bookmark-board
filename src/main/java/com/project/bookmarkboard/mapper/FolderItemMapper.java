package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.FolderItemDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FolderItemMapper {
    int insertFolderItem(List<FolderItemDTO> folderItemDTOList);
}
