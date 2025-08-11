package project.products.option.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.products.productoption.entity.ProductOption;
import project.products.productoption.exception.OutOfStockException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductOptionTest {

    @Test
    @DisplayName("상품 옵션의 재고를 뭊어진 수량만큼 차감한다.")
    void subtractSuccess() {
        // given
        ProductOption productOption = ProductOption.builder()
                .quantity(10)
                .build();

        // when
        productOption.subtract(3);

        // then
        assertThat(productOption.getQuantity()).isEqualTo(7);
    }

    @Test
    @DisplayName("차감하려는 수량보다 재고가 적으면 OutOfStockException 예외가 발생한다.")
    void subtractThrowsOutOfStockException() {
        // given
        ProductOption productOption = ProductOption.builder()
                .quantity(5)
                .build();

        // when & then
        assertThatThrownBy(() -> productOption.subtract(10))
                .isInstanceOf(OutOfStockException.class)
                .hasMessage("재고가 부족합니다.");
    }

    @Test
    @DisplayName("차감하려는 수량이 음수이면 IllegalArgumentException 예외가 발생한다.")
    void subtractThrowsIllegalArgumentExceptionWhenAmountIsNegative() {
        // given
        ProductOption productOption = ProductOption.builder()
                .quantity(10)
                .build();

        // when & then
        assertThatThrownBy(() -> productOption.subtract(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("뺄 수량은 0보다 커야 합니다.");
    }
}
