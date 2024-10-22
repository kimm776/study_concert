package com.hhplus.concert.app.interfaces.token;

import com.hhplus.concert.app.application.TokenFacade;
import com.hhplus.concert.app.domain.token.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "토큰 API", description = "토큰 정보 관리 API 입니다.")
@RequestMapping("/tokens")
@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenFacade tokenFacade;
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Operation(
            summary = "유저 토큰 발급 api",
            description = "유저의 토큰을 발급합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "토큰이 발급되었습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @PostMapping("/issue-token/")
    public ResponseEntity<Object> issueToken(@RequestBody TokenRequest request) {

        try {
            Token token = tokenFacade.issueToken(request.getUserId());
            TokenResponse response = new TokenResponse("200", "토큰이 발급되었습니다.", token.getId());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("401", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("500", "서버 에러가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }

    }
}
