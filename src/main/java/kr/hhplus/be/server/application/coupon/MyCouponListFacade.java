package kr.hhplus.be.server.application.coupon;

import kr.hhplus.be.server.domain.user_coupon.UserCouponService;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserMyCouponListRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserMyCouponListResponse;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.request.UserCouponListRequest;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.response.UserCouponListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MyCouponListFacade {

    private final UserCouponService userCouponService;

    public UserMyCouponListResponse getMyCouponList(UserMyCouponListRequest request){

        //UserCoupon 리스트 요청 객체로 User의 Coupon 리스트 요청 객체를 만든다.
        UserCouponListRequest userRequest = new UserCouponListRequest(request.getUserId());

        //보유 쿠폰을 불러와서 UserCoupon의 리스트를 응답 객체 안에 넣어준다.
        UserCouponListResponse userResponse = userCouponService.getUserCouponsByUserId(userRequest);

        //반환한다.
        return new UserMyCouponListResponse(userResponse.getUserCoupons());
    }
}
