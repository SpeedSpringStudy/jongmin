package project.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.products.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
