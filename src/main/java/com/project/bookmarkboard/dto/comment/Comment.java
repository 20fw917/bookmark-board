package com.project.bookmarkboard.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private long id;
    private long authorId;
    private long folderId;
    private String content;
    private LocalDateTime createdAt;
}
