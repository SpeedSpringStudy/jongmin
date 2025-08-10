package project.products.productoption.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.products.product.entity.Product;
import project.products.product.service.ProductService;
import project.products.option.entity.Option;
import project.products.option.service.OptionService;
import project.products.productoption.dto.ProductOptionRequestDto;
import project.products.productoption.dto.ProductOptionResponseDto;
import project.products.productoption.entity.ProductOption;
import project.products.productoption.repository.ProductOptionRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductService productService;
    private final OptionService optionService;

    @Transactional
    public ProductOptionResponseDto createProductOption(ProductOptionRequestDto requestDto) {
        Product product = productService.findProductById(requestDto.getProductId());
        Option option = optionService.findOptionById(requestDto.getOptionId());

        productOptionRepository.findByProductAndOption(product, option)
                .ifPresent(po -> {
                    throw new IllegalArgumentException("이미 해당 상품에 동일한 옵션이 존재합니다.");
                });

        ProductOption productOption = ProductOption.builder()
                .product(product)
                .option(option)
                .quantity(requestDto.getQuantity())
                .build();

        ProductOption savedProductOption = productOptionRepository.save(productOption);
        return new ProductOptionResponseDto(savedProductOption);
    }

    @Transactional
    public void updateQuantity(Long productOptionId, int quantity) {
        ProductOption productOption = findProductOptionById(productOptionId);
        productOption.updateQuantity(quantity);
    }

    @Transactional
    public void deleteProductOption(Long productOptionId) {
        ProductOption productOption = findProductOptionById(productOptionId);
        productOptionRepository.delete(productOption);
    }

    private ProductOption findProductOptionById(Long productOptionId) {
        return productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 옵션입니다."));
    }

    @Transactional
    public void subtractQuantity(Long productOptionId, int amount) {
        ProductOption productOption = findProductOptionById(productOptionId);
        productOption.subtract(amount);
    }
}
