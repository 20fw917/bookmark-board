package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.FolderDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SearchMapper {
	List<FolderDTO> getAllFromMyFolder(@Param("searchkeyword") String searchkeyword, @Param("owner") long owner);
	List<FolderDTO> getAllFromOurFolder(@Param("searchkeyword") String searchkeyword, @Param("isShared") boolean isShared);
}
