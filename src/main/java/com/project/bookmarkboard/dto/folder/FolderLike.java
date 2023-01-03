package com.project.bookmarkboard.dto.folder;

import com.project.bookmarkboard.dto.basic.BasicLike;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderLike extends BasicLike {
    private long folderId;

    public FolderLike(long userId, long folderId) {
        super(userId);
        this.folderId = folderId;
    }
}
