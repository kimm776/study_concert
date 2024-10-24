package com.hhplus.concert.app.interfaces.payment;

import com.hhplus.concert.app.interfaces.payment.dto.PaymentRequest;
import com.hhplus.concert.app.interfaces.payment.dto.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "결제 API", description = "결제 정보 관리 API 입니다.")
@RequestMapping("/payments")
public interface PaymentApi {

    @Operation(
            summary = "결제 api",
            description = "결제를 요청합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "결제가 완료되었습니다.",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping("/pay")
    ResponseEntity<PaymentResponse> payInPoint(@RequestBody PaymentRequest request);

}
