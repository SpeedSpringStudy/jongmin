package project.products.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.products.dao.ProductDao;
import project.products.domain.Product;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public List<Product> getAll() {
        return productDao.findAll();
    }

    public Product getOne(Long id) {
        return productDao.findById(id);
    }

    public void add(Product product) {
        productDao.save(product);
    }

    public void update(Long id, Product product) {
        productDao.update(new Product(id, product.getName(), product.getPrice(), product.getImageUrl()));
    }

    public void delete(Long id) {
        productDao.delete(id);
    }
}
