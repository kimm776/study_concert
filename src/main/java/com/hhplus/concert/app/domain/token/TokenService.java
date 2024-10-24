package com.hhplus.concert.app.domain.token;

import com.hhplus.concert.app.common.exception.CustomException;
import com.hhplus.concert.app.common.exception.ErrorCode;
import com.hhplus.concert.app.infrastructure.token.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    @Value("${token.max-active-tokens}")
    private int maxActiveTokens;

    //토큰 발급
    @Transactional
    public Long issueToken(Long userId) {

        if (tokenRepository.existsByUserId(userId)) {
            throw new CustomException(ErrorCode.NOT_FOUND, "유효하지 않은 접근입니다.");
        }else{
            //대기열 진입
            Token token = new Token(userId, TokenStatus.WAIT, null);
            Long SavedToken = tokenRepository.saveToQueue(token);

            //대기번호 확인
            int waitingNum = tokenRepository.findWaitingRankById(userId);

            return SavedToken;
        }
    }

    @Transactional
    public void updateTokenStatus() {

        int activeTokenCount = tokenRepository.countByActiveStatus(TokenStatus.ACTIVE); //활성화 토큰 개수 확인
        if (activeTokenCount < maxActiveTokens) {

            List<Token> waitingTokens = tokenRepository.findByWaitStatus("WAIT"); //대기 토큰 리스트 조회
            int tokensToActivate = Math.min(maxActiveTokens - activeTokenCount, waitingTokens.size()); // 대기 > 활성화로 변경해야 할 개수 조회

            for (int i = 0; i < tokensToActivate; i++) {

                Token token = waitingTokens.get(i);
                Token updatedToken = token.toBuilder()
                                        .status(TokenStatus.ACTIVE)
                                        .updateAt(LocalDateTime.now())
                                        .build();
                tokenRepository.saveToQueue(updatedToken);

            }
        }

    }

    @Transactional
    public void removeExpiredTokens() {
        LocalDateTime timeExpired = LocalDateTime.now().minusMinutes(10);
        List<Token> expiredTokens = tokenRepository.findTokensToDelete(timeExpired);

        if (expiredTokens != null && !expiredTokens.isEmpty()) {
            tokenRepository.deleteAllExpiredTokens(expiredTokens);
        }
    }

    //토큰 만료 확인
    @Transactional
    public boolean isValidToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .map(token -> token.getStatus() == TokenStatus.ACTIVE)
                .orElse(false);
    }

    // 토큰 조회
    @Transactional
    public Token checkToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유효하지 않은 접근입니다."));
    }

    @Transactional
    public void deleteById(Long tokenId) {
        tokenRepository.deleteById(tokenId);
    }
}
