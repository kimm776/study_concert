package com.hhplus.concert.app.domain.token;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository {

    boolean existsByUserId(Long userId);

    Token saveToQueue(Token token);

    int findWaitingRankById(Long userId);

    int countByActiveStatus(TokenStatus status);

    List<Token> findByWaitStatus(String status);

    List<Token> findTokensToDelete(LocalDateTime timeLimit);

    void deleteAllExpiredTokens(List<Token> tokens);

    Optional<Token> findById(Long tokenId);

    void deleteById(Long tokenId);
}