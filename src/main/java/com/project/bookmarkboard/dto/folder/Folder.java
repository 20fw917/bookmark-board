package com.project.bookmarkboard.dto.folder;

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
public class Folder {
    private long id;
    private long owner;
    private String title;
    private String memo;
    private String thumbnail;
    private boolean isShared;
    private boolean isStared;
    private LocalDateTime createdAt;

    public String getCreatedAtFormatted() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
