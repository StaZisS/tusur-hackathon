package org.hits.backend.hackathon_tusur.public_interface.wishlist;

public record RemovePhotoToWishlistItemDto(
        String[] photoIds,
        String userId
) {
}
