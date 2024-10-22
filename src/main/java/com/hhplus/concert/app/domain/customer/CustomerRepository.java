package com.hhplus.concert.app.domain.customer;

import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> findById(Long userId);

    int chargePoint(Long userId, double chargeResult, Long version);

    int updateBalanceAndIncrementVersion(Long userId, double point, Long version);

}