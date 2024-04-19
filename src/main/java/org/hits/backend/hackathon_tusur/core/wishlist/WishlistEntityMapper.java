package org.hits.backend.hackathon_tusur.core.wishlist;

import com.example.hackathon.public_.tables.records.WishlistRecord;
import org.jooq.RecordMapper;

import java.util.Optional;

public class WishlistEntityMapper implements RecordMapper<WishlistRecord, WishlistEntity> {
    @Override
    public WishlistEntity map(WishlistRecord record) {
        return new WishlistEntity(
                record.getId(),
                record.getUserId(),
                record.getRaised(),
                record.getNeeded(),
                record.getIsActive(),
                Optional.ofNullable(record.getDonationLink())
        );
    }
}
