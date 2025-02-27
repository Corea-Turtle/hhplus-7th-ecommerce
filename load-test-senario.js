import http from 'k6/http';
import { check, sleep, group } from 'k6';

export let options = {
    vus: 10,  // 가상 사용자 수
    duration: '30s', // 테스트 실행 시간
};


export default function () {

group('product_detail', () => {
    // URL에 productId 값을 올바르게 포함시킵니다.
    let productId = 1;

    let res = http.get(`http://localhost:8080/product/detail/${productId}`);
    check(res, { '상품 상세 조회 상태 코드 200': (r) => r.status === 200 });
    sleep(1);
});

group('create_order', () => {
    // ProductDTO 객체 예시 (실제 DTO 구조에 맞게 수정)
    let orderProducts = [{
      productId: 1,          // 상품 ID
      name: "Test Product",  // 상품 이름
      quantity: 2            // 주문 수량
    }];

    // 사용한 쿠폰이 없으면 빈 배열, 실제 쿠폰 정보가 있다면 해당 객체 배열 전달
    let usedCoupons = [];

    let orderPayload = JSON.stringify({
      userId: 1,
      orderProducts: orderProducts,
      usedCoupons: usedCoupons
    });

    let orderParams = { headers: { 'Content-Type': 'application/json' } };
    let res = http.post('http://localhost:8080/purchase_order/create_order', orderPayload, orderParams);
    check(res, { '주문 생성 상태 코드 200': (r) => r.status === 200 });

    // 응답 JSON에서 purchaseOrderId 추출 (응답 필드명이 PurchaseOrderCreateResponse와 일치한다고 가정)
    let json = res.json();
    console.log(json);
    purchaseOrderId = json.purchaseOrderId;
    sleep(3); // 3초 대기
  });

  // 3. 결제 승인 요청
  group('confirm_payment', () => {
    let paymentPayload = JSON.stringify({
      userId: userId,
      purchaseOrderId: purchaseOrderId,
      vendor: "TEST" // PaymentVendor enum의 값에 맞게 설정 (예시)
    });
    let paymentParams = { headers: { 'Content-Type': 'application/json' } };
    let res = http.post('http://localhost:8080/payment/confirm', paymentPayload, paymentParams);
    check(res, { '결제 승인 상태 코드 200': (r) => r.status === 200 });
    sleep(3); // 3초 대기
  });
}