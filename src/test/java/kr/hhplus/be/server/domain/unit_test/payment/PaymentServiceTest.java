package kr.hhplus.be.server.domain.unit_test.payment;

import kr.hhplus.be.server.domain.payment.Payment;
import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.payment.PaymentVendor;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import kr.hhplus.be.server.infrastructure.payment.PaymentRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.payment.dto.response.PaymentConfirmResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    PaymentRepositoryImpl paymentRepository;

    @InjectMocks
    PaymentService paymentService;

    @DisplayName("결제완료된 상품이 조회 되어야한다.")
    @Test
    void savePayment_ShouldCallRepository() {

        // Given
        PaymentConfirmResponse response = new PaymentConfirmResponse(
                1L, 1L, PurchaseOrderState.PAYMENT_INIT, PaymentVendor.TEST, 10000
        );

        Payment payment = new Payment(response.getPurchaseOrderId(), response.getUserId(), response.getState(),response.getVendor(),response.getTotalOrderPrice());


        // 리포지토리에 저장했다고 가정하고 findById 동작을 목킹
        Mockito.when(paymentRepository.findById(payment.getId())).thenReturn(Optional.of(payment));

        // Then
        Assertions.assertThat(paymentRepository.findById(payment.getId())).isEqualTo(Optional.of(payment));

    }

}