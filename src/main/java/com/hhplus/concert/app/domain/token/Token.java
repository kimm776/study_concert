package com.hhplus.concert.app.domain.token;

import com.hhplus.concert.app.infrastructure.token.TokenEntity;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Builder(toBuilder = true)
public class Token {
    private Long id;
    private Long userId;
    private TokenStatus status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Token(Long userId, TokenStatus status, LocalDateTime updateAt) {
        this.userId = userId;
        this.status = status;
        createAt = LocalDateTime.now();
        this.updateAt = updateAt;
    }

    public static Token fromEntity(TokenEntity e) {
        return new Token(e.getId(), e.getUserId(), e.getStatus(), e.getCreateAt(), e.getUpdateAt());
    }

    public TokenEntity toEntity() {
        return new TokenEntity(id, userId, status, createAt, updateAt);
    }

}
