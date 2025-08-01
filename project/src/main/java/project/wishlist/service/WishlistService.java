package project.wishlist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.products.product.entity.Product;
import project.products.product.repository.ProductRepository;
import project.wishlist.dto.WishResponse;
import project.wishlist.entity.Wishlist;
import project.wishlist.repository.WishlistRepository;
import project.member.entity.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addWish(Member member, Long productId) {
        if (wishlistRepository.existsByMemberIdAndProductId(member.getId(), productId)) return;
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        wishlistRepository.save(Wishlist.builder()
                .member(member)
                .product(product)
                .build());
    }

    @Transactional
    public void removeWish(Member member, Long productId) {
        wishlistRepository.deleteByMemberIdAndProductId(member.getId(), productId);
    }

    public Page<WishResponse> getWishes(Member member, Pageable pageable) {
        return wishlistRepository.findByMemberId(member.getId(), pageable)
                .map(w -> WishResponse.builder().productId(w.getProduct().getId()).build());
    }
}
