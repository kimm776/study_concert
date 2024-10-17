package com.hhplus.concert.app.domain.concert;

import java.util.Optional;

public interface ConcertRepository {

    Optional<Concert> findById(Long concertId);

}