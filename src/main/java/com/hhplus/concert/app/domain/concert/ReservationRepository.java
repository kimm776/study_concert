package com.hhplus.concert.app.domain.concert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    void saveReservation(Reservation reservation);

    Optional<Reservation> findByUserId(Long userId);

    List<Reservation> findReservationToDelete(LocalDateTime timeLimit);

    void deleteAllExpiredReservation(List<Reservation> reservations);

}