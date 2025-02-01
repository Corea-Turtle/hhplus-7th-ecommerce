package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.domain.coupon.infrastructure.CouponRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.coupon.dto.request.CouponCreateRequest;
import kr.hhplus.be.server.interfaces.api.coupon.dto.request.CouponUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private CouponRepositoryImpl couponRepository;

    public void createCoupon(CouponCreateRequest request){
        Coupon coupon = new Coupon(request.getType(), request.getValueOfType(), request.getRemainQuantity(), request.getExpiredDate(), request.getCreateDate());

        //type이 정액일경우 ValueOfType은 1보다 커야한다.
        if(coupon.getType()==CouponType.DISCOUNT && coupon.getValueOfType()<=0){
            throw new IllegalArgumentException("정액할인 값은 1과 같거나 커야합니다.");
        }
        //type이 정률인경우 ValueOfType은 0~100 사이의 값이다.
        if(coupon.getType()==CouponType.RATE && (coupon.getValueOfType()<=0 || coupon.getValueOfType()>100)){
            throw new IllegalArgumentException("정률할인 값을 1~100 값으로 입력해주세요.");
        }

        //expiredDate는 createDate 를 포함한 이후 날짜이어야한다.
        if(coupon.getExpiredDate().isBefore(coupon.getCreateDate())){
            throw new IllegalArgumentException("만료일은 생성일 이후 날짜만 가능합니다. 영구는 만료일 x");
        }
        couponRepository.save(coupon);

    }

    //쿠폰 업데이트
    public void updateCoupon(CouponUpdateRequest request){
        Coupon coupon = new Coupon(request.getId(), request.getUpdateQuantity());

        if(coupon.getRemainQuantity()<0) {
            throw new IllegalArgumentException("선착순 쿠폰 수는 0과 같거나 커야합니다.");
        }
        couponRepository.save(coupon);
    }

    //쿠폰 조회
    public Coupon getCouponById(Long couponId){
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(()->new IllegalArgumentException("해당하는 쿠폰이 없습니다."));
        return coupon;
    }
}
