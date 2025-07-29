package project.products.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.products.dto.ProductRequestDto;
import project.products.service.ProductService;
import project.products.entity.Product;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductSwagger {

    private final ProductService productService;

    @Override
    public ResponseEntity<Page<Product>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productService.getAll(pageable));
    }

    @Override
    public ResponseEntity<Void> create(ProductRequestDto request) {
        productService.add(request.toEntity());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> update(Long id, Product product) {
        productService.update(id, product);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
