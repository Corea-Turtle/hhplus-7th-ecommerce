package kr.hhplus.be.server.domain.user_coupon;

import kr.hhplus.be.server.domain.user_coupon.infrastructure.UserCouponRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.request.UserCouponIssueRequest;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.request.UserCouponListRequest;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.request.UserCouponUpdateRequest;
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.response.UserCouponListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserCouponService {

    private UserCouponRepositoryImpl userCouponRepository;

    //유저-쿠폰 발급
    public void issueCouponToUser(UserCouponIssueRequest request){
        //UserId, CouponId의 존재 유뮤 확인은 Facade에서 하는게 맞는거 같음.
        UserCoupon userCoupon = new UserCoupon(request.getUserId(), request.getCouponId());

        //만들어진 사용자 쿠폰의 상태가 올바른지
        if(userCoupon.getState()!=UserCouponState.AVAILABLE){
            throw new IllegalArgumentException("쿠폰의 상태가 올바르지 않습니다.");
        }
        //만들어진 사용자 쿠폰의 발급일이 올바른지
        if(userCoupon.getIssuedTime()==null){
            throw  new IllegalArgumentException("발급일이 올바르지 않습니다.");
        }

        userCouponRepository.save(userCoupon);
    }

    //유저-쿠폰 사용(주문)시 state는 used로 변경, usedDate는 사용한 일시를 기록한다.
    public void updateIssuedCouponStateUsed(UserCouponUpdateRequest request){
        //requst의 쿠폰이 존재하는지
        UserCoupon userCoupon = userCouponRepository.findByUserIdAndCouponId(request.getUserId(), request.getCouponId())
                .orElseThrow(()->new IllegalArgumentException("발급 받은 쿠폰이 존재하지 않습니다."));

        //request의 쿠폰의 state가 Available한지
        UserCouponState currentState = userCoupon.getState();
        if(currentState!=UserCouponState.AVAILABLE){
            throw new IllegalArgumentException("쿠폰의 상태가 올바르지 않습니다.");
        }

        userCoupon.updateStateToUsed();

        userCouponRepository.save(userCoupon);
    }

    //유저-쿠폰 사용(주문 취소)시 state는 Available로 변경, usedDate를 삭제한다.
    public void updateIssuedCouponStateAvailable(UserCouponUpdateRequest request){
        //requst의 쿠폰이 존재하는지
        UserCoupon userCoupon = userCouponRepository.findByUserIdAndCouponId(request.getUserId(), request.getCouponId())
                .orElseThrow(()->new IllegalArgumentException("발급 받은 쿠폰이 존재하지 않습니다."));

        //request의 쿠폰의 state가 Used인지
        UserCouponState currentState = userCoupon.getState();
        if(currentState!=UserCouponState.USED){
            throw new IllegalArgumentException("쿠폰의 상태가 올바르지 않습니다.");
        }

        userCoupon.updateStateToAvailable();

        userCouponRepository.save(userCoupon);
    }

    //유저-보유쿠폰 조회
    public UserCouponListResponse getUserCouponsByUserId(UserCouponListRequest request){
        List<UserCoupon> userCoupons = userCouponRepository.findAllByUserId(request.getUserId());
        String message = "";
        if(userCoupons.isEmpty()){
            message = "보유한 쿠폰이 없습니다.";
        }
        return new UserCouponListResponse(userCoupons, message);
    }
}
