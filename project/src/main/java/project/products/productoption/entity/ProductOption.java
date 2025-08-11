package project.products.productoption.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.products.product.entity.Product;
import project.products.option.entity.Option;
import project.products.productoption.exception.OutOfStockException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private Option option;

    @Column(nullable = false)
    private int quantity;

    @Version
    private Long version;

    @Builder
    public ProductOption(Product product, Option option, int quantity) {
        this.product = product;
        this.option = option;
        this.quantity = quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void subtract(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("뺄 수량은 0보다 커야 합니다.");
        }
        if (this.quantity < amount) {
            throw new OutOfStockException("재고가 부족합니다.");
        }
        this.quantity -= amount;
    }
}
