package project.wishlist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.common.resolver.LoginUser;
import project.member.entity.Member;
import project.wishlist.dto.WishRequest;
import project.wishlist.dto.WishResponse;
import project.wishlist.service.WishlistService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public Page<WishResponse> getWishes(@LoginUser Member member, Pageable pageable) {
        return wishlistService.getWishes(member, pageable);
    }

    @PostMapping
    public ResponseEntity<Void> addWish(@LoginUser Member member, @RequestBody WishRequest request) {
        wishlistService.addWish(member, request.getProductId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeWish(@LoginUser Member member, @RequestBody WishRequest request) {
        wishlistService.removeWish(member, request.getProductId());
        return ResponseEntity.ok().build();
    }
}
