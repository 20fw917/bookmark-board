package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByUsername(@Param("username") String username);
    User getOneByInternalId(@Param("internalId") long internalId);
    int countByUsername(@Param("username") String username);
    int countByEmail(@Param("email") String email);
    int insertUser(User user);
    int updateUser(User user);
    int countByNickname(@Param("nickname") String nickname);
    int updateProfileImageByInternalId(User user);
}
