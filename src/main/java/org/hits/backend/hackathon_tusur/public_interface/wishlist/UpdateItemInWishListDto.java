package org.hits.backend.hackathon_tusur.public_interface.wishlist;

import java.math.BigDecimal;
import java.util.Optional;

public record UpdateItemInWishListDto(
        String wishlistItemId,
        String userId,
        Optional<String> name,
        Optional<BigDecimal> price,
        Optional<String> link,
        Optional<String> comment,
        Optional<Integer> rating,
        Optional<Boolean> isClosed
) {
}
