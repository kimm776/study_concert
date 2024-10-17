package com.hhplus.concert.app.domain.concert;

import com.hhplus.concert.app.infrastructure.concert.SeatEntity;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Builder(toBuilder = true)
public class Seat {

    private Long id;
    private Long concertOptionId;
    private Long userId;
    private String seatNumber;
    private SeatStatus status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Seat(Long concertOptionId, Long userId, String seatNumber, SeatStatus status, LocalDateTime updateAt) {
        this.concertOptionId = concertOptionId;
        this.userId = userId;
        this.seatNumber = seatNumber;
        this.status = status;
        createAt = LocalDateTime.now();
        this.updateAt = updateAt;
    }

    public static Seat fromEntity(SeatEntity e) {
        return new Seat(e.getId(), e.getConcertOptionId(), e.getUserId(), e.getSeatNumber(), e.getStatus(), e.getCreateAt(), e.getUpdateAt());
    }

    public SeatEntity toEntity() {
        return new SeatEntity(id, concertOptionId, userId, seatNumber, status, createAt, updateAt);
    }

    public void changeStatus(SeatStatus newStatus) {
        this.status = newStatus;
    }
}
