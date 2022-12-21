package com.project.bookmarkboard.controller;

import com.project.bookmarkboard.dto.ArticleDTO;
import com.project.bookmarkboard.dto.CustomUserDetails;
import com.project.bookmarkboard.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Log4j2
@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleMapper articleMapper;

    @PostMapping("/write")
    public String postWrite(ArticleDTO articleDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        articleDTO.setAuthorId(customUserDetails.getUserInternalId());
        articleDTO.setCreatedAt(LocalDateTime.now());

        log.info(articleMapper.insertArticle(articleDTO));

        return "redirect:/";
    }
}
