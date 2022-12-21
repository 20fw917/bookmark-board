package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.ArticleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    int insertArticle(ArticleDTO articleDTO);
    List<ArticleDTO> findAllOrderByCreatedAt();
}
