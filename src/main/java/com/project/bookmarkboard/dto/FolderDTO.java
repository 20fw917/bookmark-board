package com.project.bookmarkboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderDTO {
    private long id;
    private long owner;
    private String title;
    private String memo;
    private String thumbnail;
    private boolean isShared;
    private boolean isStared;
}
