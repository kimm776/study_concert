package com.hhplus.concert.app.interfaces.concert;

import com.hhplus.concert.app.application.ConcertFacade;
import com.hhplus.concert.app.domain.concert.ConcertOption;
import com.hhplus.concert.app.domain.concert.Seat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "콘서트 API", description = "콘서트 정보 관리 API 입니다.")
@RequestMapping("/concerts")
@RestController
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertFacade concertFacade;

    @Operation(
            summary = "예약 가능 날짜 조회 api",
            description = "예약 가능 날짜를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "날짜를 성공적으로 조회했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConcertOptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "접근이 유효하지 않습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.hhplus.concert.app.interfaces.token.ErrorResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.hhplus.concert.app.interfaces.token.ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/{concertId}/available-date")
    public ResponseEntity<Object> getAvailableDates(@PathVariable Long concertId, @RequestParam Long tokenId) {

        try {
            List<ConcertOption> concertOptions = concertFacade.getAvailableDates(concertId, tokenId);
            ConcertOptionResponse response = new ConcertOptionResponse(concertOptions);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("401", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("500", "서버 에러가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @Operation(
            summary = "예약 가능 좌석 조회 api",
            description = "예약 가능 좌석을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "좌석을 성공적으로 조회했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SeatResponse.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "접근이 유효하지 않습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.hhplus.concert.app.interfaces.token.ErrorResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.hhplus.concert.app.interfaces.token.ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/{concertOptionId}/available-seats")
    public ResponseEntity<Object> getAvailableSeats(@PathVariable Long concertOptionId, @RequestParam Long tokenId) {

        try {
            List<Seat> seats = concertFacade.getAvailableSeats(concertOptionId, tokenId);
            SeatResponse response = new SeatResponse(seats);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse("401", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("500", "서버 에러가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @Operation(
            summary = "좌석 예약 요청 api",
            description = "좌석 예약을 요청합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "좌석 예약이 성공적으로 요청되었습니다.",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "401", description = "토큰이 만료되었습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.hhplus.concert.app.interfaces.token.ErrorResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.hhplus.concert.app.interfaces.token.ErrorResponse.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Object> reserveSeat(@RequestBody ReserveRequest request) {

        try {
            concertFacade.reserveSeat(request.getTokenId(), request.getSeatId(), request.getUserId(), request.getConcertId());
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/payment-page"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse("401", e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("500", "서버 에러가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
