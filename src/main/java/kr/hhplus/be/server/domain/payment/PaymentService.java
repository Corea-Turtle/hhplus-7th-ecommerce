package kr.hhplus.be.server.domain.payment;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.payment.event.PaymentEvent;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrder;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderService;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.infrastructure.payment.PaymentRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.payment.dto.response.PaymentConfirmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepositoryImpl paymentRepository;

    private final UserService userService;

    private final PurchaseOrderService purchaseOrderService;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void pay(User user, PurchaseOrder purchaseOrder, PaymentConfirmResponse response){
        userService.updateUserBalanceGreaterThanOrEqualToTotalOrderPrice(user,purchaseOrder); // 유저 포인트 확인 후 차감
        purchaseOrderService.purchaseOrderUpdateRequest(purchaseOrder, PurchaseOrderState.PAYMENT_COMPLETE); // 주문 상태 변경
        Long paymentId = savePayment(response); // 결제 정보 저장
        eventPublisher.publishEvent(new PaymentEvent(paymentId,response.getPurchaseOrderId(), response.getUserId(), response.getState(),response.getVendor(),response.getTotalOrderPrice()));
    }


    //결제 정보 저장
    public Long savePayment(PaymentConfirmResponse response){
        Payment payment = new Payment(response.getPurchaseOrderId(), response.getUserId(), response.getState(),response.getVendor(),response.getTotalOrderPrice());
        paymentRepository.save(payment);
        return payment.getId();
    }


}
