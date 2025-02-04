package kr.hhplus.be.server.interfaces.api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private int price;
    private int orderAmount;
}
