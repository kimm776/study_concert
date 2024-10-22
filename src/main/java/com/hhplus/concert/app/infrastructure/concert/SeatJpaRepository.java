package com.hhplus.concert.app.infrastructure.concert;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatJpaRepository extends JpaRepository<SeatEntity, Long> {

    List<SeatEntity> findByConcertOptionId(Long concertOptionId);

    Optional<SeatEntity> findById(Long seatId);

}
