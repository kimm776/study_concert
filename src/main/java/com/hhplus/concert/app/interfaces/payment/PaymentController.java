package com.hhplus.concert.app.interfaces.payment;

import com.hhplus.concert.app.application.PaymentFacade;
import com.hhplus.concert.app.interfaces.token.TokenController;
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

@Tag(name = "결제 API", description = "결제 정보 관리 API 입니다.")
@RequestMapping("/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentFacade paymentFacade;
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Operation(
            summary = "결제 api",
            description = "결제를 요청합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "결제가 완료되었습니다.",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰입니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.hhplus.concert.app.interfaces.token.ErrorResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.hhplus.concert.app.interfaces.token.ErrorResponse.class))
                    )
            }
    )
    @PostMapping("/pay")
    public ResponseEntity<?> payInPoint(@RequestBody PaymentRequest request) {
        try {
            paymentFacade.payInPoint(request.getTokenId(), request.getUserId(), request.getReservationId());
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            if (message.equals("유효하지 않은 토큰입니다.")) {
                return new ResponseEntity<>(new ErrorResponse("401", message), HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<>(new ErrorResponse("500", "서버 에러가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}
