package com.hhplus.concert.app.interfaces.customer;

import com.hhplus.concert.app.interfaces.customer.dto.CustomerRequest;
import com.hhplus.concert.app.interfaces.customer.dto.CustomerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "유저 정보 관리 API 입니다.")
@RequestMapping("/customers")
public interface CustomerApi {

    @Operation(
            summary = "유저 포인트 조회 api",
            description = "유저의 포인트를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "포인트 조회가 완료되었습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))
                    )
            }
    )
    @GetMapping("/point")
    ResponseEntity<CustomerResponse> getPoint(@RequestParam Long userId);

    @Operation(
            summary = "유저 포인트 충전 api",
            description = "유저의 포인트를 충전합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "포인트 충전이 완료되었습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))
                    )
            }
    )
    @PatchMapping("/point/charge")
    ResponseEntity<CustomerResponse> chargeBalance(@RequestBody CustomerRequest request);
}
