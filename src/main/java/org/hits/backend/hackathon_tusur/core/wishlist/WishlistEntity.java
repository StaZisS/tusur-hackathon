package org.hits.backend.hackathon_tusur.core.wishlist;

import java.math.BigDecimal;
import java.util.Optional;

public record WishlistEntity(
        String id,
        String userId,
        BigDecimal raised,
        BigDecimal needed,
        Boolean isActive,
        Optional<String> downloadLink
) {

}
