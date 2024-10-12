package com.hhplus.app.concert.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "콘서트 API", description = "콘서트 정보 관리 API 입니다.")
@RequestMapping("/concert")
@RestController
@RequiredArgsConstructor
public class ConcertController {

    @Operation(summary = "예약 가능 날짜 조회 API", description = "예약 가능 날짜를 조회합니다.")
    @GetMapping("/{concertId}/availableDate")
    public ResponseEntity<List<Map<String, Object>>> getAvailableDates(@PathVariable Long concertId) {
        List<Map<String, Object>> availableDates = new ArrayList<>();

        Map<String, Object> dateInfo = new HashMap<>();
        dateInfo.put("concertId", concertId);
        dateInfo.put("concertDate", "2024-10-10");
        availableDates.add(dateInfo);

        return ResponseEntity.ok(availableDates);
    }

    @Operation(summary = "예약 가능 좌석 조회 API", description = "예약 가능 좌석을 조회합니다.")
    @GetMapping("/{concertId}/availableSeats")
    public ResponseEntity<List<Map<String, Object>>> getAvailableSeats(@PathVariable Long concertId, @RequestParam String date) {
        List<Map<String, Object>> availableSeats = new ArrayList<>();

        Map<String, Object> seatInfo = new HashMap<>();
        seatInfo.put("concertId", concertId);
        seatInfo.put("date", date);
        seatInfo.put("availableSeats", 50);
        availableSeats.add(seatInfo);

        return ResponseEntity.ok(availableSeats);
    }

}
