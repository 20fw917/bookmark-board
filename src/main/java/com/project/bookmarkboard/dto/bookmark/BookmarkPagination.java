package com.project.bookmarkboard.dto.bookmark;

import com.project.bookmarkboard.dto.pagination.BasicPagination;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkPagination extends BasicPagination {
    private List<Bookmark> bookmarkList;

    public BookmarkPagination(int totalCount, int currentPageNum, int finalPageNum, List<Bookmark> bookmarkList) {
        super.totalCount = totalCount;
        super.currentPageNum = currentPageNum;
        super.finalPageNum = finalPageNum;
        this.bookmarkList = bookmarkList;
    }

    public BasicPagination getPagination() {
        return new BasicPagination(totalCount, currentPageNum, finalPageNum);
    }
}
