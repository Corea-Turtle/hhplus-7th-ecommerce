package kr.hhplus.be.server.application.purchase_order;

import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductService;
import kr.hhplus.be.server.domain.purchase_order.PurchaseOrderService;
import kr.hhplus.be.server.domain.stock.Stock;
import kr.hhplus.be.server.domain.stock.StockService;
import kr.hhplus.be.server.domain.user.User;
import kr.hhplus.be.server.domain.user.UserService;
import kr.hhplus.be.server.domain.user_coupon.UserCoupon;
import kr.hhplus.be.server.domain.user_coupon.UserCouponService;
import kr.hhplus.be.server.interfaces.api.product.dto.ProductDTO;
import kr.hhplus.be.server.interfaces.api.purchase_order.dto.request.PurchaseOrderCreateRequest;
import kr.hhplus.be.server.interfaces.api.purchase_order.dto.response.PurchaseOrderCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderCreateFacade {

    private final UserService userService;

    private final ProductService productService;

    private final StockService stockService;

    private final UserCouponService userCouponService;

    private final PurchaseOrderService purchaseOrderService;


/*    Long userId; //주문자ㅓ 아이디
    List<Product> orderProducts; //주문 상품 리스트(상품id, 이름, 주문 수량 등을 담고 있음);
    List<UserCoupon> usedCoupons;
    long totalPrice;
    String state*/
    public PurchaseOrderCreateResponse createOrder(PurchaseOrderCreateRequest request){
        //유저 아이디가 있는지 확인
        User user = userService.getExistUser(request.getUserId());

        List<Product> products = new ArrayList<>();
        long totalPrice = 0;
        long afterCalPrice = 0;

        //reqeust 상품 목록에 상품 재고 존재하는지 확인 후 상품 추가
        for(Product product : request.getOrderProducts()){
            Product foundProduct = productService.getProductById(product.getId());
            int stockRemain = stockService.getStockRemainQuantity(product.getId());

            Product foundProductWithAmount = new Product(foundProduct.getId(), foundProduct.getName(), foundProduct.getPrice(), stockRemain);

            //상품을 리스트에 추가
            products.add(foundProductWithAmount);

            //물품 총 가격에 재고가 0이 아닌 상품만 추가
            if(stockRemain==0){
                continue;
            }
            totalPrice += (long)foundProduct.getPrice() * stockRemain;
        }
        //쿠폰 사용 여부


        //최종 가격 생성


        //주문 생성
        return new PurchaseOrderCreateResponse(request.getUserId(), );
    }
}
