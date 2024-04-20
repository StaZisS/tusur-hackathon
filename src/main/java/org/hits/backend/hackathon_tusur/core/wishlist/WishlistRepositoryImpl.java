package org.hits.backend.hackathon_tusur.core.wishlist;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.hackathon.public_.tables.Wishlist.WISHLIST;
import static com.example.hackathon.public_.tables.WishlistItem.WISHLIST_ITEM;
import static com.example.hackathon.public_.tables.WishlistItemPhoto.WISHLIST_ITEM_PHOTO;
import static com.example.hackathon.public_.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {
    private static final WishlistEntityMapper WISHLIST_ENTITY_MAPPER = new WishlistEntityMapper();
    private static final WishlistItemEntityMapper WISHLIST_ITEM_ENTITY_MAPPER = new WishlistItemEntityMapper();

    private final DSLContext create;

    @Override
    public WishlistEntity getWishList(String wishListId) {
        return create.selectFrom(WISHLIST)
                .where(WISHLIST.ID.eq(wishListId))
                .fetchOne(WISHLIST_ENTITY_MAPPER);
    }

    @Override
    public String createWishlist(WishlistEntity entity) {
        return create.insertInto(WISHLIST)
                .set(WISHLIST.ID, entity.id())
                .set(WISHLIST.USER_ID, entity.userId())
                .set(WISHLIST.RAISED, entity.raised())
                .set(WISHLIST.NEEDED, entity.needed())
                .set(WISHLIST.IS_ACTIVE, entity.isActive())
                .set(WISHLIST.DONATION_LINK, entity.downloadLink().orElse(null))
                .returning(WISHLIST.ID)
                .fetchOne(WISHLIST.ID);
    }

    @Override
    public void updateWishlist(WishlistEntity entity) {
        create.update(WISHLIST)
                .set(WISHLIST.RAISED, entity.raised())
                .set(WISHLIST.NEEDED, entity.needed())
                .set(WISHLIST.IS_ACTIVE, entity.isActive())
                .set(WISHLIST.DONATION_LINK, entity.downloadLink().orElse(null))
                .where(WISHLIST.ID.eq(entity.id()))
                .execute();
    }

    @Override
    public Optional<WishlistEntity> getWishlistByUserId(String userId) {
        return create.selectFrom(WISHLIST)
                .where(WISHLIST.USER_ID.eq(userId))
                .fetchOptional(WISHLIST_ENTITY_MAPPER);
    }

    @Override
    public Stream<WishlistEntity> getWishlistsBeforeDate(Integer secondsBefore) {
        return create.select(WISHLIST)
                .from(WISHLIST)
                .join(USERS)
                .on(WISHLIST.USER_ID.eq(USERS.ID))
                .where(USERS.DATE_BIRTH.minus(secondsBefore).greaterThan(LocalDate.now()))
                .and(WISHLIST.IS_ACTIVE.isFalse())
                .fetchStream()
                .map(record -> WISHLIST_ENTITY_MAPPER.map(record.into(WISHLIST)));
    }

    @Override
    public Stream<WishlistEntity> getExpiredWishlists() {
        return create.select(WISHLIST)
                .from(WISHLIST)
                .join(USERS)
                .on(WISHLIST.USER_ID.eq(USERS.ID))
                .where(USERS.DATE_BIRTH.lessThan(LocalDate.now()))
                .and(WISHLIST.IS_ACTIVE.isTrue())
                .fetchStream()
                .map(record -> WISHLIST_ENTITY_MAPPER.map(record.into(WISHLIST)));
    }

    @Override
    public String addItemsToWishlist(WishlistItemEntity entity) {
        return create.insertInto(WISHLIST_ITEM)
                .set(WISHLIST_ITEM.ID, entity.id())
                .set(WISHLIST_ITEM.WISHLIST_ID, entity.wishlistId())
                .set(WISHLIST_ITEM.NAME, entity.name())
                .set(WISHLIST_ITEM.PRICE, entity.price().orElse(null))
                .set(WISHLIST_ITEM.COMMENT, entity.comment().orElse(null))
                .set(WISHLIST_ITEM.LINK, entity.link().orElse(null))
                .set(WISHLIST_ITEM.RATING, entity.rating())
                .set(WISHLIST_ITEM.IS_CLOSED, entity.isClosed())
                .returning(WISHLIST_ITEM.ID)
                .fetchOne(WISHLIST_ITEM.ID);
    }

    @Override
    public void removeItemsFromWishlist(String id) {
        create.deleteFrom(WISHLIST_ITEM)
                .where(WISHLIST_ITEM.ID.eq(id))
                .execute();
    }

    @Override
    public void updateItemsInWishlist(WishlistItemEntity entity) {
        create.update(WISHLIST_ITEM)
                .set(WISHLIST_ITEM.NAME, entity.name())
                .set(WISHLIST_ITEM.PRICE, entity.price().orElse(null))
                .set(WISHLIST_ITEM.COMMENT, entity.comment().orElse(null))
                .set(WISHLIST_ITEM.LINK, entity.link().orElse(null))
                .set(WISHLIST_ITEM.RATING, entity.rating())
                .set(WISHLIST_ITEM.IS_CLOSED, entity.isClosed())
                .where(WISHLIST_ITEM.ID.eq(entity.id()))
                .execute();
    }

    @Override
    public Optional<WishlistItemEntity> getItemById(String itemId) {
        return create.selectFrom(WISHLIST_ITEM)
                .where(WISHLIST_ITEM.ID.eq(itemId))
                .fetchOptional(WISHLIST_ITEM_ENTITY_MAPPER);
    }

    @Override
    public void addPhotoToItem(WishlistItemPhotoEntity entity) {
        create.insertInto(WISHLIST_ITEM_PHOTO)
                .set(WISHLIST_ITEM_PHOTO.ID, entity.id())
                .set(WISHLIST_ITEM_PHOTO.WISHLIST_ITEM_ID, entity.wishlistItemId())
                .execute();
    }

    @Override
    public void removePhotoFromItem(String id) {
        create.deleteFrom(WISHLIST_ITEM_PHOTO)
                .where(WISHLIST_ITEM_PHOTO.ID.eq(id))
                .execute();
    }

    @Override
    public Stream<String> getItemsPhotos(String itemId) {
        return create.select(WISHLIST_ITEM_PHOTO.ID)
                .from(WISHLIST_ITEM_PHOTO)
                .where(WISHLIST_ITEM_PHOTO.WISHLIST_ITEM_ID.eq(itemId))
                .fetchStream()
                .map(r -> r.get(WISHLIST_ITEM_PHOTO.ID));
    }

    @Override
    public Optional<String> getMainPhoto(String itemId) {
        return create.select(WISHLIST_ITEM_PHOTO.ID)
                .from(WISHLIST_ITEM_PHOTO)
                .where(WISHLIST_ITEM_PHOTO.WISHLIST_ITEM_ID.eq(itemId))
                .limit(1)
                .fetchOptional()
                .map(r -> r.get(WISHLIST_ITEM_PHOTO.ID));
    }

    @Override
    public Stream<WishlistItemEntity> getItemsByWishlistId(String wishlistId) {
        return create.selectFrom(WISHLIST_ITEM)
                .where(WISHLIST_ITEM.WISHLIST_ID.eq(wishlistId))
                .fetchStream()
                .map(WISHLIST_ITEM_ENTITY_MAPPER::map);
    }
}
