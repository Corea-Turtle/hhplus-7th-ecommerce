package kr.hhplus.be.server.domain.user_coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserCouponState {
    AVAILABLE("사용가능"),
    USED("사용됨");

    private final String text;
}
