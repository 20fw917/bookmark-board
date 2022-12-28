package com.project.bookmarkboard.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Getter
@ToString(callSuper = true)
public class CustomUserDetails extends org.springframework.security.core.userdetails.User implements Serializable {
    static final long serialVersionUID = 42L;

    private final long userInternalId;
    private final String email;
    private final String nickname;
    private final String profileImage;

    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.userInternalId = user.getInternalId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
    }

    public User toUserDTO() {
        return new User(getUserInternalId(), getUsername(), getPassword(), getEmail(), getNickname(),
                getProfileImage(), UserRole.USER.toString());
    }
}
