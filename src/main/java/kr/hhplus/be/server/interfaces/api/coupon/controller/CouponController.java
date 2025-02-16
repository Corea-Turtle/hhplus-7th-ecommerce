package kr.hhplus.be.server.interfaces.api.coupon.controller;

import kr.hhplus.be.server.application.coupon.LimitedCouponFacade;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.interfaces.api.coupon.dto.request.CouponCreateRequest;
import kr.hhplus.be.server.interfaces.api.coupon.dto.request.CouponLimitedIssueRequest;
import kr.hhplus.be.server.interfaces.api.coupon.dto.request.CouponUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    private final LimitedCouponFacade limitedCouponFacade;

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

    //선착순 쿠폰 발급
    @PostMapping("/issue_limited_coupon")
    public void issuelimitedCoupon(@RequestBody CouponLimitedIssueRequest request) {
        limitedCouponFacade.issuelimitedCoupon(request);
    }



}
