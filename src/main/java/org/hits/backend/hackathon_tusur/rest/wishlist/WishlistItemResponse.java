package org.hits.backend.hackathon_tusur.rest.wishlist;

import org.hits.backend.hackathon_tusur.public_interface.file.FileWithLinkDto;

import java.math.BigDecimal;

public record WishlistItemResponse (
        FileWithLinkDto mainPhoto,
        String name,
        BigDecimal price,
        Integer rating
) {
}
