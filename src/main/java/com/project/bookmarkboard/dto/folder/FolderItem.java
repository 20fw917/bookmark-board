package com.project.bookmarkboard.dto.folder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderItem {
    private long id;
    private long bookmarkId;
    private long parentFolder;

    public FolderItem(long bookmarkId, long parentFolder) {
        this.bookmarkId = bookmarkId;
        this.parentFolder = parentFolder;
    }
}
