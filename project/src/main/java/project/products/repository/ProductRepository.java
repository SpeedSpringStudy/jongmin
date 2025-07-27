package project.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.products.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}
