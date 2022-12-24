package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.BookmarkDTO;
import com.project.bookmarkboard.dto.BookmarkPagination;
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
    private Integer itemsPerPage;

    public BookmarkPagination getAllByOwnerOrderByIdDescLimitByFromAndTo(long owner, int pageNum, boolean isStared) {
        final int itemsCount = bookmarkMapper.getCountByOwner(owner, isStared);
        final int startItemNum = (pageNum - 1) * itemsPerPage;
        final int endPageItemNum = pageNum * itemsPerPage;

        final List<BookmarkDTO> bookmarkDTOList = bookmarkMapper.getAllByOwnerAndIsStaredOrderByIdDescLimitByFromAndTo(owner, isStared, startItemNum, endPageItemNum);

        final int finalPageNum = ((itemsCount - 1) / itemsPerPage) + 1;

        return new BookmarkPagination(itemsCount, pageNum, finalPageNum, bookmarkDTOList);
    }
}
