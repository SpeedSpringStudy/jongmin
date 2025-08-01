package project.products.option.dto;

import lombok.Getter;
import project.products.option.entity.Option;

@Getter
public class OptionResponseDto {

    private final Long id;
    private final String name;

    public OptionResponseDto(Option option) {
        this.id = option.getId();
        this.name = option.getName();
    }
}
