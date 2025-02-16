package kr.hhplus.be.server.interfaces.api.user.dto.response;


import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserMyCouponListResponse {
    List<UserCoupon> userCoupons;
}
