package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.folder.FolderLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface FolderLikeMapper {
    List<FolderLike> getAllByUserId(@Param("userId") long userId);
    int getCountByFolderIdAndUserId(FolderLike folderLike);
    int insertFolderLike(FolderLike folderLike);
    int deleteFolderLikeByUserIdAndFolderId(FolderLike folderLike);
}
