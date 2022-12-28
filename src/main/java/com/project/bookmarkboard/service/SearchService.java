package com.project.bookmarkboard.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookmarkboard.dto.FolderDTO;
import com.project.bookmarkboard.mapper.SearchMapper;

@Service
public class SearchService {
	//서비스에선 mapper에 등록해둔 sql 실행
	
	@Autowired
	SearchMapper mapper;
	
	public List<FolderDTO> getAllFromMyFolder(@Param("searchkeyword") String searchkeyword, @Param("owner") long owner){
		return mapper.getAllFromMyFolder(searchkeyword, owner);
	};
	public List<FolderDTO> getAllFromOurFolder(@Param("searchkeyword") String searchkeyword, @Param("isShared") boolean isShared){
		return mapper.getAllFromOurFolder(searchkeyword);
	};
	
	
}
