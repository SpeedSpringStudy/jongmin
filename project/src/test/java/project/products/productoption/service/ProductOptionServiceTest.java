package project.products.productoption.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.products.product.entity.Product;
import project.products.product.repository.ProductRepository;
import project.products.option.entity.Option;
import project.products.option.repository.OptionRepository;
import project.products.productoption.dto.ProductOptionRequestDto;
import project.products.productoption.dto.ProductOptionResponseDto;
import project.products.productoption.repository.ProductOptionRepository;
import project.products.category.entity.Category;
import project.products.category.repository.CategoryRepository;
import project.products.productoption.entity.ProductOption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ProductOptionServiceTest {

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("상품에 옵션을 추가한다.")
    void createProductOption() {
        // given
        Category category = categoryRepository.save(Category.builder().name("의류").color("#FFFFFF").imageUrl("url").description("desc").build());
        Product product = productRepository.save(Product.builder().name("티셔츠").price(10000).category(category).imageUrl("url").build());
        Option option = optionRepository.save(Option.builder().name("색상").build());
        ProductOptionRequestDto requestDto = new ProductOptionRequestDto(product.getId(), option.getId(), 10);

        // when
        ProductOptionResponseDto responseDto = productOptionService.createProductOption(requestDto);

        // then
        assertThat(responseDto.getProductName()).isEqualTo("티셔츠");
        assertThat(responseDto.getOptionName()).isEqualTo("색상");
        assertThat(responseDto.getQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("이미 존재하는 상품 옵션을 추가하면 예외가 발생한다.")
    void createDuplicateProductOption() {
        // given
        Category category = categoryRepository.save(Category.builder().name("신발").color("#FFFFFF").imageUrl("url").description("desc").build());
        Product product = productRepository.save(Product.builder().name("청바지").price(30000).category(category).imageUrl("url").build());
        Option option = optionRepository.save(Option.builder().name("사이즈").build());
        productOptionService.createProductOption(new ProductOptionRequestDto(product.getId(), option.getId(), 5));
        ProductOptionRequestDto requestDto = new ProductOptionRequestDto(product.getId(), option.getId(), 20);

        // when & then
        assertThatThrownBy(() -> productOptionService.createProductOption(requestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 해당 상품에 동일한 옵션이 존재합니다.");
    }

    @Test
    @DisplayName("상품 옵션의 수량을 업데이트한다.")
    void updateQuantity() {
        // given
        Category category = categoryRepository.save(Category.builder().name("전자제품").color("#FFFFFF").imageUrl("url").description("desc").build());
        Product product = productRepository.save(Product.builder().name("노트북").price(1000000).category(category).imageUrl("url").build());
        Option option = optionRepository.save(Option.builder().name("RAM 16GB").build());
        ProductOption productOption = productOptionRepository.save(ProductOption.builder().product(product).option(option).quantity(5).build());

        // when
        productOptionService.updateQuantity(productOption.getId(), 15);

        // then
        ProductOption updatedProductOption = productOptionRepository.findById(productOption.getId()).get();
        assertThat(updatedProductOption.getQuantity()).isEqualTo(15);
    }

    @Test
    @DisplayName("상품 옵션을 삭제한다.")
    void deleteProductOption() {
        // given
        Category category = categoryRepository.save(Category.builder().name("가구").color("#FFFFFF").imageUrl("url").description("desc").build());
        Product product = productRepository.save(Product.builder().name("의자").price(50000).category(category).imageUrl("url").build());
        Option option = optionRepository.save(Option.builder().name("색상: 검정").build());
        ProductOption productOption = productOptionRepository.save(ProductOption.builder().product(product).option(option).quantity(10).build());

        // when
        productOptionService.deleteProductOption(productOption.getId());

        // then
        assertThat(productOptionRepository.findById(productOption.getId())).isEmpty();
    }
}
