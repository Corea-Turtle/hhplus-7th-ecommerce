package kr.hhplus.be.server.domain.unit_test.payment;

import kr.hhplus.be.server.domain.payment.PaymentService;
import kr.hhplus.be.server.domain.payment.PaymentVendor;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import kr.hhplus.be.server.infrastructure.payment.PaymentRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.payment.dto.response.PaymentConfirmResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    PaymentRepositoryImpl paymentRepository;

    @InjectMocks
    PaymentService paymentService;

    @Test
    void savePayment_ShouldCallRepository() {

        // Given
        PaymentConfirmResponse response = new PaymentConfirmResponse(
                1L, 1L, PurchaseOrderState.PAYMENT_INIT, PaymentVendor.TEST, 10000
        );

        // When
        paymentService.savePayment(response);

        // Then
        Assertions.assertThat(paymentRepository.findById(1L)).isNotEmpty();
    }

}