package project.products.productoption.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.products.productoption.dto.ProductOptionRequestDto;
import project.products.productoption.dto.ProductOptionResponseDto;

@Tag(name = "Product Option", description = "상품 옵션 관련 API")
public interface ProductOptionSwagger {

    @Operation(summary = "상품 옵션 생성", description = "상품에 옵션을 추가합니다.")
    @PostMapping
    ResponseEntity<ProductOptionResponseDto> createProductOption(@RequestBody ProductOptionRequestDto requestDto);

    @Operation(summary = "상품 옵션 수량 수정", description = "특정 상품 옵션의 수량을 수정합니다.")
    @PutMapping("/{productOptionId}")
    ResponseEntity<Void> updateProductOptionQuantity(@PathVariable Long productOptionId, @RequestParam int quantity);

    @Operation(summary = "상품 옵션 삭제", description = "특정 상품 옵션을 삭제합니다.")
    @DeleteMapping("/{productOptionId}")
    @PreAuthorize("hasRole('ADMIN')") // hasAuthority -> hasRole 로 변경
    ResponseEntity<Void> deleteProductOption(@PathVariable Long productOptionId);
}
