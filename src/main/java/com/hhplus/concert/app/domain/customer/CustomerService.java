package com.hhplus.concert.app.domain.customer;

import com.hhplus.concert.app.common.exception.CustomException;
import com.hhplus.concert.app.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    public Customer getPoint(Long userId) {
        return customerRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유효하지 않은 접근입니다."));
    }

    @Transactional
    public double chargePoint(Long userId, Double chargePoint) {

        if (chargePoint == null || chargePoint <= 0) {
            throw new CustomException(ErrorCode.NOT_FOUND, "충전 금액이 유효하지 않습니다.");
        }

        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유효하지 않은 접근입니다."));

        double userPoint = customer.getPoint();
        double chargeResult = userPoint + chargePoint;

        // 포인트 충전값 저장
        int updatedRows = customerRepository.chargePoint(userId, chargeResult, customer.getVersion());
        if (updatedRows == 0) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "충전에 실패했습니다. 이미 충전이 완료됐을 수 있습니다.");
        }

        // 재검증
        Customer chargeCustomer = getPoint(userId);
        if (Math.abs(chargeResult - chargeCustomer.getPoint()) > 0.01) {
            throw new CustomException(ErrorCode.NOT_FOUND, "충전 금액이 일치하지 않습니다.");
        }

        return chargeResult;
    }

    //결제
    @Transactional
    public void usePoint(Long userId, double price) {
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "유효하지 않은 접근입니다."));

        if(customer.getPoint() > price){
            customer.decreasePoint(price);

            int updatedRows = customerRepository.updateBalanceAndIncrementVersion(userId, customer.getPoint(), customer.getVersion());
            if (updatedRows == 0) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "결제에 실패했습니다. 이미 결제가 완료됐을 수 있습니다.");
            }
        }else {
            throw new CustomException(ErrorCode.NOT_FOUND, "잔액이 부족합니다.");
        }
    }

}
