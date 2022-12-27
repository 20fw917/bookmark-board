package com.project.bookmarkboard.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderRequestDTO extends FolderDTO {
    private MultipartFile folderThumbnail;
    private String[] checkedItem;
    private boolean deleteRequest;

    public FolderDTO getFolderDTO() {
        return new FolderDTO(super.getId(), super.getOwner(), super.getTitle(), super.getMemo(), super.getThumbnail(),
                super.isShared(), super.isStared());
    }
}
