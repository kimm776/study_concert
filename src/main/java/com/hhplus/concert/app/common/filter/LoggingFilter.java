package com.hhplus.concert.app.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 필터 초기화
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("logging filter init");
    }

    // 요청 처리
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        long startTime = System.currentTimeMillis();

        // 요청 파라미터 문자열로 변환
        Map<String, String> parameters = httpRequest.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> String.join(",", entry.getValue())
                ));
        String paramsJson = objectMapper.writeValueAsString(parameters);

        // 요청 로깅
        logger.info("요청: Method={}, Request URI={}, Params={}",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                paramsJson);

        // 로깅
        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - startTime;

        // 응답 로깅
        logger.info("응답: 상태={}, 소요시간={}ms", httpResponse.getStatus(), duration);

    }

    // 필터 소멸
    @Override
    public void destroy() {
        log.info("logging filter destroy");
    }

}
