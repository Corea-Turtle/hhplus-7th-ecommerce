package kr.hhplus.be.server.interfaces.api.user_coupon.dto.request;

import kr.hhplus.be.server.domain.user_coupon.UserCouponState;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class UserCouponUpdateRequest {
    Long userId;
    Long couponId;
    UserCouponState state;
    LocalDateTime usedDate;

    public UserCouponUpdateRequest(Long userId, Long couponId, UserCouponState state, LocalDateTime usedDate) {
        this.userId = userId;
        this.couponId = couponId;
        this.state = state;
        this.usedDate = usedDate;
    }
}
