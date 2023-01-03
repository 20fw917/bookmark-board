package com.project.bookmarkboard.service;

import com.project.bookmarkboard.dto.bookmark.Bookmark;
import com.project.bookmarkboard.dto.folder.Folder;
import com.project.bookmarkboard.dto.folder.FolderItem;
import com.project.bookmarkboard.dto.folder.FolderRequest;
import com.project.bookmarkboard.mapper.FolderItemMapper;
import com.project.bookmarkboard.mapper.FolderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class FolderService {
    private final AttachmentService attachmentService;
    private final FolderMapper folderMapper;
    private final FolderItemMapper folderItemMapper;
    private final BookmarkService bookmarkService;

    public Folder getOneById(long folderId) {
        return folderMapper.getOneById(folderId);
    }

    public int getCountByOwnerAndIsShared(long ownerId, boolean isShared) {
        return folderMapper.getCountByOwnerAndIsShared(ownerId, isShared);
    }

    @Transactional
    public boolean updateIsStaredById(long folderId, boolean toModifyStaredStatus) {
        return folderMapper.updateIsStaredById(folderId, toModifyStaredStatus) == 1;
    }

    @Transactional
    public boolean updateIsSharedById(long folderId, boolean toModifySharedStatus) {
        return folderMapper.updateIsSharedById(folderId, toModifySharedStatus) == 1;
    }

    @Transactional
    public void insertFolder(FolderRequest folderRequest) throws IOException {
        if(folderRequest.getFolderThumbnail().getSize() != 0) {
            final String renamedFileName = attachmentService.saveFile(folderRequest.getFolderThumbnail(), "folder_thumbnail");
            folderRequest.setThumbnail(renamedFileName);

            log.debug("RECEIVED folderRequest: "  + folderRequest);
        }

        final Folder folder = folderRequest.getFolderDTO();
        folderMapper.insertFolder(folder);

        if(folderRequest.getCheckedItem() != null) {
            insertFolderItemToDB(folderRequest.getCheckedItem(), folder.getId());
        }
    }

    @Transactional
    public void copyFolder(long folderId, long newOwnerId) throws IOException {
        final Folder oldFolder = folderMapper.getOneById(folderId);

        final Folder toInsertFolder = Folder.builder()
                .owner(newOwnerId)
                .title(oldFolder.getTitle())
                .memo(oldFolder.getMemo())
                .build();

        if(oldFolder.getThumbnail() != null && !oldFolder.getThumbnail().equals("")) {
            final String newThumbnailImage = attachmentService.copyImage(oldFolder.getThumbnail(), "folder_thumbnail");
            oldFolder.setThumbnail(newThumbnailImage);
        }

        // 새 폴더 info Insert
        folderMapper.insertFolder(toInsertFolder);
        log.debug("New Folder Information: " + toInsertFolder);

        final List<Long> folderItemBookmarkIdList = folderItemMapper.getAllByParentFolderOrderByIdDesc(folderId).stream()
                .map(FolderItem::getBookmarkId)
                .collect(Collectors.toList());

        if(folderItemBookmarkIdList.size() != 0) {
            final List<Bookmark> insertedBookmarkList = bookmarkService.copyBookmark(folderItemBookmarkIdList, newOwnerId);
            final List<FolderItem> folderItemList = insertedBookmarkList.stream()
                            .map(bookmarkDTO -> FolderItem.builder()
                                    .parentFolder(toInsertFolder.getId())
                                    .bookmarkId(bookmarkDTO.getId())
                                    .build())
                    .collect(Collectors.toList());

            folderItemMapper.insertFolderItem(folderItemList);
            log.debug("insertFolderItem: " + folderItemList);
        }
    }

    @Transactional
    public boolean deleteFolder(Folder folder) {
        if(folder.getThumbnail() != null) {
            attachmentService.deleteImage(folder.getThumbnail());
        }
        folderMapper.deleteById(folder.getId());

        return true;
    }

    @Transactional
    public void updateFolder(FolderRequest folderRequest) throws IOException {
        // 1. 섬네일이 기존에서 변경된 경우 (기존 섬네일을 지워야함.)
        if(folderRequest.getThumbnail() != null && folderRequest.getFolderThumbnail().getSize() != 0) {
            attachmentService.deleteImage(folderRequest.getThumbnail());
            folderRequest.setThumbnail(null);
        }

        // 2. 섬네일 삭제를 요구한 경우
        if(folderRequest.getThumbnail() != null && folderRequest.isDeleteRequest()) {
            attachmentService.deleteImage(folderRequest.getThumbnail());
            folderRequest.setThumbnail(null);
        }

        if(folderRequest.getFolderThumbnail().getSize() != 0) {
            final String renamedFileName = attachmentService.saveFile(folderRequest.getFolderThumbnail(), "folder_thumbnail");
            folderRequest.setThumbnail(renamedFileName);
        }

        folderMapper.updateFolderById(folderRequest.getFolderDTO());
        folderItemMapper.deleteByParentFolder(folderRequest.getId());

        if(folderRequest.getCheckedItem() != null) {
            insertFolderItemToDB(folderRequest.getCheckedItem(), folderRequest.getId());
        }
    }

    private void insertFolderItemToDB(String[] toInsertItem, long folderId) {
        List<FolderItem> toAddFolderItemList = new ArrayList<>();

        for (String itemId : toInsertItem) {
            FolderItem folderItem = new FolderItem(Long.parseLong(itemId), folderId);
            toAddFolderItemList.add(folderItem);
        }

        log.debug("toAddFolderItemList: " + toAddFolderItemList);
        folderItemMapper.insertFolderItem(toAddFolderItemList);
    }
}
