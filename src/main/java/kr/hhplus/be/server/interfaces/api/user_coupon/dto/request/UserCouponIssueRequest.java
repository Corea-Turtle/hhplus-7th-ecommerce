package kr.hhplus.be.server.interfaces.api.user_coupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserCouponIssueRequest {
     Long userId;
     Long couponId;
}
