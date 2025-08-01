package project.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.auth.JwtTokenProvider;
import project.common.exception.UnauthorizedException;
import project.member.repository.MemberRepository;
import project.member.entity.Member;
import project.member.dto.LoginRequestDto;
import project.member.dto.SignupRequestDto;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        if (memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String rawPassword = requestDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        String role = requestDto.getRole();
        if (role == null || role.isEmpty()) {
            role = "USER"; // 기본값으로 USER 설정
        }
        if (!role.equals("USER") && !role.equals("ADMIN")) {
            throw new IllegalArgumentException("역할은 USER 또는 ADMIN만 가능합니다.");
        }

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .role(role)
                .build();

        memberRepository.save(member);
    }

    public Map<String, String> login(LoginRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(member.getEmail(), member.getRole());
        String refreshToken = jwtTokenProvider.generateRefreshToken(member.getEmail(), member.getRole());

        member.updateRefreshToken(refreshToken);
        memberRepository.save(member);

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
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        member.updateRefreshToken(null);
        memberRepository.save(member);
    }

    public String reissueAccessToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new UnauthorizedException("유효하지 않은 리프레시 토큰입니다.");
        }

        String email = jwtTokenProvider.getSubject(refreshToken);
        String role = jwtTokenProvider.getRole(refreshToken);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("로그아웃했거나 유효하지 않은 사용자입니다."));

        String storedToken = member.getRefreshToken();

        if (storedToken == null || !storedToken.equals(refreshToken)) {
            throw new UnauthorizedException("리프레시 토큰이 만료되었거나 일치하지 않습니다.");
        }

        return jwtTokenProvider.generateAccessToken(email, role);
    }
}
