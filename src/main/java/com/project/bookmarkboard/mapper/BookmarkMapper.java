package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.BookmarkDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkMapper {
    BookmarkDTO getOneById(@Param("id") long id);
    List<BookmarkDTO> getAllByOwnerOrderByIdAndStaredDesc(@Param("owner") long owner);
    List<BookmarkDTO> getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(@Param("owner") long owner, @Param("isStared") boolean isStared,
                                                                            @Param("from") int from, @Param("to") int to);
    List<BookmarkDTO> getAllByOwnerAndKeywordOrderByIsStaredDescAndIdDesc(@Param("owner") long owner, @Param("keyword") String keyword);
    List<BookmarkDTO> getAllByIdListOrderByIsStaredDescAndIdDesc(@Param("idList") List<Long> idList);
    List<BookmarkDTO> getAllByIdListOrderByIsStaredDescAndIdDescLimitByFromAndTo(@Param("idList") List<Long> idList,
                                                                                 @Param("from") int from, @Param("to") int to);
    int insertBookmark(BookmarkDTO bookmarkDTO);
    int getCountByOwnerAndIsStared(@Param("owner") long owner, @Param("isStared") boolean isStared);
    int getCountByOwner(@Param("owner") long owner);
    int getCountByOwnerAndIsShared(@Param("owner") long owner, @Param("isShared") boolean isShared);
    int deleteBookmarkById(@Param("id") long id);
    int updateIsStaredById(@Param("id") long id, @Param("isStared") boolean isStared);
    int updateIsSharedById(@Param("id") long id, @Param("isShared") boolean isShared);
    int updateBookmarkById(BookmarkDTO bookmarkDTO);
}
