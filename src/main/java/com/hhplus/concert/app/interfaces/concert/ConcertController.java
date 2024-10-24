package com.hhplus.concert.app.interfaces.concert;

import com.hhplus.concert.app.application.ConcertFacade;
import com.hhplus.concert.app.domain.concert.concertOption.ConcertOption;
import com.hhplus.concert.app.domain.concert.seat.Seat;
import com.hhplus.concert.app.interfaces.concert.dto.ConcertOptionResponse;
import com.hhplus.concert.app.interfaces.concert.dto.ReservationResponse;
import com.hhplus.concert.app.interfaces.concert.dto.ReserveRequest;
import com.hhplus.concert.app.interfaces.concert.dto.SeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConcertController implements ConcertApi {

    private final ConcertFacade concertFacade;

    @Override
    public ResponseEntity<ConcertOptionResponse> getAvailableDates(@PathVariable Long concertId, @RequestParam Long tokenId) {

        List<ConcertOption> concertOptions = concertFacade.getAvailableDates(concertId, tokenId);
        ConcertOptionResponse response = new ConcertOptionResponse(concertOptions);
        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<SeatResponse> getAvailableSeats(@PathVariable Long concertOptionId, @RequestParam Long tokenId) {

        List<Seat> seats = concertFacade.getAvailableSeats(concertOptionId, tokenId);
        SeatResponse response = new SeatResponse(seats);
        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<ReservationResponse> reserveSeat(@RequestBody ReserveRequest request) {
        return ResponseEntity.ok(new ReservationResponse(concertFacade.reserveSeat(request.getTokenId(), request.getSeatId(), request.getUserId(), request.getConcertId())));

    }
}
