package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.BookmarkDTO;
import com.project.bookmarkboard.dto.pagination.BookmarkPagination;
import com.project.bookmarkboard.mapper.BookmarkMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkMapper bookmarkMapper;

    @Value("${items-per-page}")
    private Integer itemPerPage;

    public BookmarkPagination getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(long owner, int pageNum, boolean isStared) {
        final int itemsCount = bookmarkMapper.getCountByOwnerAndIsStared(owner, isStared);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<BookmarkDTO> bookmarkDTOList = bookmarkMapper.getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(owner, isStared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new BookmarkPagination(itemsCount, pageNum, finalPageNum, bookmarkDTOList);
    }

    public BookmarkPagination getAllByOwnerOrderByIdDescLimitByFromAndTo(long owner, int pageNum) {
        final int itemsCount = bookmarkMapper.getCountByOwner(owner);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<BookmarkDTO> bookmarkDTOList = bookmarkMapper.getAllByOwnerOrderByIdDescLimitByFromAndTo(owner, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new BookmarkPagination(itemsCount, pageNum, finalPageNum, bookmarkDTOList);
    }


    public BookmarkPagination getAllByIdListOrderByIsStaredDescAndIdDescLimitByFromAndTo(List<Long> idList, int pageNum, int itemsCount) {
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<BookmarkDTO> bookmarkDTOList = bookmarkMapper.getAllByIdListOrderByIsStaredDescAndIdDescLimitByFromAndTo(idList, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new BookmarkPagination(itemsCount, pageNum, finalPageNum, bookmarkDTOList);
    }

    public BookmarkPagination getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(long owner, int pageNum, int itemsCount, boolean isShared) {
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<BookmarkDTO> bookmarkDTOList = bookmarkMapper.getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(owner, isShared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new BookmarkPagination(itemsCount, pageNum, finalPageNum, bookmarkDTOList);
    }
}
