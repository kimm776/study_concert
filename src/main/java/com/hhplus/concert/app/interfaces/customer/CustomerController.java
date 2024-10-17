package com.hhplus.concert.app.interfaces.customer;

import com.hhplus.concert.app.application.CustomerFacade;
import com.hhplus.concert.app.domain.customer.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Tag(name = "유저 API", description = "유저 정보 관리 API 입니다.")
@RequestMapping("/customers")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerFacade customerFacade;

    @Operation(
            summary = "유저 포인트 조회 api",
            description = "유저의 포인트를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "포인트 조회가 완료되었습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "접근이 유효하지 않습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.hhplus.concert.app.interfaces.token.ErrorResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.hhplus.concert.app.interfaces.token.ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/point")
    public ResponseEntity<Object> getPoint(@RequestParam Long userId) {
        try {
            Customer customer = customerFacade.getPoint(userId);
            CustomerResponse response = new CustomerResponse(customer.getPoint());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ErrorResponse("401", "접근이 유효하지 않습니다."), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorResponse("500", "서버 에러가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @Operation(
            summary = "유저 포인트 충전 api",
            description = "유저의 포인트를 충전합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "포인트 충전이 완료되었습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "접근이 유효하지 않습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.hhplus.concert.app.interfaces.token.ErrorResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.hhplus.concert.app.interfaces.token.ErrorResponse.class))
                    )
            }
    )
    @PatchMapping("/point/charge")
    public ResponseEntity<?> chargeBalance(@RequestBody CustomerRequest request) {
        try {
            double newPoint = customerFacade.chargePoint(request.getUserId(), request.getAmount());
            CustomerResponse response = new CustomerResponse(newPoint);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ErrorResponse("401", "접근이 유효하지 않습니다."), HttpStatus.NOT_FOUND);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ErrorResponse("500", "서버 에러가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
