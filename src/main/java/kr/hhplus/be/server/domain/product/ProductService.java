package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.domain.product.infrastructure.ProductRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    ProductRepositoryImpl productRepository;

    //1. 상품 정보 ( ID, 이름, 가격, 잔여수량 ) 을 조회.
    //2. 조회시점의 상품별 잔여수량이 정확하면 좋습니다.

    //잔여수량은 파사드에서 Stock에서 남은 수량 조회
    public Product getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 상품이 없습니다."));
        return product;
    }

}
