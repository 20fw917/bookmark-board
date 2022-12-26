
package com.project.bookmarkboard.mapper;

import com.project.bookmarkboard.dto.FolderViewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FolderViewMapper {
    FolderViewDTO getOneById(@Param("id") long id);
    List<FolderViewDTO> getAllByOwnerAndIsStaredOrderByIsStaredAndIdDescLimitByFromAndTo(@Param("owner") long owner, @Param("careStared") boolean careStared,
                                                                            @Param("from") int from, @Param("to") int to);
}
