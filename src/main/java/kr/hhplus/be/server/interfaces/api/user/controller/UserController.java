package kr.hhplus.be.server.interfaces.api.user.controller;

import kr.hhplus.be.server.application.coupon.MyCouponListFacade;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserMyBalanceRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserMyCouponListRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserMyBalanceResponse;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserMyCouponListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final MyCouponListFacade myCouponListFacade;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/get_my_balance")
    public ResponseEntity<UserMyBalanceResponse> getMyBalance(@RequestBody UserMyBalanceRequest request){

        log.warn("WARN 로그 테스트 get_my_balance");
        log.info("INFO 로그 테스트 get_my_balance");
        log.debug("DEBUG 로그 테스트 get_my_balance");
        UserMyBalanceResponse response = userService.getCurrentUserBalance(request);
        return ResponseEntity.ok()
                .body(response);
    }


    @PostMapping("get_my_coupons")
    public ResponseEntity<UserMyCouponListResponse> getMyCoupons(@RequestBody UserMyCouponListRequest request){
        UserMyCouponListResponse response = myCouponListFacade.getMyCouponList(request);
        return ResponseEntity.ok()
                .body(response);
    }
}
