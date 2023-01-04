package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.bookmark.Bookmark;
import com.project.bookmarkboard.dto.folder.FolderItem;
import com.project.bookmarkboard.mapper.BookmarkMapper;
import com.project.bookmarkboard.mapper.FolderItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkMapper bookmarkMapper;
    private final FolderItemMapper folderItemMapper;

    public int getCountByOwnerAndIsShared(long ownerId, boolean isShared) {
        return bookmarkMapper.getCountByOwnerAndIsShared(ownerId, isShared);
    }

    public Bookmark getOneById(long id) {
        return bookmarkMapper.getOneById(id);
    }

    public List<Bookmark> getAllByOwnerAndKeywordOrderByIsStaredDescAndIdDesc(long owner, String keyword) {
        // trim() 으로 양옆 공백을 제거
        return bookmarkMapper.getAllByOwnerAndKeywordOrderByIsStaredDescAndIdDesc(owner, keyword.trim());
    }

    public List<Bookmark> getAllByOwnerOrderByIdAndStaredDesc(long owner) {
        return bookmarkMapper.getAllByOwnerOrderByIdAndStaredDesc(owner);
    }

    public List<Bookmark> getAllByIdListOrderByIsStaredDescAndIdDesc(List<Long> idList) {
        return bookmarkMapper.getAllByIdListOrderByIsStaredDescAndIdDesc(idList);
    }

    public List<Long> getBookmarkIdListInFolderById(long folderId) {
        final List<FolderItem> folderItemList = folderItemMapper.getAllByParentFolderOrderByIdDesc(folderId);
        return folderItemList.stream().map(FolderItem::getBookmarkId).collect(Collectors.toList());
    }

    @Transactional
    public List<Bookmark> copyBookmark(List<Long> bookmarkIdList, long newOwner) {
        final List<Bookmark> bookmarkList = bookmarkMapper.getAllByIdList(bookmarkIdList);

        List<Bookmark> toInsertFolderItemDTOList = bookmarkList.stream()
                .peek(bookmarkDTO -> {
                    bookmarkDTO.setId(0);
                    bookmarkDTO.setOwner(newOwner);
                    bookmarkDTO.setStared(false);
                    bookmarkDTO.setShared(false);
                })
                .collect(Collectors.toList());

        bookmarkMapper.insertBookmarkList(toInsertFolderItemDTOList);
        log.debug("After toInsertFolderItemDTOList: " + toInsertFolderItemDTOList);

        return toInsertFolderItemDTOList;
    }

    @Transactional
    public boolean insertBookmark(Bookmark bookmark) {
        return bookmarkMapper.insertBookmark(bookmark) == 1;
    }

    @Transactional
    public boolean updateBookmarkById(Bookmark bookmark) {
        return bookmarkMapper.updateBookmarkById(bookmark) == 1;
    }

    @Transactional
    public boolean updateBookmarkById(long bookmarkId) {
        final Bookmark bookmark = Bookmark.builder().id(bookmarkId).build();
        return bookmarkMapper.updateBookmarkById(bookmark) == 1;
    }

    @Transactional
    public boolean deleteBookmarkById(Bookmark bookmark) {
        return bookmarkMapper.deleteBookmarkById(bookmark.getId()) == 1;
    }

    @Transactional
    public boolean deleteBookmarkById(long bookmarkId) {
        return bookmarkMapper.deleteBookmarkById(bookmarkId) == 1;
    }

    @Transactional
    public boolean updateIsStaredById(long bookmarkId, boolean toModifyStaredStatus) {
        return bookmarkMapper.updateIsStaredById(bookmarkId, toModifyStaredStatus) == 1;
    }

    @Transactional
    public boolean updateIsSharedById(long bookmarkId, boolean toModifySharedStatus) {
        return bookmarkMapper.updateIsSharedById(bookmarkId, toModifySharedStatus) == 1;
    }
}
