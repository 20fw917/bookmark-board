package com.project.bookmarkboard.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentViewDTO extends CommentDTO {
    private String authorNickname;
}
