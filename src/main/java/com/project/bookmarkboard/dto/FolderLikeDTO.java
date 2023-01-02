package com.project.bookmarkboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderLikeDTO {
    private long id;
    private long userId;
    private long folderId;

    public FolderLikeDTO(long userId, long folderId) {
        this.userId = userId;
        this.folderId = folderId;
    }
}
