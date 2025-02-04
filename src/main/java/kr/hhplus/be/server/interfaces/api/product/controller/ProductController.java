package kr.hhplus.be.server.interfaces.api.product.controller;

import kr.hhplus.be.server.application.product.SingleProductCheckFacade;
import kr.hhplus.be.server.interfaces.api.product.dto.response.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final SingleProductCheckFacade singleProductCheckFacade;
/*    //To do
    //검색은 따로 만들지 여기에 같이 만들지 고민
    @GetMapping("/list/{page}/{limit}")
    public ResponseEntity<ProductListResponse> getProductList(@RequestParam String page, @RequestParam String limit){
        PrductListResponse response = productService.getProductList(page, limit);
        return ResponseEntity.ok()
                .body(response);
    }*/


    @GetMapping("/detail/{productId}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(@RequestParam Long productId){
        ProductDetailResponse response = singleProductCheckFacade.getProductDetailByProductId(productId);//파사드에서 처리
        return ResponseEntity.ok()
                .body(response);
    }
}
