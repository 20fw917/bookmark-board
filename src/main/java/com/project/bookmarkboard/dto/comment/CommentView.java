package com.project.bookmarkboard.dto.comment;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentView extends Comment {
    private String authorNickname;
}
