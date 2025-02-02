package kr.hhplus.be.server.interfaces.api.user_coupon.dto.request;

import kr.hhplus.be.server.domain.user_coupon.UserCouponState;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserCouponUpdateRequest {
    Long userId;
    Long couponId;
    UserCouponState state;
    LocalDateTime usedDate;
}
