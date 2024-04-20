package org.hits.backend.hackathon_tusur.rest.wishlist;

import org.hits.backend.hackathon_tusur.public_interface.file.FileWithLinkDto;

import java.math.BigDecimal;
import java.util.List;

public record WishlistItemFullResponse(
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
