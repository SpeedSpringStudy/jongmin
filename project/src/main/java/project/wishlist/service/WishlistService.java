package project.wishlist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.products.repository.ProductRepository;
import project.wishlist.dao.WishlistDao;
import project.wishlist.domain.Wishlist;
import project.wishlist.dto.WishResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistDao wishlistDao;
    private final ProductRepository productRepository;

    public void addWish(Long userId, Long productId) {
        if (wishlistDao.existsByUserIdAndProductId(userId, productId)) return;
        wishlistDao.save(Wishlist.builder()
                .userId(userId)
                .productId(productId)
                .build());
    }

    public void removeWish(Long userId, Long productId) {
        wishlistDao.deleteByUserIdAndProductId(userId, productId);
    }

    public List<WishResponse> getWishes(Long userId) {
        return wishlistDao.findByUserId(userId).stream()
                .map(w -> WishResponse.builder().productId(w.getProductId()).build())
                .collect(Collectors.toList());
    }
}
