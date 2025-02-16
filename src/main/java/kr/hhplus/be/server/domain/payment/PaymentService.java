package kr.hhplus.be.server.domain.payment;

import kr.hhplus.be.server.infrastructure.payment.PaymentRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.payment.dto.response.PaymentConfirmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepositoryImpl paymentRepository;

    //결재정보 저장
    public void savePayment(PaymentConfirmResponse response){
        paymentRepository.save(new Payment(response.getPurchaseOrderId(), response.getUserId(), response.getState(),response.getVendor(),response.getTotalOrderPrice()));
    }
}
