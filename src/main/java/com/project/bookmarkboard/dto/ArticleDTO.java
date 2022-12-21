package com.project.bookmarkboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private long id;
    private long authorId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int viewCount;
}
