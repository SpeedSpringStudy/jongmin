package project.products.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.products.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
