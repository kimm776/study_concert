package com.hhplus.concert.app.interfaces.concert;

import com.hhplus.concert.app.domain.concert.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {
    private String result;
    private String message;
    private List<Seat> availableSeats;

    public SeatResponse(List<Seat> seats) {
        this.availableSeats = seats;
    }

}
