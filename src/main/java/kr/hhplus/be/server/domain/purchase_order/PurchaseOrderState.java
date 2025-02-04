package kr.hhplus.be.server.domain.purchase_order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PurchaseOrderState {

    PENDING("주문대기"),
    COMPLETE("주문완료"),
    CANCEL("주문취소");

    private final String text;
}
