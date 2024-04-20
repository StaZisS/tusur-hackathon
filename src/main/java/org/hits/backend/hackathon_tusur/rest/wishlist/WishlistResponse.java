package org.hits.backend.hackathon_tusur.rest.wishlist;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WishlistResponse(
        @JsonProperty("collecting_money")
        CollectingMoneyResponse collectingMoney,
        List<WishlistItemResponse> items
) {
}
