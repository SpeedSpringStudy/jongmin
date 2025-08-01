package project.products.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryRequestDto {

    @NotBlank
    private String name;

    private String color;
    private String imageUrl;
    private String description;
}
