package project.products.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.products.dto.ProductRequestDto;
import project.products.entity.Product;
import project.products.service.ProductService;

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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> create(ProductRequestDto request) {
        productService.add(request);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> update(Long id, ProductRequestDto requestDto) {
        productService.update(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
