package com.project.bookmarkboard.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleViewDTO extends ArticleDTO {
    private long authorId;
    private String authorNickname;
    private int likeCount;
}