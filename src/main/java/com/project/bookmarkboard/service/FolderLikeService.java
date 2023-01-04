package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.folder.FolderLike;
import com.project.bookmarkboard.mapper.FolderLikeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class FolderLikeService {
    private final FolderLikeMapper folderLikeMapper;

    public boolean getCountByFolderIdAndUserId(long userId, long folderId) {
        final FolderLike folderLike = new FolderLike(userId, folderId);

        return folderLikeMapper.getCountByFolderIdAndUserId(folderLike) == 1;
    }

    @Transactional
    public void insertFolderLike(long userId, long folderId) {
        final FolderLike folderLike = new FolderLike(userId, folderId);

        folderLikeMapper.insertFolderLike(folderLike);
    }

    @Transactional
    public void deleteFolderLikeByUserIdAndFolderId(long userId, long folderId) {
        final FolderLike folderLike = new FolderLike(userId, folderId);

        folderLikeMapper.deleteFolderLikeByUserIdAndFolderId(folderLike);
    }
}
