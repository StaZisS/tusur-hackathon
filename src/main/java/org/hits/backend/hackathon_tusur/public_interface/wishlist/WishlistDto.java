package org.hits.backend.hackathon_tusur.public_interface.wishlist;

import org.hits.backend.hackathon_tusur.rest.wishlist.CollectingMoneyResponse;

import java.util.List;

public record WishlistDto(
        CollectingMoneyResponse collectingMoney,
        List<WishlistItemDto> items
) {
}
