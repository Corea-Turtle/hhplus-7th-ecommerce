package kr.hhplus.be.server.domain.payment.event;

import kr.hhplus.be.server.domain.payment.PaymentVendor;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PaymentEvent {

    private Long paymentId;

    private Long purchaseOrderId;

    private Long userId;

    private PurchaseOrderState state;

    private PaymentVendor vendor;

    private long totalOrderPrice;

}
