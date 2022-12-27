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
public class FolderItemDTO {
    private long id;
    private long bookmarkId;
    private long parentFolder;
    private LocalDateTime createdAt;

    public FolderItemDTO(long bookmarkId, long parentFolder) {
        this.bookmarkId = bookmarkId;
        this.parentFolder = parentFolder;
    }
}
