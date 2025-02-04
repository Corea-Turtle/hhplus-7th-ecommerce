package kr.hhplus.be.server.application.coupon;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.domain.user_coupon.UserCouponService;
import kr.hhplus.be.server.interfaces.api.coupon.dto.request.CouponLimitedIssueRequest;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.request.UserCouponIssueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class LimitedCouponFacade {
    //쿠폰 - 선착순 쿠폰 발급

    private final UserService userService;

    private final CouponService couponService;

    private final UserCouponService userCouponService;

    public void issuelimitedCoupon(CouponLimitedIssueRequest request){

        //유저가 존재하는지
        User user = userService.getExistUser(request.getUserId());

        //To do
        //유저가 인가 받은 유저인지(차후 넣기)

        //쿠폰이 존재하는지
        Coupon coupon = couponService.getExistCoupon(request.getCouponId());

        //쿠폰이 있으면 만료된 쿠폰인지 확인
        if(coupon.getExpiredDate().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("만료된 쿠폰입니다.");
        }

        UserCouponIssueRequest userCouponIssueRequest =
                new UserCouponIssueRequest(request.getUserId(), request.getCouponId());

        //여기에 락을 걸어야되나?
        //쿠폰 발급 서비스 호출
        userCouponService.issueCouponToUser(userCouponIssueRequest);
    }
}
