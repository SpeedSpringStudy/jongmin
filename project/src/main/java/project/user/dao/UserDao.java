package project.user.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.user.domain.User;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("role"),
            rs.getString("refresh_token")
    );

    public void save(User user) {
        jdbcTemplate.update(
                "INSERT INTO users (email, password, role) VALUES (?, ?, ?)",
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM users WHERE email = ?",
                        userRowMapper, email)
                .stream()
                .findFirst();
    }

    public Optional<User> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id = ?",
                        userRowMapper, id)
                .stream()
                .findFirst();
    }

    public void updateRefreshToken(String email, String refreshToken) {
        jdbcTemplate.update("UPDATE users SET refresh_token = ? WHERE email = ?", refreshToken, email);
    }

    public Optional<String> getRefreshTokenByEmail(String email) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        "SELECT refresh_token FROM users WHERE email = ?",
                        new Object[]{email},
                        String.class
                )
        );
    }
}
