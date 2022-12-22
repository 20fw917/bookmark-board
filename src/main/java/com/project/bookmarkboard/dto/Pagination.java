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
    // 현재 페이지의 번호
    protected int currentPageNum;
    // 마지막 페이지의 번호
    protected int finalPageNum;
}
