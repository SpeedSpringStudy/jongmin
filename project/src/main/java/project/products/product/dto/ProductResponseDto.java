package project.products.product.dto;

import lombok.Getter;
import project.products.product.entity.Product;
import project.products.productoption.dto.ProductOptionResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductResponseDto {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final Long categoryId;
    private final String categoryName;
    private final List<ProductOptionResponseDto> options;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.categoryId = product.getCategory().getId();
        this.categoryName = product.getCategory().getName();
        this.options = product.getProductOptions().stream()
                .map(ProductOptionResponseDto::new)
                .collect(Collectors.toList());
    }
}
