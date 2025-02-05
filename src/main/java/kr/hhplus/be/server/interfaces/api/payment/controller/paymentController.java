package kr.hhplus.be.server.interfaces.api.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class paymentController {

    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<PaymentConfirmResponse> confirmPayment(@RequestBody PaymentConfirmRequest request){

        PaymentConfirmResponse response = paymentService.confirmPayment(request);

        return ResponseEntity.ok()
                .body(response);
    }

}
