package kr.hhplus.be.server.domain.stock;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.stock.infrastructure.StockRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepositoryImpl stockRepository;

    public Stock getStockByProductId(Long productId){
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(()->new IllegalArgumentException("상품이 없습니다."));
        if(stock.getRemainQuantity()==0){
            throw new IllegalArgumentException("재고가 없습니다.");
        }
        return stock;
    }
}
