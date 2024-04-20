package org.hits.backend.hackathon_tusur.core.collecting_money;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.mail.MailFormatter;
import org.hits.backend.hackathon_tusur.core.mail.MailService;
import org.hits.backend.hackathon_tusur.core.subscribe.service.SubscribeService;
import org.hits.backend.hackathon_tusur.core.user.UserEntity;
import org.hits.backend.hackathon_tusur.core.wishlist.WishlistMapper;
import org.hits.backend.hackathon_tusur.core.wishlist.WishlistRepository;
import org.hits.backend.hackathon_tusur.core.wishlist.WishlistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectingMoneyService {
    private static final Integer SECONDS_BEFORE = 60 * 60 * 24 * 14;

    private final WishlistService wishlistService;
    private final WishlistRepository wishlistRepository;
    private final WishlistMapper wishlistMapper;
    private final MailFormatter mailFormatter;
    private final SubscribeService subscribeService;
    private final MailService mailService;

    @Transactional
    public void activateCollectingMoney() {
        wishlistRepository.getWishlistsBeforeDate(SECONDS_BEFORE)
                .forEach(item -> {
                    var message = mailFormatter.formatNotificationAboutOpeningWishlist(wishlistMapper.toDto(item));
                    //TODO: получить все почты которые причастны к этому пользователю
                    List<UserEntity> users = subscribeService.getAllSubscribedUser(item.userId());
                    users.forEach(user -> mailService.sendMessage(message, user.email(), "Праздник к нам приходит!"));
                    wishlistService.activateWishlist(item.userId());
                });
    }

    @Transactional
    public void deactivateCollectingMoney() {
        wishlistRepository.getExpiredWishlists()
                .forEach(item -> wishlistService.deactivateWishlist(item.userId()));
    }
}
