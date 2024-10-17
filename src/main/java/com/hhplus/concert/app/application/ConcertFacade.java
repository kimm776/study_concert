package com.hhplus.concert.app.application;

import com.hhplus.concert.app.domain.concert.ConcertOption;
import com.hhplus.concert.app.domain.concert.ConcertService;
import com.hhplus.concert.app.domain.concert.Seat;
import com.hhplus.concert.app.domain.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertFacade {

    private final ConcertService concertService;
    private final TokenService tokenService;

    //토큰 만료 확인
    private void validateToken(Long tokenId) {
        boolean isTokenValid = tokenService.isValidToken(tokenId);
        if (!isTokenValid) {
            throw new IllegalArgumentException("토큰이 만료되었습니다.");
        }
    }

    public List<ConcertOption> getAvailableDates(Long concertId, Long tokenId) {
        validateToken(tokenId);
        return concertService.getAvailableDates(concertId, tokenId);
    }

    public List<Seat> getAvailableSeats(Long concertOptionId, Long tokenId) {
        validateToken(tokenId);
        return concertService.getAvailableSeats(concertOptionId, tokenId);
    }

    public void reserveSeat(Long tokenId, Long seatId, Long userId, Long concertId) {
        validateToken(tokenId);
        concertService.reserveSeat(seatId, userId, concertId);
    }

    public void removeExpiredReservation() {
        concertService.removeExpiredReservation();
    }
}
