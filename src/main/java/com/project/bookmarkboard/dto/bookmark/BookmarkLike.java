package com.project.bookmarkboard.dto.bookmark;

import com.project.bookmarkboard.dto.basic.BasicLike;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkLike extends BasicLike {
    private long bookmarkId;

    public BookmarkLike(long userId, long bookmarkId) {
        super(userId);
        this.bookmarkId = bookmarkId;
    }
}
