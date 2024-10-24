package com.hhplus.concert.app.interfaces.payment;

import com.hhplus.concert.app.application.PaymentFacade;
import com.hhplus.concert.app.interfaces.payment.dto.PaymentRequest;
import com.hhplus.concert.app.interfaces.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {

    private final PaymentFacade paymentFacade;

    @Override
    public ResponseEntity<PaymentResponse> payInPoint(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(new PaymentResponse(paymentFacade.payInPoint(request.getTokenId(), request.getUserId(), request.getReservationId())));
    }

}
