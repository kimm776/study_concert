package com.hhplus.concert.app.domain.concert;

import java.util.List;
import java.util.Optional;

public interface SeatRepository {

    List<Seat> findByConcertOptionId(Long concertOptionId);

    Optional<Seat> findById(Long seatId);

    void saveSeat(Seat seat);

}