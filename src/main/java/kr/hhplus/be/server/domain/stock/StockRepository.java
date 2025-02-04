package kr.hhplus.be.server.domain.stock;

import kr.hhplus.be.server.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface StockRepository {
    Optional<Stock> findById(Long id);
    Optional<Stock> findByProductId(Long productId);
    List<Stock> findAll();
    void save(Stock stock);
}
