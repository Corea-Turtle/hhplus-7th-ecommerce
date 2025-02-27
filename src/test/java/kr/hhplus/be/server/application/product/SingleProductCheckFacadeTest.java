package kr.hhplus.be.server.application.product;

import jakarta.transaction.Transactional;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.domain.stock.Stock;
import kr.hhplus.be.server.domain.stock.StockRepository;
import kr.hhplus.be.server.domain.stock.StockService;
import kr.hhplus.be.server.infrastructure.product.ProductRepositoryImpl;
import kr.hhplus.be.server.interfaces.api.product.dto.response.ProductDetailResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Testcontainers
class SingleProductCheckFacadeTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepositoryImpl productRepository;

    @Autowired
    StockService stockService;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    SingleProductCheckFacade singleProductCheckFacade;

    @DisplayName("[실패]해당 아이디의 상품이 없다.")
    @Test
    void getProductDetailByProductIdFailNoProduct() {
        //given

        //when

        //then
        Assertions.assertThatThrownBy(()->productService.getProductById(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품이 없습니다.");
    }

    @DisplayName("[실패]상품은 존재하지만 재고가 0이다.")
    @Test
    void getProductDetailByProductIdFailNoStock() {
        //given
        Product product = new Product("재고 없는 상품",1000);
        productRepository.save(product);
        Stock stock = new Stock(product.getId(), 0);
        stockRepository.save(stock);

        //when

        //then
        Assertions.assertThat(stockService.getStockRemainQuantity(product.getId())).isEqualTo(0);
    }

    @DisplayName("[성공]상품아이디를 입력해서 상품 상세 정보를 반환한다.")
    @Test
    void getProductDetailByProductId() {
        //given
        Product product = new Product("재고 있는 상품",1000);
        productRepository.save(product);
        Stock stock = new Stock(product.getId(), 100);
        stockRepository.save(stock);
        //when

        ProductDetailResponse response =  singleProductCheckFacade.getProductDetailByProductId(product.getId());
        //then
        Assertions.assertThat(product.getId()).isEqualTo(response.getProductId());
        Assertions.assertThat(stock.getProductId()).isEqualTo(response.getProductId());
        Assertions.assertThat(stock.getRemainQuantity()).isEqualTo(response.getRemainQuantity());

    }
}