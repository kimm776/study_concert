package com.hhplus.concert.app.domain.customer;

import com.hhplus.concert.app.infrastructure.customer.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("[예외] 사용자 포인트 조회 : 사용자가 존재하지 않는 경우")
    @Test
    void testGetPoint_UserNotFound() {
        Long userId = 1L;

        // Given
        given(customerRepository.findById(userId)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> customerService.getPoint(userId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("접근이 유효하지 않습니다.");
    }

    @DisplayName("[정상] 사용자 포인트 조회")
    @Test
    void testGetPoint_Success() {
        Long userId = 1L;
        Customer mockCustomer = new Customer(userId, 100.0);

        // Given
        given(customerRepository.findById(userId)).willReturn(Optional.of(mockCustomer));

        // When
        Customer result = customerService.getPoint(userId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getPoint()).isEqualTo(100.0);
    }

    @DisplayName("[예외] 포인트 충전: 유효하지 않은 금액")
    @Test
    void testChargePoint_InvalidAmount() {
        Long userId = 1L;
        double chargePoint = -50.0;

        // When & Then
        assertThatThrownBy(() -> customerService.chargePoint(userId, chargePoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("충전 금액이 유효하지 않습니다.");
    }

    @DisplayName("[예외] 포인트 충전: 사용자 찾을 수 없음")
    @Test
    void testChargePoint_UserNotFound() {
        Long userId = 1L;
        double chargePoint = 100.0;

        // Given
        given(customerRepository.findById(userId)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> customerService.chargePoint(userId, chargePoint))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("접근이 유효하지 않습니다.");
    }

    @DisplayName("[예외] 포인트 충전: 충전 실패")
    @Test
    void testChargePoint_ChargeFailed() {
        // given
        Long userId = 1L;
        double initialBalance = 100.0;
        double chargeAmount = 50.0;
        Customer customer = new Customer(userId, initialBalance, 1L);

        given(customerRepository.findById(userId)).willReturn(Optional.of(customer));
        given(customerRepository.chargePoint(userId, initialBalance + chargeAmount, customer.getVersion())).willReturn(0);

        // When & Then
        assertThatThrownBy(() -> customerService.chargePoint(userId, chargeAmount))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("충전에 실패했습니다. 이미 충전이 완료됐을 수 있습니다.");
    }

    @DisplayName("[예외] 결제 : 잔액이 부족합니다.")
    @Test
    void testUsePoint_NotEnoughPoint() {
        // Given
        Long userId = 1L;
        double priceToUse = 100.0;
        Customer customer = new Customer(userId, 50.0);

        given(customerRepository.findById(userId)).willReturn(Optional.of(customer));

        // When & Then
        assertThatThrownBy(() -> customerService.usePoint(userId, priceToUse))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("잔액이 부족합니다.");
    }
}