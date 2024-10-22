package com.hhplus.concert.app.infrastructure.concert;

import com.hhplus.concert.app.domain.concert.ReservationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_id")
    private Long seatId;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private double price;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Version
    private Long version;

    public ReservationEntity(Long id, Long seatId, Long userId, ReservationStatus status, double price, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.seatId = seatId;
        this.userId = userId;
        this.status = status;
        this.price = price;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
