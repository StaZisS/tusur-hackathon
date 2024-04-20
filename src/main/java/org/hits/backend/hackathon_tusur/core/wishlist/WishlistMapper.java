package org.hits.backend.hackathon_tusur.core.wishlist;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.file.StorageService;
import org.hits.backend.hackathon_tusur.public_interface.file.FileWithLinkDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.CollectingMoneyDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.WishlistDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.WishlistItemDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.WishlistItemFullDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishlistMapper {
    private final WishlistRepository wishlistRepository;
    private final StorageService storageService;

    public WishlistDto toDto(WishlistEntity entity) {
        return new WishlistDto(
                new CollectingMoneyDto(),
                wishlistRepository.getItemsByWishlistId(entity.id())
                        .map(item -> new WishlistItemDto(
                                item.id(),
                                wishlistRepository.getMainPhoto(item.id())
                                        .map(photoId -> new FileWithLinkDto(
                                                photoId,
                                                storageService.getDownloadLinkByName(photoId)
                                        )).orElse(null),
                                item.name(),
                                item.price().orElse(null),
                                item.rating()
                        ))
                        .toList()
        );
    }

    public WishlistItemFullDto toDto(WishlistItemEntity entity) {
        return new WishlistItemFullDto(
                entity.id(),
                entity.name(),
                entity.price().orElse(null),
                entity.comment().orElse(null),
                entity.link().orElse(null),
                entity.rating(),
                entity.isClosed(),
                wishlistRepository.getItemsPhotos(entity.id())
                        .map(photoId -> new FileWithLinkDto(
                                photoId,
                                storageService.getDownloadLinkByName(photoId)
                        ))
                        .toList()
        );
    }
}
