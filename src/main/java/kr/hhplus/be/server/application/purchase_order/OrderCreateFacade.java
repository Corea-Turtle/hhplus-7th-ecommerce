package kr.hhplus.be.server.application.purchase_order;

import kr.hhplus.be.server.domain.coupon.Coupon;
import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.CouponType;
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
import kr.hhplus.be.server.interfaces.api.user_coupon.dto.request.UserCouponUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderCreateFacade {

    private final UserService userService;

    private final ProductService productService;

    private final StockService stockService;

    private final UserCouponService userCouponService;

    private final CouponService couponService;

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
        List<UserCoupon> usedCoupons = new ArrayList<>();
        usedCoupons.addAll(request.getUsedCoupons());

        long totalPrice = 0;
        long afterCalPrice = 0;

        int couponRate = 0;
        int couponDiscount = 0;

        //reqeust 상품 목록에 상품 재고 존재하는지 확인 후 상품 추가
        for(Product product : request.getOrderProducts()){
            Product foundProduct = productService.getProductById(product.getId());
            //여기에 dblock을 걸어야하나
            Stock stock = stockService.getStockByProductId(product.getId());

            int productRequestQuantity = foundProduct.getOrderAmount();

            int stockRemain = stockService.getStockRemainQuantity(product.getId());

            try{
                if(stockRemain >= productRequestQuantity){
                    Product foundProductWithAmount = new Product(foundProduct.getId(), foundProduct.getName(), foundProduct.getPrice(), foundProduct.getOrderAmount());
                    //상품을 리스트에 추가
                    products.add(foundProductWithAmount);
                    //상품 재고 감소
                    stockService.decreaseStockByProductId(foundProductWithAmount.getId(),foundProductWithAmount.getOrderAmount());
                    //총 상품 가격 합에 상품 가격 * 주문 개수 추가
                    totalPrice += (long)foundProduct.getPrice() * stockRemain;
                }else{
                    throw new IllegalArgumentException("요청 상품 {상품 이름}의 재고가 부족하여 주문에 추가하지 못했습니다..");
                }
            }catch(Exception e){
                //e.stackTrace();
            }
        }

        //쿠폰 사용 여부
        if(request.getUsedCoupons().isEmpty()){
            afterCalPrice = totalPrice;
        }else{

            for(UserCoupon usedCoupon: usedCoupons){
                Coupon coupon = couponService.getExistCoupon(usedCoupon.getCouponId());
                //정률 할인
                if(coupon.getType() == CouponType.RATE){
                    couponRate += coupon.getValueOfType();
                }else{ //정액할인
                    couponDiscount += coupon.getValueOfType();
                }

                //사용자가 사용한 보유 쿠폰 상태 바꾸기
                UserCouponUpdateRequest updateRequest = new UserCouponUpdateRequest(usedCoupon.getUserId(), usedCoupon.getCouponId(), usedCoupon.getState(), LocalDateTime.now());

                userCouponService.updateIssuedCouponStateUsed(updateRequest);
            }
        }

        //최종 가격 생성(정률할인 먼저 한 후 정액 할인을 한다.)
        afterCalPrice = (totalPrice * (100-couponRate) / 100) - couponDiscount;

        //주문 생성(주문 완료) 상태로 주문 생성 응답 객체 반환
        return new PurchaseOrderCreateResponse(request.getUserId(), products,usedCoupons,afterCalPrice);
    }

}
