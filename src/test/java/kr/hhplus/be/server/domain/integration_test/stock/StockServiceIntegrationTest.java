package kr.hhplus.be.server.domain.integration_test.stock;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.stock.Stock;
import kr.hhplus.be.server.domain.stock.StockService;
import kr.hhplus.be.server.infrastructure.product.ProductRepositoryImpl;
import kr.hhplus.be.server.infrastructure.stock.StockRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class StockServiceIntegrationTest {

    @Autowired
    ProductRepositoryImpl productRepository;

    @Autowired
    StockRepositoryImpl stockRepository;

    @Autowired
    StockService stockService;

    @DisplayName("[실패]상품아이디를 입력했는데 재고수량이 0이면 '0'를 출력한다.")
    @Test
    void getStockByProductIdButQuantityIsZero() {
        Product product = new Product("수량0상품",1000,10);
        productRepository.save(product);
        //given
        Stock stock = new Stock( product.getId(), 0);
        stockRepository.save(stock);
        //when

        //then
        Assertions.assertThat(stockService.getStockRemainQuantity(product.getId())).isEqualTo(0);
    }

    @DisplayName("[실패]상품아이디를 입력했는데 상품이 없으면 '상품이 없습니다'를 출력한다.")
    @Test
    void getStockByProductIdButThereIsNoProduct() {
        //given
        Stock stock = new Stock( 3L, 1);
        stockRepository.save(stock);
        //when
        //then
        Assertions.assertThatThrownBy(() -> stockService.getStockByProductId(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품이 없습니다.");
    }

    @DisplayName("[성공]상품아이디로 재고정보를 얻는다.")
    @Test
    void getStockByProductId() {
        //given
        Stock stock = new Stock(4L, 10);
        stockRepository.save(stock);
        //when
        Stock stockFound = stockService.getStockByProductId(4L);

        //then
        Assertions.assertThat(stockFound.getId()).isEqualTo(stock.getId());
        Assertions.assertThat(stockFound.getProductId()).isEqualTo(4L);
        Assertions.assertThat(stockFound.getRemainQuantity()).isEqualTo(10);
    }
}