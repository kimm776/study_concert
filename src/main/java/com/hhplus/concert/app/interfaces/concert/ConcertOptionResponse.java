package com.hhplus.concert.app.interfaces.concert;

import com.hhplus.concert.app.domain.concert.ConcertOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertOptionResponse {
    private String result;
    private String message;
    private List<LocalDateTime> availableDates;

    public ConcertOptionResponse(List<ConcertOption> concertOptions) {
        this.availableDates = concertOptions.stream()
                .map(ConcertOption::getReservationDate)
                .collect(Collectors.toList());
    }

}
