package com.project.bookmarkboard.dto.bookmark;

import com.project.bookmarkboard.dto.basic.BasicPagination;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkPagination extends BasicPagination {
    private List<BookmarkView> bookmarkViewList;

    public BookmarkPagination(int totalCount, int currentPageNum, int finalPageNum, List<BookmarkView> bookmarkViewList) {
        super.totalCount = totalCount;
        super.currentPageNum = currentPageNum;
        super.finalPageNum = finalPageNum;
        this.bookmarkViewList = bookmarkViewList;
    }

    public BasicPagination getPagination() {
        return new BasicPagination(totalCount, currentPageNum, finalPageNum);
    }
}
