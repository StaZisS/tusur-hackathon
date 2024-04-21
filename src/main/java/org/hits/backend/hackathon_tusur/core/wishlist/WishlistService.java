package org.hits.backend.hackathon_tusur.core.wishlist;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.file.FileMetadata;
import org.hits.backend.hackathon_tusur.core.file.StorageService;
import org.hits.backend.hackathon_tusur.core.message.repository.MessageEntity;
import org.hits.backend.hackathon_tusur.core.message.service.MessageService;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionType;
import org.hits.backend.hackathon_tusur.public_interface.file.UploadFileDto;
import org.hits.backend.hackathon_tusur.public_interface.message.CreateMessageDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.AddItemToWishlistDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.AddPhotoToWishlistItemDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.CreateWishlistDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.RemoveItemFromWishlistDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.RemovePhotoToWishlistItemDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.UpdateItemInWishListDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.WishlistDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.WishlistItemFullDto;
import org.hits.backend.hackathon_tusur.rest.wishlist.AddWishListLinkDto;
import org.hits.backend.hackathon_tusur.websocket.chat.v1.ResponseMessageDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final StorageService storageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final WishlistMapper wishlistMapper;
    private final MessageService messageService;

    @Transactional
    public String createWishlist(CreateWishlistDto dto) {
        var entity = new WishlistEntity(
                UUID.randomUUID().toString(),
                dto.userId(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                false,
                Optional.empty()
        );
        return wishlistRepository.createWishlist(entity);
    }

    public WishlistEntity getById(String wishListId) {
        return wishlistRepository.getWishList(wishListId);
    }

    @Transactional
    public void updateLink(AddWishListLinkDto dto, String userId) {
        var wishlist = wishlistRepository.getWishlistByUserId(userId)
                .orElseThrow(() -> new ExceptionInApplication("Wishlist not found", ExceptionType.NOT_FOUND));
        if (!userId.equals(wishlist.userId())) {
            throw new ExceptionInApplication("You can't update link from another wishlist", ExceptionType.FORBIDDEN);
        }

        var entity = new WishlistEntity(
                wishlist.id(),
                wishlist.userId(),
                wishlist.raised(),
                wishlist.needed(),
                wishlist.isActive(),
                Optional.ofNullable(dto.link())
        );
        wishlistRepository.updateWishlist(entity);
    }

    @Transactional
    public void activateWishlist(String userId) {
        var wishlist = wishlistRepository.getWishlistByUserId(userId)
                .orElseThrow(() -> new ExceptionInApplication("Wishlist not found", ExceptionType.NOT_FOUND));
        if (wishlist.isActive()) return;

        var entity = new WishlistEntity(
                wishlist.id(),
                wishlist.userId(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                true,
                Optional.empty()
        );
        wishlistRepository.updateWishlist(entity);
    }

    @Transactional
    public void deactivateWishlist(String userId) {
        var wishlist = wishlistRepository.getWishlistByUserId(userId)
                .orElseThrow(() -> new ExceptionInApplication("Wishlist not found", ExceptionType.NOT_FOUND));
        if (!wishlist.isActive()) return;

        var entity = new WishlistEntity(
                wishlist.id(),
                wishlist.userId(),
                wishlist.raised(),
                wishlist.needed(),
                false,
                wishlist.downloadLink()
        );
        wishlistRepository.updateWishlist(entity);
    }

    @Transactional
    public String addItemToWishlist(AddItemToWishlistDto dto) {
        var wishlist = wishlistRepository.getWishlistByUserId(dto.userId())
                .orElseThrow(() -> new ExceptionInApplication("Wishlist not found", ExceptionType.NOT_FOUND));
        var entity = new WishlistItemEntity(
                UUID.randomUUID().toString(),
                wishlist.id(),
                dto.name(),
                Optional.ofNullable(dto.price()),
                Optional.ofNullable(dto.comment()),
                Optional.ofNullable(dto.link()),
                dto.rating(),
                false
        );

        var itemId = wishlistRepository.addItemsToWishlist(entity);
        savePhoto(dto.photos(), itemId);

        return itemId;
    }

    @Transactional
    public void removeItemFromWishlist(RemoveItemFromWishlistDto dto) {
        var wishlist = wishlistRepository.getWishlistByUserId(dto.userId())
                .orElseThrow(() -> new ExceptionInApplication("Wishlist not found", ExceptionType.NOT_FOUND));
        if (!dto.userId().equals(wishlist.userId())) {
            throw new ExceptionInApplication("You can't remove item from another wishlist", ExceptionType.FORBIDDEN);
        }

        wishlistRepository.removeItemsFromWishlist(dto.wishlistItemId());
    }

    @Transactional
    public void updateItemInWishlist(UpdateItemInWishListDto dto) {
        var wishlist = wishlistRepository.getWishlistByUserId(dto.userId())
                .orElseThrow(() -> new ExceptionInApplication("Wishlist not found", ExceptionType.NOT_FOUND));
        if (!dto.userId().equals(wishlist.userId())) {
            throw new ExceptionInApplication("You can't remove item from another wishlist", ExceptionType.FORBIDDEN);
        }

        var item = wishlistRepository.getItemById(dto.wishlistItemId())
                .orElseThrow(() -> new ExceptionInApplication("Item not found", ExceptionType.NOT_FOUND));

        var newItem = new WishlistItemEntity(
                item.id(),
                item.wishlistId(),
                dto.name().orElse(item.name()),
                dto.price().isPresent() ? dto.price() : item.price(),
                dto.comment().isPresent() ? dto.comment() : item.comment(),
                dto.link().isPresent() ? dto.link() : item.link(),
                dto.rating().orElse(item.rating()),
                dto.isClosed().orElse(item.isClosed())
        );

        wishlistRepository.updateItemsInWishlist(newItem);

        if (dto.isClosed().isPresent()) {
            var content = "Пользователь " + dto.userId() + " отметил подарок " + newItem.name() + " как купленный";

            var createMessageDto = new CreateMessageDto(dto.userId(), wishlist.userId(), content, true);
            MessageEntity messageEntity = messageService.save(createMessageDto);
            var response = new ResponseMessageDto(messageEntity.messageId(), messageEntity.senderId(), wishlist.userId(),
                    content, true, "", "");
            messagingTemplate.convertAndSend("/topic/" + wishlist.userId() + "/messages", response);
        }
    }

    @Transactional
    public void addPhotoToItem(AddPhotoToWishlistItemDto dto) {
        var wishlist = wishlistRepository.getWishlistByUserId(dto.userId())
                .orElseThrow(() -> new ExceptionInApplication("Wishlist not found", ExceptionType.NOT_FOUND));
        if (!dto.userId().equals(wishlist.userId())) {
            throw new ExceptionInApplication("You can't remove item from another wishlist", ExceptionType.FORBIDDEN);
        }

        savePhoto(dto.photos(), dto.wishlistItemId());
    }

    @Transactional
    public void removePhotoFromItem(RemovePhotoToWishlistItemDto dto) {
        var wishlist = wishlistRepository.getWishlistByUserId(dto.userId())
                .orElseThrow(() -> new ExceptionInApplication("Wishlist not found", ExceptionType.NOT_FOUND));
        if (!dto.userId().equals(wishlist.userId())) {
            throw new ExceptionInApplication("You can't remove item from another wishlist", ExceptionType.FORBIDDEN);
        }

        for (var photoId : dto.photoIds()) {
            wishlistRepository.removePhotoFromItem(photoId);
        }
    }

    public WishlistDto getWishlistUser(String userId) {
        var wishlist = wishlistRepository.getWishlistByUserId(userId)
                .orElseThrow(() -> new ExceptionInApplication("Wishlist not found", ExceptionType.NOT_FOUND));

        return wishlistMapper.toDto(wishlist);
    }

    public WishlistItemFullDto getWishlistItem(String itemId) {
        var item = wishlistRepository.getItemById(itemId)
                .orElseThrow(() -> new ExceptionInApplication("Item not found", ExceptionType.NOT_FOUND));

        return wishlistMapper.toDto(item);
    }

    private void savePhoto(MultipartFile[] photos, String itemId) {
        for (var photo : photos) {
            var fileMetadata = new FileMetadata(
                    String.format("item_%s_photo_%s", itemId, UUID.randomUUID()),
                    photo.getContentType(),
                    photo.getSize()
            );
            var uploadFileDto = new UploadFileDto(
                    fileMetadata,
                    photo
            );
            storageService.uploadFile(uploadFileDto)
                    .doOnSuccess(aVoid -> wishlistRepository.addPhotoToItem(
                            new WishlistItemPhotoEntity(
                                    fileMetadata.fileName(),
                                    itemId
                            )
                    ))
                    .subscribe();
        }
    }
}
