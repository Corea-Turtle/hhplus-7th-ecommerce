package kr.hhplus.be.server.domain.product;

import kr.hhplus.be.server.domain.product.infrastructure.ProductRepositoryImpl;
import kr.hhplus.be.server.domain.user.User;
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
class ProductServiceTest {

    @Mock
    ProductRepositoryImpl productRepository;

    @InjectMocks
    ProductService productService;

    @DisplayName("[실패] 프로덕트 아이디를 입력하여 해당 상품이 없습니다.를 반환한다.")
    @Test
    void getProductDetailFail() {
        //given
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty()); // 존재하지 않는 경우 추가

        //when
        //then
        Assertions.assertThatThrownBy(()->productService.getProductById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품이 없습니다.");


    }

    @DisplayName("[성공] 프로덕트 아이디를 입력하여 해당 프로덕트를 반환한다.")
    @Test
    void getProductDetail() {
        //given
        Product product = new Product(1L,"상품1",2000);
        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        //when
        Product productFound = productService.getProductById(product.getId());
        //then
        Assertions.assertThat(productFound.getId()).isEqualTo(1L);
        Assertions.assertThat(productFound.getName()).isEqualTo("상품1");
        Assertions.assertThat(productFound.getPrice()).isEqualTo(2000);

    }
}