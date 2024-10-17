package com.hhplus.concert.app.domain.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenRepository tokenRepository;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("[예외] 토큰 발급 : 이미 존재하는 유저")
    @Test
    void testIssueToken_UserAlreadyHasToken() {
        // given
        Long userId = 1L;
        given(tokenRepository.existsByUserId(userId)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> tokenService.issueToken(userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 접근입니다.");
    }

    @DisplayName("[정상] 토큰 발급")
    @Test
    void issueToken_ExistingUser_TokenIssued() {
        // Given
        Long userId = 1L;
        Token token = new Token(userId, TokenStatus.WAIT, null);
        given(tokenRepository.saveToQueue(any(Token.class))).willReturn(token);

        // When
        Token result = tokenService.issueToken(userId);

        // Then
        assertThat(token).isNotNull();
        System.out.println("result = " + result);
    }

}