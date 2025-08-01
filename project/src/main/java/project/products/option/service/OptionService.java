package project.products.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.products.option.dto.OptionRequestDto;
import project.products.option.dto.OptionResponseDto;
import project.products.option.entity.Option;
import project.products.option.repository.OptionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    @Transactional
    public OptionResponseDto createOption(OptionRequestDto requestDto) {
        validateDuplicateOptionName(requestDto.getName());
        Option option = Option.builder()
                .name(requestDto.getName())
                .build();
        Option savedOption = optionRepository.save(option);
        return new OptionResponseDto(savedOption);
    }

    private void validateDuplicateOptionName(String name) {
        optionRepository.findByName(name)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 옵션 이름입니다.");
                });
    }

    public OptionResponseDto findOption(Long optionId) {
        Option option = findOptionById(optionId);
        return new OptionResponseDto(option);
    }

    public Option findOptionById(Long optionId) {
        return optionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션입니다."));
    }

    public List<OptionResponseDto> findAllOptions() {
        return optionRepository.findAll().stream()
                .map(OptionResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateOption(Long optionId, OptionRequestDto requestDto) {
        Option option = findOptionById(optionId);

        if (!option.getName().equals(requestDto.getName())) {
            validateDuplicateOptionName(requestDto.getName());
        }
        option.update(requestDto.getName());
    }

    @Transactional
    public void deleteOption(Long optionId) {
        Option option = findOptionById(optionId);
        optionRepository.delete(option);
    }
}
