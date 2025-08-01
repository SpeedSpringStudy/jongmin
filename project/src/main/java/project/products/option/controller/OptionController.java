package project.products.option.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.products.option.dto.OptionRequestDto;
import project.products.option.dto.OptionResponseDto;
import project.products.option.service.OptionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/options")
public class OptionController implements OptionSwagger {

    private final OptionService optionService;

    @Override
    public ResponseEntity<OptionResponseDto> createOption(@Valid @RequestBody OptionRequestDto requestDto) {
        OptionResponseDto responseDto = optionService.createOption(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Override
    public ResponseEntity<OptionResponseDto> getOption(@PathVariable Long optionId) {
        OptionResponseDto responseDto = optionService.findOption(optionId);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<List<OptionResponseDto>> getAllOptions() {
        List<OptionResponseDto> options = optionService.findAllOptions();
        return ResponseEntity.ok(options);
    }

    @Override
    public ResponseEntity<Void> updateOption(@PathVariable Long optionId, @Valid @RequestBody OptionRequestDto requestDto) {
        optionService.updateOption(optionId, requestDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteOption(@PathVariable Long optionId) {
        optionService.deleteOption(optionId);
        return ResponseEntity.noContent().build();
    }
}
