package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.FolderItemDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FolderItemMapper {
    int insertFolderItem(List<FolderItemDTO> folderItemDTOList);
    List<FolderItemDTO> getAllByParentFolderOrderByIdDesc(@Param("id") long id);
    int deleteByParentFolder(@Param("parentFolderId") long parentFolderId);
}
