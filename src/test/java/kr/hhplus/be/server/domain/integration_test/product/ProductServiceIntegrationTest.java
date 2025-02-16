package kr.hhplus.be.server.domain.integration_test.product;

import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.infrastructure.product.ProductRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest
@Testcontainers
class ProductServiceIntegrationTest {

    @Autowired
    ProductRepositoryImpl productRepository;

    @Autowired
    ProductService productService;


    @DisplayName("[실패] 프로덕트 아이디를 입력하여 해당 상품이 없습니다.를 반환한다.")
    @Test
    void getProductDetailFail() {
        //given
        Product product = new Product("상품1",2000);
        productRepository.save(product);// 존재하지 않는 경우 추가

        //when

        //then
        Assertions.assertThatThrownBy(()->productService.getProductById(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품이 없습니다.");


    }

    @DisplayName("[성공] 프로덕트 아이디를 입력하여 해당 프로덕트를 반환한다.")
    @Test
    void getProductDetail() {
        //given
        Product product = new Product("상품1",2000);
        productRepository.save(product);
        //when
        Product productFound = productService.getProductById(product.getId());
        //then
        Assertions.assertThat(productFound.getId()).isEqualTo(product.getId());
        Assertions.assertThat(productFound.getName()).isEqualTo("상품1");
        Assertions.assertThat(productFound.getPrice()).isEqualTo(2000);

    }
}