package kr.hhplus.be.server.interfaces.api.user.controller;

import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.interfaces.api.user.dto.request.UserMyBalanceRequest;
import kr.hhplus.be.server.interfaces.api.user.dto.response.UserMyBalanceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get_my_balance")
    public ResponseEntity<UserMyBalanceResponse> getMyBalance(@RequestBody UserMyBalanceRequest request){
        UserMyBalanceResponse response = userService.getCurrentUserBalance(request);
        return ResponseEntity.ok()
                .body(response);
    }


/*    @GetMapping("get_my_coupons")
    public ResponseEntity<UserMyCouponListResponse> getMyCoupons(@RequestBody UserMyCouponListRequest request){
        UserMyCouponListResponse response = userService.getMyCoupons(request);
        return ResponseEntity.ok()
                .body(response);
    }*/
}
