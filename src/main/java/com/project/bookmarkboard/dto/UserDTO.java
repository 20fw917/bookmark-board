package com.project.bookmarkboard.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class UserDTO extends User implements Serializable {
    private String passwordRe;

    public User toUser() {
        return new User(super.getInternalId(), super.getUsername(), super.getNickname(), super.getPassword(),
                super.getEmail(), super.getRole());
    }
}
