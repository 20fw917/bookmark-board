package com.project.bookmarkboard.dto;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkPagination extends Pagination {
    private List<BookmarkDTO> bookmarkDTOList;

    public BookmarkPagination(int totalCount, int currentPageNum, int finalPageNum, List<BookmarkDTO> bookmarkDTOList) {
        super.totalCount = totalCount;
        super.currentPageNum = currentPageNum;
        super.finalPageNum = finalPageNum;
        this.bookmarkDTOList = bookmarkDTOList;
    }

    public Pagination getPagination() {
        return new Pagination(totalCount, currentPageNum, finalPageNum);
    }
}
