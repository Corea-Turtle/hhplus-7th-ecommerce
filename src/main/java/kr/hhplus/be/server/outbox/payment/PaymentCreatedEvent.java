package kr.hhplus.be.server.outbox.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.domain.payment.PaymentVendor;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "payment_created_event")
public class PaymentCreatedEvent {
    @Id
    @GeneratedValue()
    private Long id; //추후 uuid 적용
    private Long paymentId;
    private Long purchaseOrderId; // 결제 요청한 주문
    PurchaseOrderState state; // 성공 실패 등..

    public PaymentCreatedEvent(Long paymentId, Long purchaseOrderId, PurchaseOrderState state) {
        this.paymentId = paymentId;
        this.purchaseOrderId = purchaseOrderId;
        this.state = state;
    }

    public void updateState(PurchaseOrderState state){
        this.state = state;
    }

}
