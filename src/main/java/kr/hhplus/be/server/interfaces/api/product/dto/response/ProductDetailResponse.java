package kr.hhplus.be.server.interfaces.api.product.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ProductDetailResponse {
    private Long productId;
    private String productName;
    private int productPrice;
    private int remainQuantity;
}
