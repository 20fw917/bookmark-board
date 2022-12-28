package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.FolderDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MainMapper {
	List<FolderDTO> getAllFromMyFolderis_stared(@Param("owner") long owner);
	// int getAllFromOurFolderis_recommended(@Param("isShared") boolean isShared);
}
