package project.wishlist.dao;

import project.wishlist.domain.Wishlist;

import java.util.List;

public interface WishlistDao {
    void save(Wishlist wishlist);

    void deleteByUserIdAndProductId(Long userId, Long productId);

    List<Wishlist> findByUserId(Long userId);

    boolean existsByUserIdAndProductId(Long userId, Long productId);
}
