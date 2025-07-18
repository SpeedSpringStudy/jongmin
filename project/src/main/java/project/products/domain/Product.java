package project.products.domain;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;
}
