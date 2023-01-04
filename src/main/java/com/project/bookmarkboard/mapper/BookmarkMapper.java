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
    List<Bookmark> getAllByOwnerAndKeywordOrderByIsStaredDescAndIdDesc(@Param("owner") long owner, @Param("keyword") String keyword);
    List<Bookmark> getAllByIdListOrderByIsStaredDescAndIdDesc(@Param("idList") List<Long> idList);
    int insertBookmark(Bookmark bookmark);
    int insertBookmarkList(List<Bookmark> bookmarkList);
    int getCountByOwnerAndIsStared(@Param("owner") long owner, @Param("isStared") boolean isStared);
    int getCountByOwner(@Param("owner") long owner);
    int getCountByOwnerAndIsShared(@Param("owner") long owner, @Param("isShared") boolean isShared);
    int getCountByIsSharedAndKeyword(@Param("isShared") boolean isShared, @Param("keyword") String keyword);
    int getCountByOwnerAndKeyword(@Param("owner") long owner, @Param("keyword") String keyword);
    int getCountByNotOwnerAndIsSharedAndKeyword(@Param("owner") long owner, @Param("isShared") boolean isShared, @Param("keyword") String keyword);
    int deleteBookmarkById(@Param("id") long id);
    int updateIsStaredById(@Param("id") long id, @Param("isStared") boolean isStared);
    int updateIsSharedById(@Param("id") long id, @Param("isShared") boolean isShared);
    int updateBookmarkById(Bookmark bookmark);
}
