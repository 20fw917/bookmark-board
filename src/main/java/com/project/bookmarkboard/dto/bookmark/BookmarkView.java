package com.project.bookmarkboard.dto.bookmark;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkView extends Bookmark {
    // 만든 유저의 닉네임
    private String authorNickname;
    // 좋아요 갯수
    private int likeCount;
    // 좋아요 여부
    private Boolean isLiked;

    public BookmarkView(long id, long owner, String title, String memo, String thumbnail, boolean isShared, boolean isStared,
                        LocalDateTime createdAt, String authorNickname, int likeCount, Boolean isLiked) {
        super(id, owner, title, memo, thumbnail, createdAt, isShared, isStared);
        this.authorNickname = authorNickname;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
    }
}
