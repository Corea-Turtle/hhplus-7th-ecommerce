package kr.hhplus.be.server.domain.stock;

import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.domain.product.infrastructure.ProductRepositoryImpl;
import kr.hhplus.be.server.domain.stock.infrastructure.StockRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {


    @Mock
    StockRepositoryImpl stockRepository;

    @InjectMocks
    StockService stockService;

    @DisplayName("[실패]상품아이디를 입력했는데 재고수량이 0이면 '재고가 없습니다'를 출력한다.")
    @Test
    void getStockByProductIdButQuantityIsZero() {
        //given
        Stock stock = new Stock(1L,2L,0);
        Mockito.when(stockRepository.findByProductId(2L)).thenReturn(Optional.of(stock)); // 존재하지 않는 경우 추가
        //when
        //then
        Assertions.assertThatThrownBy(()->stockService.getStockByProductId(2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 없습니다.");
    }

    @DisplayName("[실패]상품아이디를 입력했는데 상품이 없으면 '상품이 없습니다'를 출력한다.")
    @Test
    void getStockByProductIdButThereIsNoProduct() {
        //given
        Mockito.when(stockRepository.findByProductId(1L)).thenReturn(Optional.empty()); // 존재하지 않는 경우 추가
        //when
        //then
        Assertions.assertThatThrownBy(()->stockService.getStockByProductId(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품이 없습니다.");
    }

    @DisplayName("[성공]상품아이디로 재고정보를 얻는다.")
    @Test
    void getStockByProductId() {
        //given
        Stock stock = new Stock(1L,2L,10);
        //when
        Mockito.when(stockRepository.findByProductId(2L)).thenReturn(Optional.of(stock));
        Stock stockFound = stockService.getStockByProductId(2L);

        //then
        Assertions.assertThat(stockFound.getId()).isEqualTo(1L);
        Assertions.assertThat(stockFound.getProductId()).isEqualTo(2L);
        Assertions.assertThat(stockFound.getRemainQuantity()).isEqualTo(10);
    }
}