package com.project.bookmarkboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private long id;
    private long authorId;
    private long articleId;
    private String content;
    private LocalDateTime createdAt;
    private Long rootCommentId;
}
