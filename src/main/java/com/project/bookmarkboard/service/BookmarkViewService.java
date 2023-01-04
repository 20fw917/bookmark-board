package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.bookmark.BookmarkPagination;
import com.project.bookmarkboard.dto.bookmark.BookmarkView;
import com.project.bookmarkboard.mapper.BookmarkMapper;
import com.project.bookmarkboard.mapper.BookmarkViewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookmarkViewService {
    private final BookmarkViewMapper bookmarkViewMapper;
    private final BookmarkMapper bookmarkMapper;
    private final BookmarkLikeService bookmarkLikeService;

    @Value("${items-per-page}")
    private Integer itemPerPage;

    public List<BookmarkView> getLikeStatus(List<BookmarkView> bookmarkViewList, long userId) {
        return bookmarkViewList.stream()
                .peek(bookmarkView -> bookmarkView.setIsLiked(bookmarkLikeService.getCountByBookmarkIdAndUserId(userId, bookmarkView.getId())))
                .collect(Collectors.toList());
    }

    public BookmarkView getLikeStatus(BookmarkView bookmarkView, long userId) {
        bookmarkView.setIsLiked(bookmarkLikeService.getCountByBookmarkIdAndUserId(userId, bookmarkView.getId()));
        return bookmarkView;
    }

    public BookmarkPagination getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(long owner, int pageNum, boolean isStared) {
        final int itemsCount = bookmarkMapper.getCountByOwnerAndIsStared(owner, isStared);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<BookmarkView> bookmarkList = bookmarkViewMapper.getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(owner, isStared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new BookmarkPagination(itemsCount, pageNum, finalPageNum, bookmarkList);
    }

    public BookmarkPagination getAllByOwnerOrderByIdDescLimitByFromAndTo(long owner, int pageNum) {
        final int itemsCount = bookmarkMapper.getCountByOwner(owner);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<BookmarkView> bookmarkList = bookmarkViewMapper.getAllByOwnerOrderByIdDescLimitByFromAndTo(owner, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new BookmarkPagination(itemsCount, pageNum, finalPageNum, bookmarkList);
    }

    public BookmarkPagination getAllByIdListOrderByIsStaredDescAndIdDescLimitByFromAndTo(List<Long> idList, int pageNum, int itemsCount) {
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<BookmarkView> bookmarkList = bookmarkViewMapper.getAllByIdListOrderByIsStaredDescAndIdDescLimitByFromAndTo(idList, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new BookmarkPagination(itemsCount, pageNum, finalPageNum, bookmarkList);
    }

    public BookmarkPagination getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(long owner, int pageNum, int itemsCount, boolean isShared) {
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<BookmarkView> bookmarkList = bookmarkViewMapper.getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(owner, isShared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new BookmarkPagination(itemsCount, pageNum, finalPageNum, bookmarkList);
    }
}
