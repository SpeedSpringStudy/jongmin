package project.products.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import project.products.domain.Product;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("image_url")
    );

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM product", productRowMapper);
    }

    public Product findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM product WHERE id = ?", productRowMapper, id);
    }

    public void save(Product product) {
        jdbcTemplate.update("INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)",
                product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void update(Product product) {
        jdbcTemplate.update("UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?",
                product.getName(), product.getPrice(), product.getImageUrl(), product.getId());
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM product WHERE id = ?", id);
    }
}
