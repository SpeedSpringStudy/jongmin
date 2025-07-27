package project.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.wishlist.entity.Wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByMemberId(Long memberId);

    Optional<Wishlist> findByMemberIdAndProductId(Long memberId, Long productId);

    void deleteByMemberIdAndProductId(Long memberId, Long productId);

    boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
