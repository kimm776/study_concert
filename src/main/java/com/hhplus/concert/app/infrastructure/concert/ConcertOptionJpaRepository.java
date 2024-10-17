package com.hhplus.concert.app.infrastructure.concert;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertOptionJpaRepository extends JpaRepository<ConcertOptionEntity, Long> {

    List<ConcertOptionEntity> findByConcertIdAndReservationDateAfter(Long concertId, LocalDateTime now);

}
