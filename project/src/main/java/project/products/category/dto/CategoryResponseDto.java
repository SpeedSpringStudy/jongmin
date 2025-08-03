package project.products.category.dto;

import lombok.Builder;
import lombok.Getter;
import project.products.category.entity.Category;

@Getter
@Builder
public class CategoryResponseDto {

    private Long id;
    private String name;
    private String color;
    private String imageUrl;
    private String description;

    public static CategoryResponseDto from(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .color(category.getColor())
                .imageUrl(category.getImageUrl())
                .description(category.getDescription())
                .build();
    }
}
