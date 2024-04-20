package org.hits.backend.hackathon_tusur.rest.wishlist;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.wishlist.WishlistService;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.AddItemToWishlistDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.AddPhotoToWishlistItemDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.RemoveItemFromWishlistDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.RemovePhotoToWishlistItemDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.UpdateItemInWishListDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.WishlistDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.WishlistItemDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.WishlistItemFullDto;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
@SecurityRequirement(name = "oauth2")
@Tag(name = "Wishlist", description = "Test controller for working with wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/item")
    public String addItemToWishList(@RequestParam(value = "photos", required = false) MultipartFile[] photos,
                                    @RequestParam(value = "name") String name,
                                    @RequestParam(value = "price", required = false) BigDecimal price,
                                    @RequestParam(value = "link", required = false) String link,
                                    @RequestParam(value = "comment", required = false) String comment,
                                    @RequestParam("rating") Integer rating,
                                    JwtAuthenticationToken token) {
        var dto = new AddItemToWishlistDto(
                token.getTokenAttributes().get("sub").toString(),
                photos,
                name,
                price,
                link,
                comment,
                rating
        );
        return wishlistService.addItemToWishlist(dto);
    }

    @DeleteMapping("/item/{itemId}")
    public void removeItemFromWishList(@PathVariable(value = "itemId") String itemId,
                                       JwtAuthenticationToken token) {
        var dto = new RemoveItemFromWishlistDto(
                itemId,
                token.getTokenAttributes().get("sub").toString()
        );
        wishlistService.removeItemFromWishlist(dto);
    }

    @PutMapping("/item/{itemId}")
    public void updateItemInWishList(@PathVariable(value = "itemId") String itemId,
                                    @RequestParam(value = "name", required = false) Optional<String> name,
                                    @RequestParam(value = "price", required = false) Optional<BigDecimal> price,
                                    @RequestParam(value = "link", required = false) Optional<String> link,
                                    @RequestParam(value = "comment", required = false) Optional<String> comment,
                                    @RequestParam(value = "rating", required = false) Optional<Integer> rating,
                                    @RequestParam(value = "isClosed", required = false) Optional<Boolean> isClosed,
                                    JwtAuthenticationToken token) {
        var dto = new UpdateItemInWishListDto(
                itemId,
                token.getTokenAttributes().get("sub").toString(),
                name,
                price,
                link,
                comment,
                rating,
                isClosed
        );
        wishlistService.updateItemInWishlist(dto);
    }

    @PatchMapping("/link")
    public void updateLink(@RequestBody AddWishListLinkDto dto, JwtAuthenticationToken token) {
        var userId = token.getTokenAttributes().get("sub").toString();
        wishlistService.updateLink(dto, userId);
    }

    @PostMapping("/item/{itemId}/photo")
    public void addPhotoToItem(@PathVariable(value = "itemId") String itemId,
                             @RequestParam(value = "photos") MultipartFile[] photos,
                             JwtAuthenticationToken token) {
        var dto = new AddPhotoToWishlistItemDto(
                photos,
                itemId,
                token.getTokenAttributes().get("sub").toString()
        );
        wishlistService.addPhotoToItem(dto);
    }

    @DeleteMapping("/item/{itemId}/photo")
    public void removePhotoFromItem(@RequestParam(value = "photoIds") String[] photoIds,
                                    @PathVariable(value = "itemId") String itemId,
                                    JwtAuthenticationToken token) {
        var dto = new RemovePhotoToWishlistItemDto(
                photoIds,
                token.getTokenAttributes().get("sub").toString()
        );
        wishlistService.removePhotoFromItem(dto);
    }

    @GetMapping("/{userId}")
    public WishlistResponse getWishlistUser(@PathVariable(value = "userId") String userId) {
        var response = wishlistService.getWishlistUser(userId);
        return convertToResponse(response);
    }

    @GetMapping("/my")
    public WishlistResponse getMyWishlist(JwtAuthenticationToken token) {
        var userId = token.getTokenAttributes().get("sub").toString();
        var response = wishlistService.getWishlistUser(userId);
        return convertToResponse(response);
    }

    @GetMapping("/item/{itemId}")
    public WishlistItemFullResponse getWishlistItem(@PathVariable(value = "itemId") String itemId) {
        var response = wishlistService.getWishlistItem(itemId);
        return convertToResponse(response);
    }

    private WishlistResponse convertToResponse(WishlistDto dto) {
        return new WishlistResponse(
                new CollectingMoneyResponse(),
                dto.items().stream().map(this::convertToResponse).toList()
        );
    }

    private WishlistItemResponse convertToResponse(WishlistItemDto dto) {
        return new WishlistItemResponse(
                dto.id(),
                dto.mainPhoto(),
                dto.name(),
                dto.price(),
                dto.rating(),
                dto.isClosed()
        );
    }

    private WishlistItemFullResponse convertToResponse(WishlistItemFullDto dto) {
        return new WishlistItemFullResponse(
                dto.id(),
                dto.name(),
                dto.price(),
                dto.comment(),
                dto.link(),
                dto.rating(),
                dto.isClosed(),
                dto.photos()
        );
    }
}
