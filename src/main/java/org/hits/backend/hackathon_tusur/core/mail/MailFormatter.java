package org.hits.backend.hackathon_tusur.core.mail;

import org.hits.backend.hackathon_tusur.public_interface.wishlist.WishlistDto;
import org.springframework.stereotype.Component;

@Component
public class MailFormatter {
    public String formatNotificationAboutOpeningWishlist(WishlistDto dto) {
        var builder = new StringBuilder();



        return builder.toString();
    }
}
