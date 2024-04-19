package org.hits.backend.hackathon_tusur.public_interface.wishlist;

public record RemoveItemFromWishlistDto (
        String wishlistItemId,
        String userId
) {
}
