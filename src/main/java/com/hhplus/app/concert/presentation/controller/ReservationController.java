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

@Tag(name = "예약 API", description = "예약 정보 관리 API 입니다.")
@RequestMapping("/reservation")
@RestController
@RequiredArgsConstructor
public class ReservationController {

    @Operation(summary = "좌석 예약 요청 API", description = "좌석 예약을 요청합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> reserveConcert(@RequestParam Long userId, @RequestParam Long seatId) {

        Map<String, Object> response = new HashMap<>();
        response.put("id", 1L);
        response.put("userId", 1L);
        response.put("seatId", 1L);
        response.put("status", "RESERVED");
        response.put("createdAt", LocalDateTime.now());
        response.put("updateAt", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

}
