package kr.hhplus.be.server.domain.stock;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "stock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private int remainQuantity;

    public void subtractRemainQuantity(int subtractAmount){
        this.remainQuantity -= subtractAmount;
    }

    public Stock(Long productId, int remainQuantity) {
        this.productId = productId;
        this.remainQuantity = remainQuantity;
    }
}
