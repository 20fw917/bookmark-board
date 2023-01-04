package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.folder.FolderView;
import com.project.bookmarkboard.dto.folder.FolderViewPagination;
import com.project.bookmarkboard.mapper.FolderMapper;
import com.project.bookmarkboard.mapper.FolderViewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderViewService {
    private final FolderViewMapper folderViewMapper;
    private final FolderLikeService folderLikeService;
    private final FolderMapper folderMapper;

    @Value("${items-per-page}")
    private Integer itemPerPage;

    public FolderView getOneById(long folderId) {
        return folderViewMapper.getOneById(folderId);
    }

    public List<FolderView> getLikeStatus(List<FolderView> folderViewList, long userId) {
        return folderViewList.stream()
                .peek(folderView -> folderView.setIsLiked(folderLikeService.getCountByFolderIdAndUserId(userId, folderView.getId())))
                .collect(Collectors.toList());
    }

    public FolderView getLikeStatus(FolderView folderView, long userId) {
        folderView.setIsLiked(folderLikeService.getCountByFolderIdAndUserId(userId, folderView.getId()));
        return folderView;
    }

    public FolderViewPagination getSearchResult(String keyword, Long currentUserId, boolean currentUserOnly, int pageNum) {
        final int startItemNum = (pageNum - 1) * itemPerPage;

        // 비로그인 시
        if(currentUserId == null) {
            final int itemsCount = folderMapper.getCountByIsSharedAndKeyword(true, keyword);
            final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

            final List<FolderView> folderViewList = folderViewMapper.getAllByIsSharedAndKeywordOrderByIdDescLimitByFromAndTo(true, keyword, startItemNum, itemPerPage);
            return new FolderViewPagination(itemsCount, pageNum, finalPageNum, folderViewList);
        }

        // 로그인 시
        int itemsCount = folderMapper.getCountByOwnerAndKeyword(currentUserId, keyword);
        List<FolderView> folderViewList = folderViewMapper.getAllByOwnerAndKeywordOrderByIdDescLimitByFromAndTo(currentUserId, keyword, startItemNum, itemPerPage);

        // 현재 유저 필터가 적용되어 있지 않은 경우
        if(!currentUserOnly) {
            itemsCount += folderMapper.getCountByNotOwnerAndIsSharedAndKeyword(currentUserId, true, keyword);
            folderViewList.addAll(folderViewMapper.getAllByNotOwnerAndIsSharedAndKeywordOrderByIdDesc(currentUserId, keyword, true, startItemNum, itemPerPage));
        }

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;
        return new FolderViewPagination(itemsCount, pageNum, finalPageNum, folderViewList);
    }

    public FolderViewPagination getAllByOwnerOrderByIsStaredAndIdDescLimitByFromAndTo(long owner, int pageNum, boolean careStared) {
        final int itemsCount = folderMapper.getCountByOwner(owner);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<FolderView> folderViewList = folderViewMapper.getAllByOwnerOrderByIsStaredAndIdDescLimitByFromAndTo(owner, careStared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new FolderViewPagination(itemsCount, pageNum, finalPageNum, folderViewList);
    }

    public FolderViewPagination getAllByOwnerAndIsSharedOrderByIdDescLimitByFromAndTo(long owner, int pageNum, boolean isShared) {
        final int itemsCount = folderMapper.getCountByOwnerAndIsShared(owner, isShared);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<FolderView> folderViewList = folderViewMapper.getAllByOwnerAndIsSharedOrderByIsStaredAndIdDescLimitByFromAndTo(owner, isShared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new FolderViewPagination(itemsCount, pageNum, finalPageNum, folderViewList);
    }

    public FolderViewPagination getAllByOwnerAndIsStaredOrderById(long owner, int pageNum, boolean isStared) {
        final int itemsCount = folderMapper.getCountByOwnerAndIsStared(owner, isStared);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<FolderView> folderViewList = folderViewMapper.getAllByOwnerAndIsStaredOrderById(owner, isStared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new FolderViewPagination(itemsCount, pageNum, finalPageNum, folderViewList);
    }

    public FolderViewPagination getSuggestFolder(Long owner, int pageNum, boolean isShared) {
        if(owner == null) {
            return getAllByIsSharedOrderByLikeCount(pageNum, isShared);
        } else {
            return getAllByNotOwnerAndIsSharedOrderByLikeCountLimitByFromAndTo(owner, pageNum, isShared);
        }
    }

    private FolderViewPagination getAllByNotOwnerAndIsSharedOrderByLikeCountLimitByFromAndTo(long owner, int pageNum, boolean isShared) {
        final int itemsCount = folderMapper.getCountByNotOwnerAndIsShared(owner, true);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<FolderView> folderViewList = folderViewMapper.getAllByNotOwnerAndIsSharedOrderByLikeCountLimitByFromAndTo(owner, isShared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new FolderViewPagination(itemsCount, pageNum, finalPageNum, folderViewList);
    }

    private FolderViewPagination getAllByIsSharedOrderByLikeCount(int pageNum, boolean isShared) {
        final int itemsCount = folderMapper.getCountByIsShared(true);
        final int startItemNum = (pageNum - 1) * itemPerPage;

        final List<FolderView> folderViewList = folderViewMapper.getAllByIsSharedOrderByLikeCountLimitByFromAndTo(isShared, startItemNum, itemPerPage);

        final int finalPageNum = ((itemsCount - 1) / itemPerPage) + 1;

        return new FolderViewPagination(itemsCount, pageNum, finalPageNum, folderViewList);
    }
}
