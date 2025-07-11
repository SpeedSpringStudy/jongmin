package project.products.domain;

import lombok.*;

@Getter
@Builder
public class Product {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;
}
