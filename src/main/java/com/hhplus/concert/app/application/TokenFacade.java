package com.hhplus.concert.app.application;

import com.hhplus.concert.app.domain.customer.CustomerRepository;
import com.hhplus.concert.app.domain.token.Token;
import com.hhplus.concert.app.domain.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenFacade {

    private final TokenService tokenService;
    private final CustomerRepository customerRepository;

    public Token issueToken(Long userId) {
        return tokenService.issueToken(userId);
    }

    public void updateTokenStatus() {
        tokenService.updateTokenStatus();
    }

    public void removeExpiredTokens() {
        tokenService.removeExpiredTokens();
    }
}
