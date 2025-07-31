package project.products.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.products.dto.CategoryRequestDto;
import project.products.dto.CategoryResponseDto;
import project.products.entity.Category;
import project.products.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long id, CategoryRequestDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));
        category.update(dto.getName(), dto.getColor(), dto.getImageUrl(), dto.getDescription());
    }

    @Transactional
    public void create(CategoryRequestDto dto) {
        Category category = Category.builder()
                .name(dto.getName())
                .color(dto.getColor())
                .imageUrl(dto.getImageUrl())
                .description(dto.getDescription())
                .build();
        categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
