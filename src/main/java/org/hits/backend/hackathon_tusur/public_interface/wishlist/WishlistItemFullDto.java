package org.hits.backend.hackathon_tusur.public_interface.wishlist;

import org.hits.backend.hackathon_tusur.public_interface.file.FileWithLinkDto;

import java.math.BigDecimal;
import java.util.List;

public record WishlistItemFullDto(
        String id,
        String name,
        BigDecimal price,
        String comment,
        String link,
        Integer rating,
        Boolean isClosed,
        List<FileWithLinkDto> photos
) {
}
