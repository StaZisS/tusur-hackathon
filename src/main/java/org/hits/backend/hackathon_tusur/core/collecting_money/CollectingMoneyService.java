package org.hits.backend.hackathon_tusur.core.collecting_money;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.mail.MailFormatter;
import org.hits.backend.hackathon_tusur.core.mail.MailService;
import org.hits.backend.hackathon_tusur.core.wishlist.WishlistMapper;
import org.hits.backend.hackathon_tusur.core.wishlist.WishlistRepository;
import org.hits.backend.hackathon_tusur.core.wishlist.WishlistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollectingMoneyService {
    private static final Integer SECONDS_BEFORE = 60 * 60 * 24 * 14;

    private final WishlistService wishlistService;
    private final WishlistRepository wishlistRepository;
    private final WishlistMapper wishlistMapper;
    private final MailFormatter mailFormatter;
    private final MailService mailService;

    @Transactional
    public void activateCollectingMoney() {
        wishlistRepository.getWishlistsBeforeDate(SECONDS_BEFORE)
                .forEach(item -> {
                    var message = mailFormatter.formatNotificationAboutOpeningWishlist(wishlistMapper.toDto(item));
                    //TODO: получить все почты которые причастны к этому пользователю
                    wishlistService.activateWishlist(item.id());
                });
    }

    @Transactional
    public void deactivateCollectingMoney() {
        wishlistRepository.getExpiredWishlists()
                .forEach(item -> wishlistService.deactivateWishlist(item.id()));
    }
}
