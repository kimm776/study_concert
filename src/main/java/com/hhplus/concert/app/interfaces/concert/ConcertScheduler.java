package com.hhplus.concert.app.interfaces.concert;

import com.hhplus.concert.app.application.ConcertFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertScheduler {

    private final ConcertFacade concertFacade;

    // 1분마다 만료된 예약내역 삭제
    @Scheduled(cron = "0 * * * * *")
    public void removeExpiredTokens() {
        concertFacade.removeExpiredReservation();
    }

}
