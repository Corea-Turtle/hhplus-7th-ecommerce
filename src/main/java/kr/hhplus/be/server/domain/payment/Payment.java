package kr.hhplus.be.server.domain.payment;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderState;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@Entity
@Getter
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long purchaseOrderId;

    private Long userId;

    private PurchaseOrderState state;

    private PaymentVendor vendor;

    private long totalOrderPrice;

    private LocalDateTime paymentCreateDateTime;

    private LocalDateTime paymentUpdateDateTime;
}
