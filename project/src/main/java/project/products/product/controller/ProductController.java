package project.products.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.products.product.dto.ProductRequestDto;
import project.products.product.dto.ProductResponseDto;
import project.products.product.entity.Product;
import project.products.product.service.ProductService;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductSwagger {

    private final ProductService productService;

    @Override
    public ResponseEntity<Page<ProductResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productService.getAll(pageable));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> create(ProductRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.add(request));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(Long id, ProductRequestDto requestDto) {
        productService.update(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
