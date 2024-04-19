package org.hits.backend.hackathon_tusur.public_interface.wishlist;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record AddItemToWishlistDto(
        String userId,
        MultipartFile[] photos,
        String name,
        BigDecimal price,
        String link,
        String comment,
        Integer rating
) {
}
