package com.project.bookmarkboard.dto.folder;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderView extends Folder {
    // 폴더 내 Item 갯수
    private int itemCount;
    // 만든 유저의 닉네임
    private String authorNickname;
    // 좋아요 갯수
    private int likeCount;
    // 좋아요 여부
    private Boolean isLiked;

    public FolderView(long id, long owner, String title, String memo, String thumbnail, boolean isShared, boolean isStared,
                      LocalDateTime createdAt, int itemCount, String authorNickname, int likeCount, Boolean isLiked) {
        super(id, owner, title, memo, thumbnail, isShared, isStared, createdAt);
        this.itemCount = itemCount;
        this.authorNickname = authorNickname;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
    }
}
