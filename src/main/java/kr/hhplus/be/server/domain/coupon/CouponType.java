package kr.hhplus.be.server.domain.coupon;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CouponType {

    RATE("정률할인"),
    DISCOUNT("정액할인");



    private final String text;
}
