package project.products.productoption.dto;

import lombok.Getter;
import project.products.productoption.entity.ProductOption;

@Getter
public class ProductOptionResponseDto {
    private final Long id;
    private final Long productId;
    private final String productName;
    private final Long optionId;
    private final String optionName;
    private final int quantity;

    public ProductOptionResponseDto(ProductOption productOption) {
        this.id = productOption.getId();
        this.productId = productOption.getProduct().getId();
        this.productName = productOption.getProduct().getName();
        this.optionId = productOption.getOption().getId();
        this.optionName = productOption.getOption().getName();
        this.quantity = productOption.getQuantity();
    }
}
