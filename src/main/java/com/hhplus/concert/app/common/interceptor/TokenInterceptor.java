package com.hhplus.concert.app.common.interceptor;


import com.hhplus.concert.app.common.exception.CustomException;
import com.hhplus.concert.app.common.exception.ErrorCode;
import com.hhplus.concert.app.domain.token.TokenService;
import com.hhplus.concert.app.domain.token.TokenStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenValue = request.getHeader("Authorization");
        logger.info("Authorization 헤더: {}", tokenValue);

        if (tokenValue == null || tokenValue.isEmpty()) {
            logger.warn("토큰이 제공되지 않았습니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 필요합니다.");
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        boolean isTokenValid = tokenService.isValidToken(Long.valueOf(tokenValue));
        if (!isTokenValid) {
            logger.warn("유효하지 않은 토큰: {}", tokenValue);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        logger.info("토큰이 활성 상태입니다.");
        return true;
    }
}