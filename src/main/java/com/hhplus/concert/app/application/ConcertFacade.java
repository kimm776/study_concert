package com.hhplus.concert.app.application;

import com.hhplus.concert.app.domain.concert.ConcertService;
import com.hhplus.concert.app.domain.concert.concertOption.ConcertOption;
import com.hhplus.concert.app.domain.concert.seat.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertFacade {

    private final ConcertService concertService;

    public List<ConcertOption> getAvailableDates(Long concertId) {
        return concertService.getAvailableDates(concertId);
    }

    public List<Seat> getAvailableSeats(Long concertOptionId) {
        return concertService.getAvailableSeats(concertOptionId);
    }

    public Long reserveSeat(Long seatId, Long userId, Long concertId) {
        return concertService.reserveSeat(seatId, userId, concertId);
    }

    public void removeExpiredReservation() {
        concertService.removeExpiredReservation();
    }
}
