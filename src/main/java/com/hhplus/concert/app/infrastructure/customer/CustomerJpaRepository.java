package com.hhplus.concert.app.infrastructure.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE CustomerEntity c SET c.point = c.point + :amount WHERE c.id = :userId AND c.version = :version")
    int chargePoint(@Param("userId") Long userId, @Param("amount") double amount, @Param("version") Long version);

    @Modifying
    @Query("UPDATE CustomerEntity c SET c.point = :point, c.version = c.version + 1 WHERE c.id = :userId AND c.version = :version")
    int updateBalanceAndIncrementVersion(Long userId, double point, Long version);
}
