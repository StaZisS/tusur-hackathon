package org.hits.backend.hackathon_tusur.core.wishlist;

import java.util.Optional;
import java.util.stream.Stream;

public interface WishlistRepository {
    String createWishlist(WishlistEntity entity);
    void updateWishlist(WishlistEntity entity);
    Optional<WishlistEntity> getWishlistByUserId(String userId);

    String addItemsToWishlist(WishlistItemEntity entity);
    void removeItemsFromWishlist(String id);
    void updateItemsInWishlist(WishlistItemEntity entity);
    Optional<WishlistItemEntity> getItemById(String itemId);

    void addPhotoToItem(WishlistItemPhotoEntity entity);
    void removePhotoFromItem(String id);
    Stream<String> getItemsPhotos(String itemId);
    Optional<String> getMainPhoto(String itemId);
    Stream<WishlistItemEntity> getItemsByWishlistId(String wishlistId);
}
