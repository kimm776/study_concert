package com.hhplus.concert.app.interfaces.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long tokenId;
    private Long userId;
    private Long reservationId;
    private Double amount;
}
