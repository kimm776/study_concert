package com.hhplus.concert.app.application;

import com.hhplus.concert.app.domain.customer.Customer;
import com.hhplus.concert.app.domain.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerFacade {

    private final CustomerService customerService;

    public Customer getPoint(Long userId) {
        return customerService.getPoint(userId);
    }

    public double chargePoint(Long userId, double amount) {
        return customerService.chargePoint(userId, amount);
    }
}
