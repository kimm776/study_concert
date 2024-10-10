package com.hhplus.app.concert.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "결제 API", description = "결제 정보 관리 API 입니다.")
@RequestMapping("/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    @Operation(summary = "결제 API", description = "결제 요청합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> paymentConcert(@RequestParam Long reservationId, @RequestParam Long amount) {
        Map<String, Object> response = new HashMap<>();

        response.put("reservation_id", reservationId);
        response.put("amount", amount);
        response.put("paymentDate", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

}
