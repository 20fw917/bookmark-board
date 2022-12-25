package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.BookmarkDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkMapper {
    BookmarkDTO getOneById(@Param("id") long id);
    List<BookmarkDTO> getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(@Param("owner") long owner, @Param("isStared") boolean isStared,
                                                                            @Param("from") int from, @Param("to") int to);
    int insertBookmark(BookmarkDTO bookmarkDTO);
    int getCountByOwner(@Param("owner") long owner, @Param("isStared") boolean isStared);
    int deleteBookmarkById(@Param("id") long id);
    int updateIsStaredById(@Param("id") long id, @Param("isStared") boolean isStared);
    int updateIsSharedById(@Param("id") long id, @Param("isShared") boolean isShared);
}
