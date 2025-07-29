package project.products.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.products.dto.ProductRequestDto;
import project.products.entity.Product;

@Tag(name = "Product", description = "상품 관련 API")
@RequestMapping("/api/products")
public interface ProductSwagger {

    @Operation(summary = "상품 전체 조회")
    @GetMapping
    ResponseEntity<Page<Product>> getAll(@ParameterObject Pageable pageable);

    @Operation(
            summary = "상품 등록",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductRequestDto.class),
                            examples = @ExampleObject(
                                    name = "상품 등록 예시",
                                    value = "{\n  \"name\": \"커피\",\n  \"price\": 1500,\n  \"imageUrl\": \"https://image.com/c.png\"\n}"
                            )
                    )
            )
    )
    @PostMapping
    ResponseEntity<Void> create(@org.springframework.web.bind.annotation.RequestBody ProductRequestDto request);

    @Operation(
            summary = "상품 수정",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Product.class),
                            examples = @ExampleObject(
                                    name = "상품 수정 예시",
                                    value = "{\n  \"name\": \"수정된 커피\",\n  \"price\": 1800,\n  \"imageUrl\": \"https://image.com/c-new.png\"\n}"
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    ResponseEntity<Void> update(@PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody Product request);

    @Operation(summary = "상품 삭제", security = @SecurityRequirement(name = "JWT"))
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
