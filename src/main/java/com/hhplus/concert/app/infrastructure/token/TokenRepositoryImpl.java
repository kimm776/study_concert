package com.hhplus.concert.app.infrastructure.token;

import com.hhplus.concert.app.domain.token.Token;
import com.hhplus.concert.app.domain.token.TokenRepository;
import com.hhplus.concert.app.domain.token.TokenStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public boolean existsByUserId(Long userId) {
        return tokenJpaRepository.existsByUserId(userId);
    }

    @Override
    public Token saveToQueue(Token token) {
        TokenEntity entity = tokenJpaRepository.save(token.toEntity());
        return Token.fromEntity(entity);
    }

    @Override
    public int findWaitingRankById(Long userId) {
        return tokenJpaRepository.findWaitingRankById(userId);
    }

    @Override
    public int countByActiveStatus(TokenStatus status) {
        return tokenJpaRepository.countByActiveStatus(status);
    }

    @Override
    public List<Token> findByWaitStatus(String status) {
        List<TokenEntity> entities = tokenJpaRepository.findByWaitStatus();
        return entities.stream()
                .map(Token::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Token> findTokensToDelete(LocalDateTime timeLimit) {
        List<TokenEntity> entities = tokenJpaRepository.findTokensToDelete(timeLimit);
        return entities.stream()
                .map(Token::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllExpiredTokens(List<Token> tokens) {
        List<TokenEntity> tokenEntities = tokens.stream()
                .map(Token::toEntity)
                .collect(Collectors.toList());

        tokenJpaRepository.deleteAll(tokenEntities);
    }

    @Override
    public Optional<Token> findById(Long tokenId) {
        return tokenJpaRepository.findById(tokenId)
                .map(Token::fromEntity);
    }
}
