package com.hhplus.concert.app.infrastructure.concert.reservation;

import com.hhplus.concert.app.domain.concert.reservation.Reservation;
import com.hhplus.concert.app.domain.concert.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Long saveReservation(Reservation reservation) {
        return reservationJpaRepository.save(reservation.toEntity()).getId();
    }

    @Override
    public Optional<Reservation> findByUserId(Long userId) {
        return reservationJpaRepository.findByUserId(userId)
                .map(Reservation::fromEntity);
    }

    @Override
    public List<Reservation> findReservationToDelete(LocalDateTime timeLimit) {
        List<ReservationEntity> entities = reservationJpaRepository.findReservationToDelete(timeLimit);
        return entities.stream()
                .map(Reservation::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllExpiredReservation(List<Reservation> reservations) {
        List<ReservationEntity> entities = reservations.stream()
                .map(Reservation::toEntity)
                .collect(Collectors.toList());

        reservationJpaRepository.deleteAll(entities);
    }
}
