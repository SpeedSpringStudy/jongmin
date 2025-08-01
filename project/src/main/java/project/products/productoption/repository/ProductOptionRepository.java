package project.products.productoption.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.products.product.entity.Product;
import project.products.option.entity.Option;
import project.products.productoption.entity.ProductOption;

import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    Optional<ProductOption> findByProductAndOption(Product product, Option option);
}
