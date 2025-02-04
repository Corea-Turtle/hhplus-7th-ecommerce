package kr.hhplus.be.server.interfaces.api.user.controller;

import kr.hhplus.be.server.application.coupon.MyCouponListFacade;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserMyBalanceRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserMyCouponListRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserMyBalanceResponse;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserMyCouponListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final MyCouponListFacade myCouponListFacade;

    @GetMapping("/get_my_balance")
    public ResponseEntity<UserMyBalanceResponse> getMyBalance(@RequestBody UserMyBalanceRequest request){
        UserMyBalanceResponse response = userService.getCurrentUserBalance(request);
        return ResponseEntity.ok()
                .body(response);
    }


    @GetMapping("get_my_coupons")
    public ResponseEntity<UserMyCouponListResponse> getMyCoupons(@RequestBody UserMyCouponListRequest request){
        UserMyCouponListResponse response = myCouponListFacade.getMyCouponList(request);
        return ResponseEntity.ok()
                .body(response);
    }
}
