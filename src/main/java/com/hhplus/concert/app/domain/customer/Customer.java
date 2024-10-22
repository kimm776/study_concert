package com.hhplus.concert.app.domain.customer;

import com.hhplus.concert.app.infrastructure.customer.CustomerEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Customer {

    private Long id;
    private double point;
    private Long version;

    public Customer(Long id, double point) {
        this.id = id;
        this.point = point;
    }

    public static Customer fromEntity(CustomerEntity e) {
        return new Customer(e.getId(), e.getPoint(), e.getVersion());
    }

    public CustomerEntity toEntity() {
        return new CustomerEntity(id, point, version);
    }

    public void decreasePoint(double price) {
        if (point < price) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }
        point -= price;
    }
}
