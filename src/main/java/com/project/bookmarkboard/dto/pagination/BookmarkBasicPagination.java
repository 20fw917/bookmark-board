package com.project.bookmarkboard.dto.pagination;

import com.project.bookmarkboard.dto.BookmarkDTO;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkBasicPagination extends BasicPagination {
    private List<BookmarkDTO> bookmarkDTOList;

    public BookmarkBasicPagination(int totalCount, int currentPageNum, int finalPageNum, List<BookmarkDTO> bookmarkDTOList) {
        super.totalCount = totalCount;
        super.currentPageNum = currentPageNum;
        super.finalPageNum = finalPageNum;
        this.bookmarkDTOList = bookmarkDTOList;
    }

    public BasicPagination getPagination() {
        return new BasicPagination(totalCount, currentPageNum, finalPageNum);
    }
}
