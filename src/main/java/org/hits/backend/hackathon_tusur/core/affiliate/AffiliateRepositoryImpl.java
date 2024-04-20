package org.hits.backend.hackathon_tusur.core.affiliate;

import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

import static com.example.hackathon.public_.tables.Affiliate.AFFILIATE;

@Repository
@RequiredArgsConstructor
public class AffiliateRepositoryImpl implements AffiliateRepository {
    private static final AffiliateEntityMapper AFFILIATE_ENTITY_MAPPER = new AffiliateEntityMapper();

    private final DSLContext create;

    @Override
    public String createAffiliate(AffiliateEntity affiliateEntity) {
        return create.insertInto(AFFILIATE)
                .set(AFFILIATE.ID, affiliateEntity.id())
                .set(AFFILIATE.NAME, affiliateEntity.name())
                .set(AFFILIATE.ADDRESS, affiliateEntity.address())
                .returning(AFFILIATE.ID)
                .fetchOne(AFFILIATE.ID);
    }

    @Override
    public void updateAffiliate(AffiliateEntity affiliateEntity) {
        create.update(AFFILIATE)
                .set(AFFILIATE.NAME, affiliateEntity.name())
                .set(AFFILIATE.ADDRESS, affiliateEntity.address())
                .where(AFFILIATE.ID.eq(affiliateEntity.id()))
                .execute();
    }

    @Override
    public void deleteAffiliate(String affiliateId) {
        create.deleteFrom(AFFILIATE)
                .where(AFFILIATE.ID.eq(affiliateId))
                .execute();
    }

    @Override
    public Optional<AffiliateEntity> getAffiliateByName(String affiliateName) {
        return create.selectFrom(AFFILIATE)
                .where(AFFILIATE.NAME.eq(affiliateName))
                .fetchOptional(AFFILIATE_ENTITY_MAPPER);
    }

    @Override
    public Optional<AffiliateEntity> getAffiliateById(String affiliateId) {
        return create.selectFrom(AFFILIATE)
                .where(AFFILIATE.ID.eq(affiliateId))
                .fetchOptional(AFFILIATE_ENTITY_MAPPER);
    }

    @Override
    public Stream<AffiliateEntity> getAffiliatesByName(String affiliateName) {
        Condition condition = DSL.trueCondition();
        if (!affiliateName.isBlank()) {
            condition = condition.and(AFFILIATE.NAME.contains(affiliateName));
        }
        return create.selectFrom(AFFILIATE)
                .where(condition)
                .fetchStream()
                .map(AFFILIATE_ENTITY_MAPPER::map);
    }
}
