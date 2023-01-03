package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.bookmark.Bookmark;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkMapper {
    Bookmark getOneById(@Param("id") long id);
    List<Bookmark> getAllByOwnerOrderByIdAndStaredDesc(@Param("owner") long owner);
    List<Bookmark> getAllByIdList(@Param("idList") List<Long> idList);
    List<Bookmark> getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(@Param("owner") long owner, @Param("isShared") boolean isShared,
                                                                         @Param("from") int from, @Param("itemPerPage") int itemPerPage);
    List<Bookmark> getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(@Param("owner") long owner, @Param("isStared") boolean isStared,
                                                                         @Param("from") int from, @Param("itemPerPage") int itemPerPage);
    List<Bookmark> getAllByOwnerOrderByIdDescLimitByFromAndTo(@Param("owner") long owner,
                                                              @Param("from") int from, @Param("itemPerPage") int itemPerPage);
    List<Bookmark> getAllByOwnerAndKeywordOrderByIsStaredDescAndIdDesc(@Param("owner") long owner, @Param("keyword") String keyword);
    List<Bookmark> getAllByIdListOrderByIsStaredDescAndIdDesc(@Param("idList") List<Long> idList);
    List<Bookmark> getAllByIdListOrderByIsStaredDescAndIdDescLimitByFromAndTo(@Param("idList") List<Long> idList,
                                                                              @Param("from") int from, @Param("itemPerPage") int itemPerPage);
    int insertBookmark(Bookmark bookmark);
    int insertBookmarkList(List<Bookmark> bookmarkList);
    int getCountByOwnerAndIsStared(@Param("owner") long owner, @Param("isStared") boolean isStared);
    int getCountByOwner(@Param("owner") long owner);
    int getCountByOwnerAndIsShared(@Param("owner") long owner, @Param("isShared") boolean isShared);
    int deleteBookmarkById(@Param("id") long id);
    int updateIsStaredById(@Param("id") long id, @Param("isStared") boolean isStared);
    int updateIsSharedById(@Param("id") long id, @Param("isShared") boolean isShared);
    int updateBookmarkById(Bookmark bookmark);
}
