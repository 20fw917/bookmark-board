package com.project.bookmarkboard.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FolderMapper {
    int getCountByOwner(@Param("owner") long owner);
}
