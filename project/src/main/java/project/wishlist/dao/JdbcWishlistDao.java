package project.wishlist.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import project.wishlist.domain.Wishlist;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcWishlistDao implements WishlistDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void save(Wishlist wishlist) {
        String sql = "INSERT INTO wishlist (user_id, product_id) VALUES (:userId, :productId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", wishlist.getUserId())
                .addValue("productId", wishlist.getProductId());
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteByUserIdAndProductId(Long userId, Long productId) {
        String sql = "DELETE FROM wishlist WHERE user_id = :userId AND product_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("productId", productId);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public List<Wishlist> findByUserId(Long userId) {
        String sql = "SELECT * FROM wishlist WHERE user_id = :userId";
        return jdbcTemplate.query(sql,
                new MapSqlParameterSource("userId", userId),
                (rs, rowNum) -> Wishlist.builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .productId(rs.getLong("product_id"))
                        .build());
    }

    @Override
    public boolean existsByUserIdAndProductId(Long userId, Long productId) {
        String sql = "SELECT COUNT(*) FROM wishlist WHERE user_id = :userId AND product_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("productId", productId);
        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }
}
