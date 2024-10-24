package com.hhplus.concert.app.interfaces.customer;

import com.hhplus.concert.app.application.CustomerFacade;
import com.hhplus.concert.app.domain.customer.Customer;
import com.hhplus.concert.app.interfaces.customer.dto.CustomerRequest;
import com.hhplus.concert.app.interfaces.customer.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi{

    private final CustomerFacade customerFacade;

    @Override
    public ResponseEntity<CustomerResponse> getPoint(@RequestParam Long userId) {
        Customer customer = customerFacade.getPoint(userId);
        CustomerResponse response = new CustomerResponse(customer.getPoint());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CustomerResponse> chargeBalance(@RequestBody CustomerRequest request) {
        double newPoint = customerFacade.chargePoint(request.getUserId(), request.getAmount());
        CustomerResponse response = new CustomerResponse(newPoint);
        return ResponseEntity.ok(response);
    }

}
