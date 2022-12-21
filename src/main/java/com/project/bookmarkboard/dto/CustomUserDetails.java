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

    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.userInternalId = user.getInternalId();
    }
}
