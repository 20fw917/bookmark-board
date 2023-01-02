package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.FolderLikeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface FolderLikeMapper {
    List<FolderLikeDTO> getAllByUserId(@Param("userId") long userId);
    int getCountByFolderIdAndUserId(FolderLikeDTO folderLikeDTO);
    int insertFolderLike(FolderLikeDTO folderLikeDTO);
    int deleteFolderLikeById(FolderLikeDTO folderLikeDTO);
}
