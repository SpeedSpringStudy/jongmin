package project.wishlist.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Wishlist {
    private Long id;
    private Long userId;
    private Long productId;
}
