package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.user.CustomUserDetails;
import com.project.bookmarkboard.dto.user.User;
import com.project.bookmarkboard.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DB에서 Account 객체 조회
        final User user = userMapper.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        // 권한 정보 등록
        List<GrantedAuthority> roles = new ArrayList<>(List.of(new SimpleGrantedAuthority(user.getRole())));

        // AccountContext 생성자로 UserDetails 타입 생성
        return new CustomUserDetails(user, roles);
    }

    public Authentication createNewAuthentication(Authentication currentAuth, String username) {
        UserDetails userDetails = loadUserByUsername(username);
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(userDetails, currentAuth.getCredentials(), userDetails.getAuthorities());
        newAuth.setDetails(currentAuth.getDetails());
        return newAuth;
    }
}