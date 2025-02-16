package kr.hhplus.be.server.domain.stock;

import kr.hhplus.be.server.infrastructure.stock.StockRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepositoryImpl stockRepository;

    public Stock getStockByProductId(Long productId){
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(()->new IllegalArgumentException("상품이 없습니다."));
        return stock;
    }

    public int getStockRemainQuantity(Long productId){
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(()->new IllegalArgumentException("상품이 없습니다."));
        return stock.getRemainQuantity();
    }


    public Stock decreaseStockByProductId(Long productId, int decreaseAmount){
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(()->new IllegalArgumentException("상품이 없습니다."));
        stock.subtractRemainQuantity(decreaseAmount);

        return stock;
    }

}
