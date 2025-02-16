package kr.hhplus.be.server.interfaces.api.user_coupon.dto.request;

import kr.hhplus.be.server.domain.user_coupon.UserCouponState;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserCouponIssueRequest {
     Long userId;
     Long couponId;
     UserCouponState state;
     LocalDateTime issuedDate;

     //통테용 실패테스트 생성자: state 설정 USED
     public UserCouponIssueRequest(Long userId, Long couponId, UserCouponState state) {
          this.userId = userId;
          this.couponId = couponId;
          this.state = state;
          this.issuedDate = LocalDateTime.now();
     }

     //통테용 실패테스트 생성자: issuedDate = null
     public UserCouponIssueRequest(Long userId, Long couponId, LocalDateTime issuedDate) {
          this.userId = userId;
          this.couponId = couponId;
          this.state = UserCouponState.AVAILABLE;
          this.issuedDate = issuedDate;
     }

     public UserCouponIssueRequest(Long userId, Long couponId) {
          this.userId = userId;
          this.couponId = couponId;
     }
}
