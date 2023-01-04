package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.folder.Folder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FolderMapper {
    int getCountByOwner(@Param("owner") long owner);
    int getCountByNotOwnerAndIsShared(@Param("owner") long owner, @Param("isShared") boolean isShared);
    int getCountByIsShared(@Param("isShared") boolean isShared);
    int getCountByIsSharedAndKeyword(@Param("isShared") boolean isShared, @Param("keyword") String keyword);
    int getCountByOwnerAndKeyword(@Param("owner") long owner, @Param("keyword") String keyword);
    int getCountByNotOwnerAndIsSharedAndKeyword(@Param("owner") long owner, @Param("isShared") boolean isShared, @Param("keyword") String keyword);
    int getCountByOwnerAndIsStared(@Param("owner") long owner, @Param("isStared") boolean isStared);
    int getCountByOwnerAndIsShared(@Param("owner") long owner, @Param("isShared") boolean isShared);
    int deleteById(@Param("id") long id);
    Folder getOneById(@Param("id") long id);
    int updateIsStaredById(@Param("id") long id, @Param("isStared") boolean isStared);
    int updateIsSharedById(@Param("id") long id, @Param("isShared") boolean isShared);
    long insertFolder(Folder folder);
    int updateFolderById(Folder folder);
}
