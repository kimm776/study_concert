package com.hhplus.concert.app.interfaces.concert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveRequest {
    private Long tokenId;
    private Long concertOptionId;
    private Long concertId;
    private Long seatId;
    private Long userId;
}