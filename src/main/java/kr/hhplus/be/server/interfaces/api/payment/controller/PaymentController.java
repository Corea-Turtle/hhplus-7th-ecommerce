package kr.hhplus.be.server.interfaces.api.payment.controller;

import kr.hhplus.be.server.application.payment.PaymentConfirmFacade;
import kr.hhplus.be.server.interfaces.api.payment.dto.request.PaymentConfirmRequest;
import kr.hhplus.be.server.interfaces.api.payment.dto.response.PaymentConfirmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentConfirmFacade paymentConfirmFacade;

    //결제 승인 요청
    @PostMapping("/confirm")
    public ResponseEntity<PaymentConfirmResponse> confirmPayment(@RequestBody PaymentConfirmRequest request){

        PaymentConfirmResponse response = paymentConfirmFacade.confirmPayment(request);

        return ResponseEntity.ok()
                .body(response);
    }

}
