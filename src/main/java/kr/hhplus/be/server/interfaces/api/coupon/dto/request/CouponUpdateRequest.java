package kr.hhplus.be.server.interfaces.api.coupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponUpdateRequest {
    private Long id;
    private int updateQuantity;
}
