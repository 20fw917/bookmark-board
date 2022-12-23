package com.project.bookmarkboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    /*
        페이징 기능 구현을 위해 현재 페이지의 정보를 담는 DTO
     */

    // 전체 Item의 갯수
    protected int totalCount;
    // 시작하는 Index 번호
    protected int startIndexNum;
    // 현재 페이지의 번호
    protected int currentPageNum;
    // 마지막 페이지의 번호
    protected int finalPageNum;
    // 이전 페이지 여부
    protected boolean isPreviousPageExists;
    // 다음 페이지 여부
    protected boolean isNextPageExists;

    public Pagination(int totalCount, int currentPageNum, int finalPageNum) {
        this.totalCount = totalCount;
        this.startIndexNum = (currentPageNum - (currentPageNum % 10)) + 1;
        this.currentPageNum = currentPageNum;
        this.finalPageNum = finalPageNum;
        this.isPreviousPageExists = currentPageNum - 10 > 0;
        this.isNextPageExists = this.startIndexNum + 9 < finalPageNum;
    }
}
