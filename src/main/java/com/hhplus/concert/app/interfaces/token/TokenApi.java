package com.hhplus.concert.app.interfaces.token;

import com.hhplus.concert.app.interfaces.token.dto.TokenRequest;
import com.hhplus.concert.app.interfaces.token.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "토큰 API", description = "토큰 정보 관리 API 입니다.")
@RequestMapping("/tokens")
public interface TokenApi {

    @Operation(
            summary = "유저 토큰 발급 api",
            description = "유저의 토큰을 발급합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "토큰이 발급되었습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))
                    )
            }
    )
    @PostMapping("/issue-token/")
    ResponseEntity<TokenResponse> issueToken(@RequestBody TokenRequest request);

}
