package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.FolderViewDTO;
import com.project.bookmarkboard.dto.pagination.FolderViewPagination;
import com.project.bookmarkboard.mapper.FolderMapper;
import com.project.bookmarkboard.mapper.FolderViewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderViewService {
    private final FolderViewMapper folderViewMapper;
    private final FolderMapper folderMapper;

    @Value("${items-per-page}")
    private Integer itemPerPage;

    public FolderViewPagination getAllByOwnerOrderByIsStaredAndIdDescLimitByFromAndTo(long owner, int pageNum, boolean careStared) {
        final int itemsCount = folderMapper.getCountByOwner(owner);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<FolderViewDTO> folderViewDTOList = folderViewMapper.getAllByOwnerOrderByIsStaredAndIdDescLimitByFromAndTo(owner, careStared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new FolderViewPagination(itemsCount, pageNum, finalPageNum, folderViewDTOList);
    }

    public FolderViewPagination getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(long owner, int pageNum, boolean isShared) {
        final int itemsCount = folderMapper.getCountByOwnerAndIsShared(owner, isShared);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<FolderViewDTO> folderViewDTOList = folderViewMapper.getAllByOwnerAndIsSharedOrderByIsStaredAndIdDescLimitByFromAndTo(owner, isShared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new FolderViewPagination(itemsCount, pageNum, finalPageNum, folderViewDTOList);
    }

    public FolderViewPagination getAllByOwnerAndIsStaredOrderById(long owner, int pageNum, boolean isStared) {
        final int itemsCount = folderMapper.getCountByOwnerAndIsStared(owner, isStared);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<FolderViewDTO> folderViewDTOList = folderViewMapper.getAllByOwnerAndIsStaredOrderById(owner, isStared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new FolderViewPagination(itemsCount, pageNum, finalPageNum, folderViewDTOList);
    }

    public FolderViewPagination getAllByNotOwnerAndIsSharedOrderByLikeCount(long owner, int pageNum, boolean isShared) {
        final int itemsCount = folderMapper.getCountByNotOwnerAndIsShared(owner, true);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<FolderViewDTO> folderViewDTOList = folderViewMapper.getAllByNotOwnerAndIsSharedOrderByLikeCountLimitByFromAndTo(owner, isShared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new FolderViewPagination(itemsCount, pageNum, finalPageNum, folderViewDTOList);
    }

    public FolderViewPagination getAllByIsSharedOrderByLikeCount(int pageNum, boolean isShared) {
        final int itemsCount = folderMapper.getCountByIsShared(true);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<FolderViewDTO> folderViewDTOList = folderViewMapper.getAllByIsSharedOrderByLikeCountLimitByFromAndTo(isShared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new FolderViewPagination(itemsCount, pageNum, finalPageNum, folderViewDTOList);
    }
}
