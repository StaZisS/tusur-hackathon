package org.hits.backend.hackathon_tusur.public_interface.wishlist;

import org.springframework.web.multipart.MultipartFile;

public record AddPhotoToWishlistItemDto (
        MultipartFile[] photos,
        String wishlistItemId,
        String userId
) {
}
