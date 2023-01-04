package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.bookmark.BookmarkViewPagination;
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

    public BookmarkViewPagination getSearchResult(String keyword, Long currentUserId, boolean currentUserOnly, int pageNum) {
        final int startItemNum = (pageNum - 1) * itemPerPage;

        // 비로그인 시
        if(currentUserId == null) {
            final int itemsCount = bookmarkMapper.getCountByIsSharedAndKeyword(true, keyword);
            final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

            final List<BookmarkView> bookmarkViewList = bookmarkViewMapper.getAllByIsSharedAndKeywordOrderByIdDescLimitByFromAndTo(true, keyword, startItemNum, itemPerPage);
            return new BookmarkViewPagination(itemsCount, pageNum, finalPageNum, bookmarkViewList);
        }

        // 로그인 시
        int itemsCount = bookmarkMapper.getCountByOwnerAndKeyword(currentUserId, keyword);
        List<BookmarkView> bookmarkViewList = bookmarkViewMapper.getAllByOwnerAndKeywordOrderByIdDescLimitByFromAndTo(currentUserId, keyword, startItemNum, itemPerPage);

        // 현재 유저 필터가 적용되어 있지 않은 경우
        if(!currentUserOnly) {
            itemsCount += bookmarkMapper.getCountByNotOwnerAndIsSharedAndKeyword(currentUserId, true, keyword);
            bookmarkViewList.addAll(bookmarkViewMapper.getAllByNotOwnerAndIsSharedAndKeywordOrderByIdDesc(currentUserId, keyword, true, startItemNum, itemPerPage));
        }

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;
        return new BookmarkViewPagination(itemsCount, pageNum, finalPageNum, bookmarkViewList);
    }

    public BookmarkViewPagination getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(long owner, int pageNum, boolean isStared) {
        final int itemsCount = bookmarkMapper.getCountByOwnerAndIsStared(owner, isStared);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<BookmarkView> bookmarkList = bookmarkViewMapper.getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(owner, isStared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new BookmarkViewPagination(itemsCount, pageNum, finalPageNum, bookmarkList);
    }

    public BookmarkViewPagination getAllByOwnerOrderByIdDescLimitByFromAndTo(long owner, int pageNum) {
        final int itemsCount = bookmarkMapper.getCountByOwner(owner);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<BookmarkView> bookmarkList = bookmarkViewMapper.getAllByOwnerOrderByIdDescLimitByFromAndTo(owner, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new BookmarkViewPagination(itemsCount, pageNum, finalPageNum, bookmarkList);
    }

    public BookmarkViewPagination getAllByIdListOrderByIsStaredDescAndIdDescLimitByFromAndTo(List<Long> idList, int pageNum, int itemsCount) {
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<BookmarkView> bookmarkList = bookmarkViewMapper.getAllByIdListOrderByIsStaredDescAndIdDescLimitByFromAndTo(idList, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new BookmarkViewPagination(itemsCount, pageNum, finalPageNum, bookmarkList);
    }

    public BookmarkViewPagination getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(long owner, int pageNum, int itemsCount, boolean isShared) {
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<BookmarkView> bookmarkList = bookmarkViewMapper.getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(owner, isShared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new BookmarkViewPagination(itemsCount, pageNum, finalPageNum, bookmarkList);
    }
}
