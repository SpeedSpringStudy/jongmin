package project.products.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import project.products.entity.Category;
import project.products.entity.Product;
import project.products.validator.ForbiddenWords;

@Getter
public class ProductRequestDto {

    @NotBlank(message = "상품 이름은 필수입니다.")
    @Size(max = 15, message = "상품 이름은 최대 15자까지 가능합니다.")
    @Pattern(
            regexp = "^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]*$",
            message = "허용되지 않은 특수 문자가 포함되어 있습니다."
    )
    @ForbiddenWords(value = {"카카오"}, message = "금지된 단어가 포함되어 있습니다: 카카오")
    private String name;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
    private int price;

    private String imageUrl;

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    public Product toEntity(Category category) {
        return Product.builder()
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .category(category)
                .build();
    }
}
