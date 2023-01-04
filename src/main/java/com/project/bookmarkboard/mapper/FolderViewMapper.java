
package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.folder.FolderView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FolderViewMapper {
    FolderView getOneById(@Param("id") long id);
    List<FolderView> getAllByOwnerOrderByIsStaredAndIdDescLimitByFromAndTo(@Param("owner") long owner,
                                                                           @Param("careStared") boolean careStared,
                                                                           @Param("from") int from, @Param("itemPerPage") int itemPerPage);

    List<FolderView> getAllByOwnerAndIsSharedOrderByIsStaredAndIdDescLimitByFromAndTo(@Param("owner") long owner,
                                                                                      @Param("isShared") boolean isShared,
                                                                                      @Param("from") int from, @Param("itemPerPage") int itemPerPage);
    List<FolderView> getAllByOwnerAndIsStaredOrderById(@Param("owner") long owner, @Param("isStared") boolean isStared,
                                                       @Param("from") int from, @Param("itemPerPage") int itemPerPage);

    List<FolderView> getAllByNotOwnerAndIsSharedOrderByLikeCountLimitByFromAndTo(@Param("owner") long owner, @Param("isShared") boolean isShared,
                                                                                 @Param("from") int from, @Param("itemPerPage") int itemPerPage);

    List<FolderView> getAllByIsSharedOrderByLikeCountLimitByFromAndTo(@Param("isShared") boolean isShared,
                                                                      @Param("from") int from, @Param("itemPerPage") int itemPerPage);
}