package kr.hhplus.be.server.interfaces.api.payment.dto.request;

import kr.hhplus.be.server.domain.payment.PaymentVendor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentConfirmRequest {

    Long userId;
    Long purchaseOrderId;
    PaymentVendor vendor;
}
