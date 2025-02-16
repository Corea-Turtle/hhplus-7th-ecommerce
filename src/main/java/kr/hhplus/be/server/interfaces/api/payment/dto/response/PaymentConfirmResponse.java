package kr.hhplus.be.server.interfaces.api.payment.dto.response;

import kr.hhplus.be.server.domain.payment.PaymentVendor;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PaymentConfirmResponse {
    Long purchaseOrderId; // 결제 요청한 주문
    Long userId; // 요청 유저 아이디
    PurchaseOrderState state; // 성공 실패 등..
    PaymentVendor vendor;// 페이먼트 결제사 (나중 도입?)
    long totalOrderPrice;//총 결제 액
}
