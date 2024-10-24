package com.hhplus.concert.app.domain.concert;

import com.hhplus.concert.app.domain.concert.Concert;

import java.util.Optional;

public interface ConcertRepository {

    Optional<Concert> findById(Long concertId);

}