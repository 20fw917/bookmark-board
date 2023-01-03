package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.bookmark.BookmarkLike;
import com.project.bookmarkboard.dto.folder.FolderLike;
import com.project.bookmarkboard.mapper.BookmarkLikeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookmarkLikeService {
    private final BookmarkLikeMapper bookmarkLikeMapper;

    public boolean getCountByBookmarkIdAndUserId(long userId, long bookmarkId) {
        final BookmarkLike bookmarkLike = new BookmarkLike(userId, bookmarkId);

        return bookmarkLikeMapper.getCountByBookmarkIdAndUserId(bookmarkLike) == 1;
    }

    @Transactional
    public void insertBookmarkLike(long userId, long bookmarkId) {
        final BookmarkLike bookmarkLike = new BookmarkLike(userId, bookmarkId);

        bookmarkLikeMapper.insertBookmarkLike(bookmarkLike);
    }

    @Transactional
    public void deleteBookmarkLikeByUserIdAndFolderId(long userId, long bookmarkId) {
        final BookmarkLike bookmarkLike = new BookmarkLike(userId, bookmarkId);

        bookmarkLikeMapper.deleteBookmarkLikeByUserIdAndBookmarkId(bookmarkLike);
    }
}
