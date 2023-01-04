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

//    public List<FolderView> getSearchResult(String keyword, Long currentUserId, boolean currentUserOnly) {
//        /*
//            구현 전략
//            1. 자신의 폴더 중 키워드에 일치하는 것(공유 여부 무관)을 구해옴
//            2. 자신의 것이 아닌 것 + 공유된 것 중 키워드에 일치하는 폴더를 가져옴
//            3. 1+2
//            * 만일, 로그인 중이 아닐 시 2번만
//         */
//    }

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
