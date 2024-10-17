package com.hhplus.concert.app.infrastructure.concert;

import com.hhplus.concert.app.domain.concert.SeatStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "seat")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "concert_option_id")
    private Long concertOptionId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "seat_number")
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Version
    private Long version;

    public SeatEntity(Long id, Long concertOptionId, Long userId, String seatNumber, SeatStatus status, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.concertOptionId = concertOptionId;
        this.userId = userId;
        this.seatNumber = seatNumber;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
