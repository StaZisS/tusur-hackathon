package org.hits.backend.hackathon_tusur.public_interface.wishlist;

import org.hits.backend.hackathon_tusur.public_interface.file.FileWithLinkDto;

import java.math.BigDecimal;

public record WishlistItemDto(
        String id,
        FileWithLinkDto mainPhoto,
        String name,
        BigDecimal price,
        Integer rating,
        boolean isClosed
) {
}
