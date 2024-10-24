package com.hhplus.concert.app.interfaces.concert;

import com.hhplus.concert.app.interfaces.concert.dto.ConcertOptionResponse;
import com.hhplus.concert.app.interfaces.concert.dto.ReservationResponse;
import com.hhplus.concert.app.interfaces.concert.dto.ReserveRequest;
import com.hhplus.concert.app.interfaces.concert.dto.SeatResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "콘서트 API", description = "콘서트 정보 관리 API 입니다.")
@RequestMapping("/concerts")
public interface ConcertApi {

    @Operation(
            summary = "예약 가능 날짜 조회 api",
            description = "예약 가능 날짜를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "날짜를 성공적으로 조회했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConcertOptionResponse.class))
                    )
            }
    )
    @GetMapping("/{concertId}/available-date")
    ResponseEntity<ConcertOptionResponse> getAvailableDates(@PathVariable Long concertId, @RequestParam Long tokenId);

    @Operation(
            summary = "예약 가능 좌석 조회 api",
            description = "예약 가능 좌석을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "좌석을 성공적으로 조회했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SeatResponse.class))
                    )
            }
    )
    @GetMapping("/{concertOptionId}/available-seats")
    ResponseEntity<SeatResponse> getAvailableSeats(@PathVariable Long concertOptionId, @RequestParam Long tokenId);


    @Operation(
            summary = "좌석 예약 요청 api",
            description = "좌석 예약을 요청합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "좌석 예약이 성공적으로 요청되었습니다.",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping
    ResponseEntity<ReservationResponse> reserveSeat(@RequestBody ReserveRequest request);
}
