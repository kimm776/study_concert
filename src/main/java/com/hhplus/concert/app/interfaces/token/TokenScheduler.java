package com.hhplus.concert.app.interfaces.token;

import com.hhplus.concert.app.application.TokenFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenScheduler {

    private final TokenFacade tokenFacade;

    // 1분마다 status 변경
    @Scheduled(cron = "0 * * * * *")
    public void updateTokenStatus() {
        tokenFacade.updateTokenStatus();
    }

    // 1분마다 만료 토큰 삭제
    @Scheduled(cron = "0 * * * * *")
    public void removeExpiredTokens() {
        tokenFacade.removeExpiredTokens();
    }

}
