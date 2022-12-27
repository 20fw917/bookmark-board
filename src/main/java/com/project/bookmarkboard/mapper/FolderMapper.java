package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.FolderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FolderMapper {
    int getCountByOwner(@Param("owner") long owner);
    int deleteById(@Param("id") long id);
    FolderDTO getOneById(@Param("id") long id);
    int updateIsStaredById(@Param("id") long id, @Param("isStared") boolean isStared);
    long insertFolder(FolderDTO folderDTO);
}
