package project.products;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.products.category.entity.Category;
import project.products.category.repository.CategoryRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() > 0) return;

        List<Category> categories = List.of(
                new Category("교환권", "#FFADAD", "https://image.com/coupon.png", "교환 가능한 상품"),
                new Category("상품권", "#FFD6A5", "https://image.com/giftcard.png", "기프트카드 형태의 상품"),
                new Category("뷰티", "#FDFFB6", "https://image.com/beauty.png", "화장품 및 뷰티 관련"),
                new Category("패션", "#CAFFBF", "https://image.com/fashion.png", "의류 및 잡화"),
                new Category("식품", "#9BF6FF", "https://image.com/food.png", "먹거리 및 간식"),
                new Category("리빙/도서", "#A0C4FF", "https://image.com/living.png", "생활용품, 책 등"),
                new Category("레저/스포츠", "#BDB2FF", "https://image.com/leisure.png", "운동 및 여가"),
                new Category("아티스트/캐릭터", "#FFC6FF", "https://image.com/character.png", "굿즈, 아티스트 브랜드"),
                new Category("유아동/반려", "#FFFFFC", "https://image.com/kids-pet.png", "아이, 반려동물 용품"),
                new Category("디지털/가전", "#E0BBE4", "https://image.com/digital.png", "IT, 전자제품"),
                new Category("카카오프렌즈", "#FFDFD3", "https://image.com/kakao.png", "카카오프렌즈 전용 상품"),
                new Category("트렌드 선물", "#FEC8D8", "https://image.com/trend.png", "인기 상품 모음"),
                new Category("백화점", "#D0F4DE", "https://image.com/department.png", "프리미엄 백화점 상품")
        );

        categoryRepository.saveAll(categories);
    }
}
