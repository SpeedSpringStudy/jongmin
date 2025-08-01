package project.products.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.products.category.dto.CategoryRequestDto;
import project.products.category.dto.CategoryResponseDto;

import java.util.List;

@Tag(name = "Category", description = "카테고리 관련 API")
@RequestMapping("/api/categories")
public interface CategorySwagger {

    @Operation(summary = "카테고리 전체 조회")
    @GetMapping
    ResponseEntity<List<CategoryResponseDto>> getAll();

    @Operation(
            summary = "카테고리 추가",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = CategoryRequestDto.class),
                            examples = @ExampleObject(
                                    name = "카테고리 추가 예시",
                                    value = "{\n  \"name\": \"트렌드 선물\",\n  \"color\": \"#FFA500\",\n  \"imageUrl\": \"https://image.com/category.png\",\n  \"description\": \"요즘 인기 선물 모음\" }"
                            )
                    )
            )
    )
    @PostMapping
    ResponseEntity<Void> create(@RequestBody @Valid CategoryRequestDto dto);

    @Operation(
            summary = "카테고리 수정",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = CategoryRequestDto.class),
                            examples = @ExampleObject(
                                    name = "카테고리 수정 예시",
                                    value = "{\n  \"name\": \"패션\",\n  \"color\": \"#0099FF\",\n  \"imageUrl\": \"https://image.com/fashion.png\",\n  \"description\": \"옷과 잡화\" }"
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid CategoryRequestDto dto);

    @Operation(summary = "카테고리 삭제", security = @SecurityRequirement(name = "JWT"))
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
