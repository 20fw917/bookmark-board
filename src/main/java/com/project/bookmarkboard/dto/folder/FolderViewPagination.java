package com.project.bookmarkboard.dto.folder;

import com.project.bookmarkboard.dto.pagination.BasicPagination;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderViewPagination extends BasicPagination {
    private List<FolderView> folderViewList;

    public FolderViewPagination(int totalCount, int currentPageNum, int finalPageNum, List<FolderView> folderViewList) {
        super.totalCount = totalCount;
        super.currentPageNum = currentPageNum;
        super.finalPageNum = finalPageNum;
        this.folderViewList = folderViewList;
    }

    public BasicPagination getPagination() {
        return new BasicPagination(totalCount, currentPageNum, finalPageNum);
    }
}
