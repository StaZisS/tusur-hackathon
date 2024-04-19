package org.hits.backend.hackathon_tusur.core.wishlist;

import com.example.hackathon.public_.tables.records.WishlistItemRecord;
import org.jooq.RecordMapper;

import java.util.Optional;

public class WishlistItemEntityMapper implements RecordMapper<WishlistItemRecord, WishlistItemEntity> {
    @Override
    public WishlistItemEntity map(WishlistItemRecord wishlistItemRecord) {
        return new WishlistItemEntity(
                wishlistItemRecord.getId(),
                wishlistItemRecord.getWishlistId(),
                wishlistItemRecord.getName(),
                Optional.ofNullable(wishlistItemRecord.getPrice()),
                Optional.ofNullable(wishlistItemRecord.getComment()),
                Optional.ofNullable(wishlistItemRecord.getLink()),
                wishlistItemRecord.getRating(),
                wishlistItemRecord.getIsClosed()
        );
    }
}
