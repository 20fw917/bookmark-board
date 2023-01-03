
package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.bookmark.BookmarkView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkViewMapper {
    List<BookmarkView> getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(@Param("owner") long owner, @Param("isShared") boolean isShared,
                                                                             @Param("from") int from, @Param("itemPerPage") int itemPerPage);
    List<BookmarkView> getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(@Param("owner") long owner, @Param("isStared") boolean isStared,
                                                                         @Param("from") int from, @Param("itemPerPage") int itemPerPage);
    List<BookmarkView> getAllByOwnerOrderByIdDescLimitByFromAndTo(@Param("owner") long owner,
                                                              @Param("from") int from, @Param("itemPerPage") int itemPerPage);

    List<BookmarkView> getAllByIdListOrderByIsStaredDescAndIdDescLimitByFromAndTo(@Param("idList") List<Long> idList,
                                                                              @Param("from") int from, @Param("itemPerPage") int itemPerPage);

}