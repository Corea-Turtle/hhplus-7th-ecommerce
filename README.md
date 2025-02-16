## 항해 plus 백엔드 과정 - ecommerce project

## Getting Started

e-커머스 상품 주문 서비스를 구현하는 프로젝트입니다. 아래 8개의 API들을 기본적으로 구현한 후 고도화 합니다.
- 잔액 충전 API
- 잔액 조회 API
- 상품 조회 API
- 주문 API 
- 결제 API
- 인기 판매 상품 조회 API
- 선착순 쿠폰 발급 API
- 보유 쿠폰 목록 조회 API


### Prerequisites

- `.github` 폴더 내 pull_request_template.md 파일로 커밋 template 작성  명렁어 : `git config commit.template <파일 주소 절대경로>`

- ERD, flow_chart, sequence_diagram 은 docs 폴더 내에 존재.

- lombok 설치 필요

- swagger-ui 실행 주소 :
  `http://localhost:8080/swagger-ui/index.html`
- swagger-ui config 파일 위치 : config/swagger


#### Running Docker Containers

`local` profile 로 실행하기 위하여 인프라가 설정되어 있는 Docker 컨테이너를 실행해주셔야 합니다.

```bash
docker-compose up -d
```