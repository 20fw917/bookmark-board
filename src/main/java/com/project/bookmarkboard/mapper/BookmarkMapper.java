package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.BookmarkDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkMapper {
    List<BookmarkDTO> getAllByOwnerOrderByIdDesc(@Param("owner") long owner);
    List<BookmarkDTO> getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(@Param("owner") long owner, boolean isStared, int from, int to);
    int insertBookmark(BookmarkDTO bookmarkDTO);
    int getCountByOwner(@Param("owner") long owner, boolean isStared);
    int deleteBookmarkById(@Param("id") long id);
}
