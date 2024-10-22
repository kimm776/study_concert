package com.hhplus.concert.app.domain.concert;

import com.hhplus.concert.app.infrastructure.concert.ConcertOptionEntity;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ConcertOption {

    private Long id;
    private Long concertId;
    private LocalDateTime reservationDate;
    private int seats;

    public ConcertOption(Long concertId, LocalDateTime reservationDate, int seats){
        this.concertId = concertId;
        this.reservationDate = reservationDate;
        this.seats = seats;
    }

    public static ConcertOption fromEntity(ConcertOptionEntity e) {
        return new ConcertOption(e.getId(), e.getConcertId(), e.getReservationDate(), e.getSeats());
    }

    public ConcertOptionEntity toEntity() {
        return new ConcertOptionEntity(id, concertId, reservationDate, seats);
    }
}
