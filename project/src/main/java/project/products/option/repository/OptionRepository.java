package project.products.option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.products.option.entity.Option;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    Optional<Option> findByName(String name);
}
