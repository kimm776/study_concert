package com.hhplus.concert.app.infrastructure.customer;

import com.hhplus.concert.app.domain.customer.Customer;
import com.hhplus.concert.app.domain.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    @Override
    public Optional<Customer> findById(Long userId) {
        return customerJpaRepository.findById(userId)
                .map(Customer::fromEntity);
    }

    @Override
    public int chargePoint(Long userId, double amount, Long version) {
        return customerJpaRepository.chargePoint(userId, amount, version);
    }

    @Override
    public int updateBalanceAndIncrementVersion(Long userId, double point, Long version) {
        int updatedRows = customerJpaRepository.updateBalanceAndIncrementVersion(userId, point, version);
        return updatedRows;
    }
}
