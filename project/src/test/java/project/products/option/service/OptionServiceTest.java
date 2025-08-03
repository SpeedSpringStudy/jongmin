package project.products.option.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.products.option.dto.OptionRequestDto;
import project.products.option.dto.OptionResponseDto;
import project.products.option.entity.Option;
import project.products.option.repository.OptionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class OptionServiceTest {

    @Autowired
    private OptionService optionService;

    @Autowired
    private OptionRepository optionRepository;

    @Test
    @DisplayName("새로운 옵션을 생성한다.")
    void createOption() {
        // given
        OptionRequestDto requestDto = new OptionRequestDto("색상");

        // when
        OptionResponseDto responseDto = optionService.createOption(requestDto);

        // then
        assertThat(responseDto.getName()).isEqualTo("색상");
        assertThat(optionRepository.findById(responseDto.getId())).isPresent();
    }

    @Test
    @DisplayName("이미 존재하는 이름으로 옵션을 생성하면 예외가 발생한다.")
    void createOptionWithDuplicateName() {
        // given
        optionRepository.save(Option.builder().name("사이즈").build());
        OptionRequestDto requestDto = new OptionRequestDto("사이즈");

        // when & then
        assertThatThrownBy(() -> optionService.createOption(requestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 옵션 이름입니다.");
    }

    @Test
    @DisplayName("ID로 옵션을 조회한다.")
    void findOption() {
        // given
        Option savedOption = optionRepository.save(Option.builder().name("용량").build());

        // when
        OptionResponseDto responseDto = optionService.findOption(savedOption.getId());

        // then
        assertThat(responseDto.getId()).isEqualTo(savedOption.getId());
        assertThat(responseDto.getName()).isEqualTo("용량");
    }

    @Test
    @DisplayName("존재하지 않는 ID로 옵션을 조회하면 예외가 발생한다.")
    void findOptionWithNonExistingId() {
        // given
        Long nonExistingId = 999L;

        // when & then
        assertThatThrownBy(() -> optionService.findOption(nonExistingId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 옵션입니다.");
    }

    @Test
    @DisplayName("옵션 정보를 수정한다.")
    void updateOption() {
        // given
        Option savedOption = optionRepository.save(Option.builder().name("무게").build());
        OptionRequestDto requestDto = new OptionRequestDto("두께");

        // when
        optionService.updateOption(savedOption.getId(), requestDto);

        // then
        Option updatedOption = optionRepository.findById(savedOption.getId()).get();
        assertThat(updatedOption.getName()).isEqualTo("두께");
    }

    @Test
    @DisplayName("이미 존재하는 이름으로 옵션을 수정하면 예외가 발생한다.")
    void updateOptionWithDuplicateName() {
        // given
        optionRepository.save(Option.builder().name("전압").build());
        Option savedOption = optionRepository.save(Option.builder().name("전류").build());
        OptionRequestDto requestDto = new OptionRequestDto("전압");

        // when & then
        assertThatThrownBy(() -> optionService.updateOption(savedOption.getId(), requestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 옵션 이름입니다.");
    }

    @Test
    @DisplayName("옵션을 삭제한다.")
    void deleteOption() {
        // given
        Option savedOption = optionRepository.save(Option.builder().name("제조사").build());

        // when
        optionService.deleteOption(savedOption.getId());

        // then
        assertThat(optionRepository.findById(savedOption.getId())).isEmpty();
    }

    @Test
    @DisplayName("모든 옵션을 조회한다.")
    void findAllOptions() {
        // given
        optionRepository.save(Option.builder().name("옵션1").build());
        optionRepository.save(Option.builder().name("옵션2").build());

        // when
        List<OptionResponseDto> options = optionService.findAllOptions();

        // then
        assertThat(options).hasSize(2);
        assertThat(options.get(0).getName()).isEqualTo("옵션1");
        assertThat(options.get(1).getName()).isEqualTo("옵션2");
    }
}
