package org.hits.backend.hackathon_tusur.core.affiliate;

import com.example.hackathon.public_.tables.records.AffiliateRecord;
import org.jooq.RecordMapper;

public class AffiliateEntityMapper implements RecordMapper<AffiliateRecord, AffiliateEntity> {
    @Override
    public AffiliateEntity map(AffiliateRecord affiliateRecord) {
        return new AffiliateEntity(
                affiliateRecord.getId(),
                affiliateRecord.getName(),
                affiliateRecord.getAddress()
        );
    }
}
