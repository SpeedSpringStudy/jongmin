package project.products.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.products.product.dto.ProductRequestDto;
import project.products.product.dto.ProductResponseDto;
import project.products.category.entity.Category;
import project.products.product.entity.Product;
import project.products.product.repository.ProductRepository;
import project.products.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<ProductResponseDto> getAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductResponseDto::new);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    @Transactional
    public ProductResponseDto add(ProductRequestDto request) {
        if (productRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("이미 존재하는 상품 이름입니다.");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

        Product product = request.toEntity(category);
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

    @Transactional
    public void update(Long id, ProductRequestDto request) {
        Product product = findProductById(id);
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        product.update(request.getName(), request.getPrice(), request.getImageUrl(), category);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}