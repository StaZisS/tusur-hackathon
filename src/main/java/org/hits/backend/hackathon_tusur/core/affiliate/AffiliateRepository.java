package org.hits.backend.hackathon_tusur.core.affiliate;

import java.util.Optional;
import java.util.stream.Stream;

public interface AffiliateRepository {
    String createAffiliate(AffiliateEntity affiliateEntity);
    void updateAffiliate(AffiliateEntity affiliateEntity);
    void deleteAffiliate(String affiliateId);
    Optional<AffiliateEntity> getAffiliateByName(String affiliateName);
    Optional<AffiliateEntity> getAffiliateById(String affiliateId);
    Stream<AffiliateEntity> getAffiliatesByName(String affiliateName);
}
