package com.project.bookmarkboard.dto.folder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderLike {
    private long id;
    private long userId;
    private long folderId;

    public FolderLike(long userId, long folderId) {
        this.userId = userId;
        this.folderId = folderId;
    }
}
