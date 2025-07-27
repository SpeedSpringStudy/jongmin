package project.products.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.products.dto.ProductRequestDto;
import project.products.entity.Product;
import project.products.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getOne(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    @Transactional
    public void add(Product product) {
        if (productRepository.existsByName(product.getName())) {
            throw new IllegalArgumentException("이미 존재하는 상품 이름입니다.");
        }
        productRepository.save(product);
    }

    @Transactional
    public void update(Long id, Product updated) {
        Product product = getOne(id);
        product.update(updated); // Product 엔티티에 update 메서드 필요
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}