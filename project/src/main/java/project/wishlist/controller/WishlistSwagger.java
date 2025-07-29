package project.wishlist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.common.resolver.LoginUser;
import project.member.entity.Member;
import project.wishlist.dto.WishRequest;
import project.wishlist.dto.WishResponse;

@Tag(name = "Wishlist", description = "위시리스트 관련 API")
@RequestMapping("/api/wishlist")
public interface WishlistSwagger {

    @Operation(summary = "위시리스트 조회", security = @SecurityRequirement(name = "JWT"))
    @GetMapping
    ResponseEntity<Page<WishResponse>> getWishes(
            @Parameter(hidden = true) @LoginUser Member member,
            @ParameterObject Pageable pageable);

    @Operation(
            summary = "위시리스트 추가",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WishRequest.class),
                            examples = @ExampleObject(
                                    name = "AddWish",
                                    value = "{\n  \"productId\": 1\n}"
                            )
                    )
            )
    )
    @PostMapping
    ResponseEntity<Void> addWish(
            @Parameter(hidden = true) @LoginUser Member member,
            @org.springframework.web.bind.annotation.RequestBody WishRequest request);

    @Operation(
            summary = "위시리스트 삭제",
            security = @SecurityRequirement(name = "JWT"),
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WishRequest.class),
                            examples = @ExampleObject(
                                    name = "RemoveWish",
                                    value = "{\n  \"productId\": 1\n}"
                            )
                    )
            )
    )
    @DeleteMapping
    ResponseEntity<Void> removeWish(
            @Parameter(hidden = true) @LoginUser Member member,
            @org.springframework.web.bind.annotation.RequestBody WishRequest request);
}
