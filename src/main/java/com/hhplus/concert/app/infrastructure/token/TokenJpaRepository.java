package com.hhplus.concert.app.infrastructure.token;

import com.hhplus.concert.app.domain.token.Token;
import com.hhplus.concert.app.domain.token.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {

    boolean existsByUserId(Long userId);

    TokenEntity save(Token token);

    @Query(value = "SELECT r.rank FROM ( " +
            "SELECT user_id, ROW_NUMBER() OVER (ORDER BY create_at) AS rank " +
            "FROM token " +
            "WHERE status = 'WAIT') AS r " +
            "WHERE user_id = :userId", nativeQuery = true)
    int findWaitingRankById(@Param("userId") Long userId);

    @Query("SELECT COUNT(t) FROM TokenEntity t WHERE t.status = :status")
    int countByActiveStatus(@Param("status") TokenStatus status);

    @Query("SELECT t FROM TokenEntity t WHERE t.status = 'WAIT' ORDER BY t.createAt")
    List<TokenEntity> findByWaitStatus();

    @Query("SELECT t FROM TokenEntity t WHERE t.status = 'ACTIVE' AND t.updateAt <= :timeLimit")
    List<TokenEntity> findTokensToDelete(@Param("timeLimit") LocalDateTime timeLimit);

    Optional<TokenEntity> findById(Long tokenId);
}
