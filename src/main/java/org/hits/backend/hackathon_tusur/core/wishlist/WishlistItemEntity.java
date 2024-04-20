package org.hits.backend.hackathon_tusur.core.wishlist;

import java.math.BigDecimal;
import java.util.Optional;

public record WishlistItemEntity(
        String id,
        String wishlistId,
        String name,
        Optional<BigDecimal> price,
        Optional<String> comment,
        Optional<String> link,
        Integer rating,
        Boolean isClosed
) {
}
