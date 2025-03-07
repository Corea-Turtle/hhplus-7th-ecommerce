package kr.hhplus.be.server.domain.purchase_order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PurchaseOrderState {

    ORDER_PENDING("주문생성"),
    ORDER_CANCEL("주문취소"),
    PAYMENT_INIT("결제생성"),
    PAYMENT_COMPLETE("결제완료");

    private final String text;
}
