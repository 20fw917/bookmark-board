package com.project.bookmarkboard.dto.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicLike {
    private long id;
    private long userId;

    public BasicLike(long userId) {
        this.userId = userId;
    }
}
