package kr.hhplus.be.server.infrastructure.stock;

import kr.hhplus.be.server.domain.stock.Stock;
import kr.hhplus.be.server.domain.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class StockRepositoryImpl implements StockRepository {

    private final StockJpaRepository stockJpaRepository;


    @Override
    public Optional<Stock> findById(Long id) {
        return stockJpaRepository.findById(id);
    }

    @Override
    public Optional<Stock> findByProductId(Long productId) {
        return stockJpaRepository.findByProductId(productId);
    }

    @Override
    public List<Stock> findAll() {
        return stockJpaRepository.findAll();
    }

    @Override
    public void save(Stock stock) {
        stockJpaRepository.save(stock);
    }
}
