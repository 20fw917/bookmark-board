package com.project.bookmarkboard.dto.folder;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderRequest extends Folder {
    private MultipartFile folderThumbnail;
    private String[] checkedItem;
    private boolean deleteRequest;

    public Folder getFolderDTO() {
        return new Folder(super.getId(), super.getOwner(), super.getTitle(), super.getMemo(), super.getThumbnail(),
                super.isShared(), super.isStared(), super.getCreatedAt());
    }
}

