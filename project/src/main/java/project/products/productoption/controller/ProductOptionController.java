package project.products.productoption.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.products.productoption.dto.ProductOptionRequestDto;
import project.products.productoption.dto.ProductOptionResponseDto;
import project.products.productoption.service.ProductOptionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-options")
public class ProductOptionController implements ProductOptionSwagger {

    private final ProductOptionService productOptionService;

    @Override
    public ResponseEntity<ProductOptionResponseDto> createProductOption(@RequestBody ProductOptionRequestDto requestDto) {
        ProductOptionResponseDto responseDto = productOptionService.createProductOption(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Override
    public ResponseEntity<Void> updateProductOptionQuantity(@PathVariable Long productOptionId, @RequestParam int quantity) {
        productOptionService.updateQuantity(productOptionId, quantity);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteProductOption(@PathVariable Long productOptionId) {
        productOptionService.deleteProductOption(productOptionId);
        return ResponseEntity.noContent().build();
    }
}
