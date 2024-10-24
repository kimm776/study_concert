package com.hhplus.concert.app.domain.concert.concertOption;

import com.hhplus.concert.app.domain.concert.concertOption.ConcertOption;

import java.util.List;
import java.util.Optional;

public interface ConcertOptionRepository {

    List<ConcertOption> findByConcertIdAndReservationDateAfter(Long concertId);
    Optional<ConcertOption> findById(Long concertOptionId);

}