package com.hhplus.concert.app.domain.token;

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
    public Token issueToken(Long userId) {

        if (tokenRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("유효하지 않은 접근입니다.");
        }else{
            //대기열 진입
            Token token = new Token(userId, TokenStatus.WAIT, null);
            Token SavedToken = tokenRepository.saveToQueue(token);
            System.out.println("대기열 진입 완료 token = " + SavedToken);

            //대기번호 확인
            int waitingNum = tokenRepository.findWaitingRankById(userId);
            System.out.println("내 대기번호는?? >>>>>>>> " + waitingNum + "번");

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
            System.out.println("대기열에서 삭제된 토큰 개수: " + expiredTokens.size());
        }
    }

    //토큰 만료 확인
    @Transactional
    public boolean isValidToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .map(token -> token.getStatus() == TokenStatus.ACTIVE)
                .orElse(false);
    }

}
