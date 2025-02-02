package kr.hhplus.be.server.interfaces.api.coupon.controller;

import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.interfaces.api.coupon.dto.request.CouponCreateRequest;
import kr.hhplus.be.server.interfaces.api.coupon.dto.request.CouponLimitedIssueRequest;
import kr.hhplus.be.server.interfaces.api.coupon.dto.request.CouponUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    //쿠폰 생성
    @PostMapping("/new")
    public void createCoupon(@RequestBody CouponCreateRequest request){
        couponService.createCoupon(request);
    }

    //쿠폰 업데이트
    @PutMapping("/update")
    public void updateCoupon(@RequestBody CouponUpdateRequest request) {
        couponService.updateCoupon(request);
    }

/*    @PostMapping("/issue")
    public void issuelimitedCoupon(@RequestBody CouponLimitedIssueRequest request){
        limitedCouponIssueUseCase.issuelimitedCoupon(request);
    }*/

}
