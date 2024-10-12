package com.hhplus.app.concert.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "유저 API", description = "유저 정보 관리 API 입니다.")
@RequestMapping("/customer")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    @Operation(summary = "유저 포인트 조회 api", description = "유저의 포인트를 조회합니다.")
    @GetMapping("/balance")
    public ResponseEntity<Map<String, Object>> getBalance(@RequestParam Long userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("balance", 10000L);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "유저 포인트 충전 api", description = "유저의 포인트를 충전합니다.")
    @PatchMapping("/balance/charge")
    public ResponseEntity<Map<String, Object>> chargeBalance(@RequestParam Long userId, @RequestParam Long chargeBalance) {
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("balance", chargeBalance + 1000);

        return ResponseEntity.ok(response);
    }

}
