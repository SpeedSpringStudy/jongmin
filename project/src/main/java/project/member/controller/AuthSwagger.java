package project.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.member.dto.LoginRequestDto;
import project.member.dto.SignupRequestDto;

import java.util.Map;

@Tag(name = "Auth", description = "인증 관련 API")
@RequestMapping("/api/auth")
public interface AuthSwagger {

    @Operation(summary = "회원가입",
            requestBody = @RequestBody(
                    content = @Content(
                            examples = @ExampleObject(value = "{\n  \"email\": \"test@example.com\",\n  \"password\": \"12345678\"\n}"),
                            schema = @Schema(implementation = SignupRequestDto.class)
                    )
            )
    )
    @PostMapping("/signup")
    ResponseEntity<Void> signup(@RequestBody @Valid SignupRequestDto dto);

    @Operation(summary = "로그인",
            requestBody = @RequestBody(
                    content = @Content(
                            examples = @ExampleObject(value = "{\n  \"email\": \"test@example.com\",\n  \"password\": \"12345678\"\n}"),
                            schema = @Schema(implementation = LoginRequestDto.class)
                    )
            )
    )
    @PostMapping("/login")
    ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequestDto dto);

    @Operation(summary = "로그아웃",
            security = @SecurityRequirement(name = "JWT"),
            parameters = @Parameter(name = "Authorization", description = "AccessToken", required = true, example = "Bearer {accessToken}")
    )
    @PostMapping("/logout")
    ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authorization);

    @Operation(summary = "토큰 재발급",
            requestBody = @RequestBody(
                    content = @Content(
                            examples = @ExampleObject(value = "{\n  \"refreshToken\": \"{refreshToken}\"\n}"),
                            schema = @Schema(implementation = Map.class)
                    )
            )
    )
    @PostMapping("/token/refresh")
    ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> request);
}
