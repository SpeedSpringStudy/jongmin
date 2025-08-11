package project.products.productoption.service;

import org.junit.jupiter.api.AfterEach;
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
import project.products.productoption.exception.OutOfStockException;
import project.products.productoption.repository.ProductOptionRepository;
import project.products.category.entity.Category;
import project.products.category.repository.CategoryRepository;
import project.products.productoption.entity.ProductOption;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
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

    @AfterEach
    void tearDown() {
        // 테스트 데이터 정리 (외래키 제약조건 고려하여 자식 테이블부터 삭제)
        productOptionRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        optionRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
    }

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
    @DisplayName("상품 옵션의 수량을 차감한다.")
    void subtractQuantity() {
        // given
        Category category = categoryRepository.save(Category.builder().name("식품").color("#FFFFFF").imageUrl("url").description("desc").build());
        Product product = productRepository.save(Product.builder().name("사과").price(2000).category(category).imageUrl("url").build());
        Option option = optionRepository.save(Option.builder().name("1kg").build());
        ProductOption productOption = productOptionRepository.save(ProductOption.builder().product(product).option(option).quantity(10).build());

        // when
        productOptionService.subtractQuantity(productOption.getId(), 3);

        // then
        ProductOption updatedProductOption = productOptionRepository.findById(productOption.getId()).get();
        assertThat(updatedProductOption.getQuantity()).isEqualTo(7);
    }

    @Test
    @DisplayName("상품 옵션의 수량 차감 시 재고가 부족하면 예외가 발생한다.")
    void subtractQuantity_ThrowsOutOfStockException() {
        // given
        Category category = categoryRepository.save(Category.builder().name("음료").color("#FFFFFF").imageUrl("url").description("desc").build());
        Product product = productRepository.save(Product.builder().name("콜라").price(1500).category(category).imageUrl("url").build());
        Option option = optionRepository.save(Option.builder().name("500ml").build());
        ProductOption productOption = productOptionRepository.save(ProductOption.builder().product(product).option(option).quantity(2).build());

        // when & then
        assertThatThrownBy(() -> productOptionService.subtractQuantity(productOption.getId(), 5))
                .isInstanceOf(OutOfStockException.class)
                .hasMessage("재고가 부족합니다.");
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

    @Test
    @DisplayName("동시에 재고를 차감하면 한 번만 성공하고 나머지는 충돌 예외가 발생한다.")
    void subtractQuantityWithOptimisticLock() throws InterruptedException {
        // given
        Category category = categoryRepository.save(Category.builder().name("음료").color("#FFFFFF").imageUrl("url").description("desc").build());
        Product product = productRepository.save(Product.builder().name("사이다").price(1000).category(category).imageUrl("url").build());
        Option option = optionRepository.save(Option.builder().name("500ml").build());
        ProductOption po = productOptionRepository.save(ProductOption.builder().product(product).option(option).quantity(10).build());
        Long productOptionId = po.getId();

        int threadCount = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        // when
        // 2개의 스레드가 거의 동시에 재고 6개 차감을 시도 (총 12개 차감 시도, 재고는 10개)
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    productOptionService.subtractQuantity(productOptionId, 6);
                    successCount.incrementAndGet();
                } catch (ObjectOptimisticLockingFailureException e) {
                    // 충돌 발생
                    failCount.incrementAndGet();
                } catch (Exception e) {
                    // 테스트 중 예상치 못한 다른 예외 발생 시 확인을 위해 출력
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드의 작업이 끝날 때까지 대기
        executorService.shutdown();

        // then
        ProductOption finalPo = productOptionRepository.findById(productOptionId).get();
        assertThat(finalPo.getQuantity()).isEqualTo(4); // 10 - 6 = 4
        assertThat(successCount.get()).isEqualTo(1);    // 한 개의 스레드만 성공
        assertThat(failCount.get()).isEqualTo(1);       // 한 개의 스레드는 충돌로 실패
    }
}
