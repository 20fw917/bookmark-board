package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.bookmark.BookmarkLike;
import com.project.bookmarkboard.dto.folder.FolderLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookmarkLikeMapper {
    List<BookmarkLike> getAllByUserId(@Param("userId") long userId);
    int getCountByBookmarkIdAndUserId(BookmarkLike bookmarkLike);
    int insertBookmarkLike(BookmarkLike bookmarkLike);
    int deleteBookmarkLikeByUserIdAndBookmarkId(BookmarkLike bookmarkLike);
}
