package com.project.bookmarkboard.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderViewDTO extends FolderDTO {
    // 폴더 내 Item 갯수
    private int itemCount;
}
