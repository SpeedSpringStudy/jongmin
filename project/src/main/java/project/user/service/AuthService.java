package project.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.auth.JwtTokenProvider;
import project.common.exception.UnauthorizedException;
import project.user.dao.UserDao;
import project.user.domain.User;
import project.user.dto.LoginRequestDto;
import project.user.dto.SignupRequestDto;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDao userDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        if (userDao.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String rawPassword = requestDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .role("USER")
                .build();

        userDao.save(user);
    }

    public Map<String, String> login(LoginRequestDto dto) {
        User user = userDao.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail(), user.getRole());

        userDao.updateRefreshToken(user.getEmail(), refreshToken);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    public void logout(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        String email = jwtTokenProvider.getSubject(accessToken);
        userDao.updateRefreshToken(email, null);
    }

    public String reissueAccessToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new UnauthorizedException("유효하지 않은 리프레시 토큰입니다.");
        }

        String email = jwtTokenProvider.getSubject(refreshToken);
        String role = jwtTokenProvider.getRole(refreshToken);

        String storedToken = userDao.getRefreshTokenByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("로그아웃했거나 유효하지 않은 사용자입니다."));

        if (storedToken == null || !storedToken.equals(refreshToken)) {
            throw new UnauthorizedException("리프레시 토큰이 만료되었거나 일치하지 않습니다.");
        }

        return jwtTokenProvider.generateAccessToken(email, role);
    }
}
