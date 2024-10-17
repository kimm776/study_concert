package com.hhplus.concert.app.domain.concert;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertOptionRepository concertOptionRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;

    //콘서트 날짜 조회
    @Transactional
    public List<ConcertOption> getAvailableDates(Long concertId, Long tokenId) {
        Optional<Concert> concerts = concertRepository.findById(concertId);
        if (concerts.isEmpty()) {
            throw new IllegalArgumentException("콘서트가 존재하지 않습니다.");
        }

        List<ConcertOption> concertOption = concertOptionRepository.findByConcertIdAndReservationDateAfter(concertId);

        return concertOption;
    }

    //콘서트 좌석 조회
    @Transactional
    public List<Seat> getAvailableSeats(Long concertOptionId, Long tokenId) {
        //콘서트 옵션 아이디 체크
        Optional<ConcertOption> concertOption = concertOptionRepository.findById(concertOptionId);
        if (concertOption.isEmpty()) {
            throw new IllegalArgumentException("해당 콘서트 옵션이 존재하지 않습니다.");
        }

        //좌석 존재 유무 체크
        List<Seat> seats = seatRepository.findByConcertOptionId(concertOptionId);
        if (seats.isEmpty()) {
            throw new IllegalArgumentException("해당 콘서트에 대한 좌석이 존재하지 않습니다.");
        }

        // 잔여 좌석수 체크
        long occupiedSeatCount = seats.stream()
                .filter(seat -> seat.getStatus() == SeatStatus.OCCUPIED)
                .count();
        if (occupiedSeatCount >= concertOption.get().getSeats()) {
            throw new IllegalArgumentException("해당 콘서트의 잔여 좌석이 없습니다.");
        }

        return seats;
    }

    //좌석 예약 요청
    @Transactional
    public void reserveSeat(Long seatId, Long userId, Long concertId) {
        try {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new IllegalArgumentException("좌석이 존재하지 않습니다."));

            if (seat.getStatus() == SeatStatus.OCCUPIED) {
                throw new IllegalStateException("이미 선택된 좌석입니다.");
            }

            //좌석 선점
            Seat updateSeat = seat.toBuilder()
                                .userId(userId)
                                .status(SeatStatus.OCCUPIED)
                                .updateAt(LocalDateTime.now())
                                .build();
            seatRepository.saveSeat(updateSeat);

            // 예약 정보 생성
            Optional<Concert> concert = concertRepository.findById(concertId);
            Reservation reservation = new Reservation(seatId, userId, ReservationStatus.RESERVED, concert.get().getPrice(), LocalDateTime.now());
            reservationRepository.saveReservation(reservation);

        } catch (OptimisticLockException e) {
            throw new IllegalStateException("좌석 예약에 실패했습니다. 다른 사용자가 이미 좌석을 예약했을 수 있습니다.");
        }
    }

    //예약 내역 조회
    public Reservation findByUserId(Long userId) {
        return reservationRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("예약이 존재하지 않습니다."));
    }

    //예약 결제 완료 내역 저장
    @Transactional
    public void saveReservation(Reservation reservation) {
        reservationRepository.saveReservation(reservation);
    }

    //만료된 예약내역 삭제 (좌석 선점 후 5분내에 결제하지 않는 경우)
    @Transactional
    public void removeExpiredReservation() {
        LocalDateTime timeExpired = LocalDateTime.now().minusMinutes(5);
        List<Reservation> expiredReservation = reservationRepository.findReservationToDelete(timeExpired);

        if (expiredReservation != null && !expiredReservation.isEmpty()) {
            reservationRepository.deleteAllExpiredReservation(expiredReservation);
            System.out.println("만료된 예약 삭제 개수: " + expiredReservation.size());
        }
    }
}
