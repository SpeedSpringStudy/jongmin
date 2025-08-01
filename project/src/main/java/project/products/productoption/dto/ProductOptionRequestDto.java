package project.products.productoption.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionRequestDto {
    private Long productId;
    private Long optionId;
    private int quantity;
}
