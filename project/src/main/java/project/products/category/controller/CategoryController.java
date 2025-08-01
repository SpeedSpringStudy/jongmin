package project.products.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.products.category.dto.CategoryRequestDto;
import project.products.category.dto.CategoryResponseDto;
import project.products.category.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController implements CategorySwagger {

    private final CategoryService categoryService;

    @Override
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid CategoryRequestDto dto) {
        categoryService.create(dto);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid CategoryRequestDto request) {
        categoryService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
