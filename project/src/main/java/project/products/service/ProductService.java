package project.products.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.products.domain.Product;
import project.products.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        return productRepository.update(id, product);
    }

    public void delete(Long id) {
        productRepository.delete(id);
    }
}
