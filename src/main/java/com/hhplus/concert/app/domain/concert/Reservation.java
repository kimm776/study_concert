package com.hhplus.concert.app.domain.concert;

import com.hhplus.concert.app.infrastructure.concert.ReservationEntity;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Reservation {

    private Long id;
    private Long seatId;
    private Long userId;
    private ReservationStatus status;
    private double price;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Reservation(Long seatId, Long userId, ReservationStatus status, double price, LocalDateTime updateAt) {
        this.seatId = seatId;
        this.userId = userId;
        this.status = status;
        this.price = price;
        this.createAt = LocalDateTime.now();
        this.updateAt = updateAt;
    }

    public static Reservation fromEntity(ReservationEntity e) {
        return new Reservation(e.getId(), e.getSeatId(), e.getUserId(), e.getStatus(), e.getPrice(), e.getCreateAt(), e.getUpdateAt());
    }

    public ReservationEntity toEntity() {
        return new ReservationEntity(id, seatId, userId, status, price, createAt, updateAt);
    }

    public void statusToPayment() {
        this.status = ReservationStatus.PAYMENT;
    }
}
