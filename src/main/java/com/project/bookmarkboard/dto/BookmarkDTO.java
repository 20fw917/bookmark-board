package com.project.bookmarkboard.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDTO {
    private long id;
    private long owner;
    private String title;
    private String memo;
    private String url;
    private LocalDateTime createdAt;
    private boolean isShared;
}
