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

@Tag(name = "토큰 API", description = "토큰 정보 관리 API 입니다.")
@RequestMapping("/token")
@RestController
@RequiredArgsConstructor
public class TokenController {

    @Operation(summary = "유저 토큰 발급 api", description = "유저의 토큰을 발급합니다.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUserToken(@RequestParam Long userId, @RequestParam Long seatId) {
        Map<String, Object> response = new HashMap<>();

        response.put("id", 1L);
        response.put("userId", userId);
        response.put("seatId", seatId);
        response.put("status", "RESERVED");
        response.put("createdAt", LocalDateTime.now());
        response.put("updateAt", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
}
