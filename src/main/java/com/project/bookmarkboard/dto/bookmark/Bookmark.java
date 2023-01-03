package com.project.bookmarkboard.dto.bookmark;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
    private long id;
    private long owner;
    private String title;
    private String memo;
    private String url;
    private LocalDateTime createdAt;
    private boolean isShared;
    private boolean isStared;

    public String getCreatedAtFormatted() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
