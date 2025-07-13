package project.products.repository;

import org.springframework.stereotype.Repository;
import project.products.domain.Product;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {

    private final Map<Long, Product> store = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    public Product getOne(Long id) {
        return store.get(id);
    }

    public Product save(Product product) {
        Long id = idSequence.getAndIncrement();
        Product newProdcut = Product.builder()
                .id(id)
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
        store.put(id, newProdcut);
        return newProdcut;
    }

    public Product update(Long id, Product product) {
        Product updated = Product.builder()
                .id(id)
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
        store.put(id, updated);
        return updated;
    }

    public void delete(Long id) {
        store.remove(id);
    }
}
