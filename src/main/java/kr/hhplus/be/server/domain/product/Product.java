package kr.hhplus.be.server.domain.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    @Transient
    private int orderAmount;

    public Product(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(Long id, String name, int price, int orderAmount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.orderAmount = orderAmount;
    }

    public Product(String name, int price, int orderAmount) {
        this.name = name;
        this.price = price;
        this.orderAmount = orderAmount;
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
