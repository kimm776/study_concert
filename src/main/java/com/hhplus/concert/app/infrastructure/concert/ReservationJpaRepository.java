package com.hhplus.concert.app.infrastructure.concert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    Optional<ReservationEntity> findByUserId(Long userId);

    @Query("SELECT t FROM ReservationEntity t WHERE t.status = 'RESERVED' AND t.updateAt <= :timeLimit")
    List<ReservationEntity> findReservationToDelete(@Param("timeLimit") LocalDateTime timeLimit);
}
