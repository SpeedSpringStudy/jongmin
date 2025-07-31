package project.products.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.products.dto.CategoryRequestDto;
import project.products.dto.CategoryResponseDto;
import project.products.entity.Category;
import project.products.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
class CategoryServiceTest {

    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final CategoryService categoryService = new CategoryService(categoryRepository);

    @Test
    @DisplayName("카테고리 전체 조회 성공")
    void getAll_success() {
        // given
        Category cat1 = new Category("뷰티", "#000", "url1", "desc1");
        Category cat2 = new Category("패션", "#111", "url2", "desc2");
        given(categoryRepository.findAll()).willReturn(List.of(cat1, cat2));

        // when
        List<CategoryResponseDto> result = categoryService.getAll();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("뷰티");
        assertThat(result.get(1).getColor()).isEqualTo("#111");
    }

    @Test
    @DisplayName("카테고리 생성 성공")
    void create_success() {
        // given
        CategoryRequestDto dto = new CategoryRequestDto("디지털", "#123456", "img", "desc");

        // when
        categoryService.create(dto);

        // then
        verify(categoryRepository, times(1))
                .save(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 수정 성공")
    void update_success() {
        // given
        Long id = 1L;
        Category existing = new Category("기존", "#000000", "img", "desc");
        CategoryRequestDto dto = new CategoryRequestDto("수정된", "#ffffff", "newimg", "newdesc");

        given(categoryRepository.findById(id)).willReturn(Optional.of(existing));

        // when
        categoryService.update(id, dto);

        // then
        assertThat(existing.getName()).isEqualTo("수정된");
        assertThat(existing.getColor()).isEqualTo("#ffffff");
    }

    @Test
    @DisplayName("카테고리 삭제 성공")
    void delete_success() {
        // given
        Long id = 1L;

        // when
        categoryService.delete(id);

        // then
        verify(categoryRepository, times(1)).deleteById(id);
    }
}
