package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.FolderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FolderMapper {
    int getCountByOwner(@Param("owner") long owner);
    int getCountByNotOwnerAndIsShared(@Param("owner") long owner, @Param("isShared") boolean isShared);
    int getCountByIsShared(@Param("isShared") boolean isShared);
    int getCountByOwnerAndIsStared(@Param("owner") long owner, @Param("isStared") boolean isStared);
    int getCountByOwnerAndIsShared(@Param("owner") long owner, @Param("isShared") boolean isShared);
    int deleteById(@Param("id") long id);
    FolderDTO getOneById(@Param("id") long id);
    int updateIsStaredById(@Param("id") long id, @Param("isStared") boolean isStared);
    int updateIsSharedById(@Param("id") long id, @Param("isShared") boolean isShared);
    long insertFolder(FolderDTO folderDTO);
    int updateFolderById(FolderDTO folderDTO);
}
