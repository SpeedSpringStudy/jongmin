package project.wishlist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.common.resolver.LoginUser;
import project.user.domain.User;
import project.wishlist.dto.WishRequest;
import project.wishlist.dto.WishResponse;
import project.wishlist.service.WishlistService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public List<WishResponse> getWishes(@LoginUser User user) {
        return wishlistService.getWishes(user.getId());
    }

    @PostMapping
    public ResponseEntity<Void> addWish(@LoginUser User user, @RequestBody WishRequest request) {
        wishlistService.addWish(user.getId(), request.getProductId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeWish(@LoginUser User user, @RequestBody WishRequest request) {
        wishlistService.removeWish(user.getId(), request.getProductId());
        return ResponseEntity.ok().build();
    }
}
