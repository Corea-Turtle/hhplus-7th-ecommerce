package kr.hhplus.be.server.outbox.payment;

import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PaymentCreatedMessage {
    private Long paymentEventId;
    private Long paymentId;
    private Long purchaseOrderId; // 결제 요청한 주문
    PurchaseOrderState state; // 성공 실패 등..
}
