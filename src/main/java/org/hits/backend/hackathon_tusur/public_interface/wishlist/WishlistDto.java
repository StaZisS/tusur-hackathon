package org.hits.backend.hackathon_tusur.public_interface.wishlist;

import java.util.List;

public record WishlistDto(
        CollectingMoneyDto collectingMoney,
        List<WishlistItemDto> items
) {
}
