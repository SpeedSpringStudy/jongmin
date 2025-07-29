package project.wishlist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.member.entity.Member;
import project.wishlist.dto.WishRequest;
import project.wishlist.dto.WishResponse;
import project.wishlist.service.WishlistService;

@RestController
@RequiredArgsConstructor
public class WishlistController implements WishlistSwagger {

    private final WishlistService wishlistService;

    @Override
    public ResponseEntity<Page<WishResponse>> getWishes(Member member, Pageable pageable) {
        return ResponseEntity.ok(wishlistService.getWishes(member, pageable));
    }

    @Override
    public ResponseEntity<Void> addWish(Member member, WishRequest request) {
        wishlistService.addWish(member, request.getProductId());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removeWish(Member member, WishRequest request) {
        wishlistService.removeWish(member, request.getProductId());
        return ResponseEntity.ok().build();
    }
}
