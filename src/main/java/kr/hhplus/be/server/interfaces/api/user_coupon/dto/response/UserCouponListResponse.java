package kr.hhplus.be.server.interfaces.api.user_coupon.dto.response;

import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserCouponListResponse {
    List<UserCoupon> userCoupons;
    String message;
}
