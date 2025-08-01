package project.products.option.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.products.option.dto.OptionRequestDto;
import project.products.option.dto.OptionResponseDto;

import java.util.List;

@Tag(name = "Option", description = "옵션 관련 API")
public interface OptionSwagger {

    @Operation(summary = "옵션 생성", description = "새로운 상품 옵션을 생성합니다.")
    @PostMapping
    ResponseEntity<OptionResponseDto> createOption(@Valid @RequestBody OptionRequestDto requestDto);

    @Operation(summary = "단일 옵션 조회", description = "ID를 통해 특정 옵션을 조회합니다.")
    @GetMapping("/{optionId}")
    ResponseEntity<OptionResponseDto> getOption(@PathVariable Long optionId);

    @Operation(summary = "모든 옵션 조회", description = "모든 상품 옵션을 조회합니다.")
    @GetMapping
    ResponseEntity<List<OptionResponseDto>> getAllOptions();

    @Operation(summary = "옵션 수정", description = "ID를 통해 특정 옵션의 정보를 수정합니다.")
    @PutMapping("/{optionId}")
    ResponseEntity<Void> updateOption(@PathVariable Long optionId, @Valid @RequestBody OptionRequestDto requestDto);

    @Operation(summary = "옵션 삭제", description = "ID를 통해 특정 옵션을 삭제합니다.")
    @DeleteMapping("/{optionId}")
    @PreAuthorize("hasRole('ADMIN')") // hasAuthority -> hasRole 로 변경
    ResponseEntity<Void> deleteOption(@PathVariable Long optionId);
}
