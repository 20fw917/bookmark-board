package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.BookmarkDTO;
import com.project.bookmarkboard.dto.BookmarkPagination;
import com.project.bookmarkboard.mapper.BookmarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkMapper bookmarkMapper;

    @Value("${items-per-page}")
    private Integer itemsPerPage;

    public BookmarkPagination getAllByOwnerOrderByIdDescLimitByFromAndTo(long owner, int pageNum) {
        final int itemsCount = bookmarkMapper.getCountByOwner(owner);
        final int startItemNum = pageNum * itemsPerPage;
        final int endPageItemNum = (pageNum + 1) * itemsPerPage;
        final List<BookmarkDTO> bookmarkDTOList = bookmarkMapper.getAllByOwnerOrderByIdDescLimitByFromAndTo(owner, startItemNum, endPageItemNum);

        final int finalPageNum = (itemsCount / itemsPerPage) + 1;

        return new BookmarkPagination(itemsCount, pageNum, finalPageNum, bookmarkDTOList);
    }
}
