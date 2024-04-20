package org.hits.backend.hackathon_tusur.core.affiliate;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class AffiliateRepositoryImpl implements AffiliateRepository {
    private final DSLContext create;

    @Override
    public String createAffiliate(AffiliateEntity affiliateEntity) {
        return null;
    }

    @Override
    public void updateAffiliate(AffiliateEntity affiliateEntity) {

    }

    @Override
    public void deleteAffiliate(String affiliateId) {

    }

    @Override
    public Optional<AffiliateEntity> getAffiliateByName(String affiliateName) {
        return Optional.empty();
    }

    @Override
    public Optional<AffiliateEntity> getAffiliateById(String affiliateId) {
        return Optional.empty();
    }

    @Override
    public Stream<AffiliateEntity> getAffiliatesByName(String affiliateName) {
        return null;
    }
}
