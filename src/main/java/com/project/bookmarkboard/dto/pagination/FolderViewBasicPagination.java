package com.project.bookmarkboard.dto.pagination;

import com.project.bookmarkboard.dto.FolderViewDTO;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderViewBasicPagination extends BasicPagination {
    private List<FolderViewDTO> folderViewDTOList;

    public FolderViewBasicPagination(int totalCount, int currentPageNum, int finalPageNum, List<FolderViewDTO> folderViewDTOList) {
        super.totalCount = totalCount;
        super.currentPageNum = currentPageNum;
        super.finalPageNum = finalPageNum;
        this.folderViewDTOList = folderViewDTOList;
    }

    public BasicPagination getPagination() {
        return new BasicPagination(totalCount, currentPageNum, finalPageNum);
    }
}
