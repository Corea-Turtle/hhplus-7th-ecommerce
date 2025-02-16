package kr.hhplus.be.server.application.product;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.domain.stock.Stock;
import kr.hhplus.be.server.domain.stock.StockService;
import kr.hhplus.be.server.interfaces.api.product.dto.response.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SingleProductCheckFacade {

    private final ProductService productService;

    private final StockService stockService;

    public ProductDetailResponse getProductDetailByProductId(Long productId){

        //상품 정보 넣기
        Product product = productService.getProductById(productId);

        //재고 정보 넣기
        Stock productStock = stockService.getStockByProductId(productId);

        return new ProductDetailResponse(product.getId(), product.getName(),product.getPrice(), productStock.getRemainQuantity());
    }
}
