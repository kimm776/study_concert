package com.hhplus.concert.app.application;

import com.hhplus.concert.app.domain.concert.ConcertService;
import com.hhplus.concert.app.domain.concert.Reservation;
import com.hhplus.concert.app.domain.customer.CustomerService;
import com.hhplus.concert.app.domain.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private static final Logger logger = LoggerFactory.getLogger(PaymentFacade.class);
    private final ConcertService concertService;
    private final CustomerService customerService;
    private final TokenService tokenService;

    //토큰 만료 확인
    private void validateToken(Long tokenId) {
        boolean isTokenValid = tokenService.isValidToken(tokenId);
        if (!isTokenValid) {
            throw new IllegalArgumentException("토큰이 만료되었습니다.");
        }
    }

    @Transactional
    public void payInPoint(Long tokenId, Long userId, Long reservationId) {
        validateToken(tokenId);
        //예약정보 확인
        Reservation reservation = null;
        try {
            reservation = concertService.findByUserId(userId);
        }catch (Exception e) {
            logger.error("예약 조회 실패: {}", e.getMessage());
        }

        //결제
        double price = reservation.getPrice();
        try {
            customerService.usePoint(userId, price);
        } catch (Exception e) {
            logger.error("결제 실패: {}", e.getMessage());
            throw e;
        }

        //예약완료
        try {
            reservation.statusToPayment();
            concertService.saveReservation(reservation);
        } catch (Exception e) {
            logger.error("예약 완료 처리 실패: {}", e.getMessage());
            throw e;
        }

        //대기열 삭제
        tokenService.deleteById(tokenId);

    }
}
