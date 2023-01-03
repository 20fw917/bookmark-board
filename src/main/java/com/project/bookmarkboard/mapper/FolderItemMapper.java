package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.folder.FolderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FolderItemMapper {
    int insertFolderItem(List<FolderItem> folderItemList);
    List<FolderItem> getAllByParentFolderOrderByIdDesc(@Param("id") long id);
    int deleteByParentFolder(@Param("parentFolderId") long parentFolderId);
}
