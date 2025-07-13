# spring-gift-product

## step1 - 조회, 추가, 수정, 삭제 API

- [ ] **상품 조회**
    - 상품 목록을 조회하는 기능
    - HTTP 메서드: GET
    - 엔드포인트: `/api/products`

- [ ] **상품 추가**
    - 새로운 상품을 추가하는 기능
    - HTTP 메서드: POST
    - 엔드포인트: `/api/products`

- [ ] **상품 수정**
    - 기존 상품 정보를 수정하는 기능
    - HTTP 메서드: PUT
    - 엔드포인트: `/api/products/{id}`

- [ ] **상품 삭제**
    - 특정 ID를 가진 상품을 삭제하는 기능
    - HTTP 메서드: DELETE
    - 엔드포인트: `/api/products/{id}`

## step2 - 관리자 화면

- [ ] **상품 목록 화면**
    - 상품 목록을 화면에 표시하는 기능
    - HTML 페이지: `templates/products.html`
    - 접속 방법 : `localhost:8080/products`
    - 상품 목록과 오른쪽 상단의 상품 추가 버튼
    - 각 상품 우측 상품 수정 버튼과 상품 삭제 버튼

- [ ] **상품 추가 화면**
    - 새로운 상품을 추가하는 화면
    - HTML 페이지: `templates/product_form.html`
    - 폼을 통해 상품 정보를 입력받아 추가

- [ ] **상품 수정 화면**
    - 기존 상품 정보를 수정하는 화면
    - HTML 페이지: `templates/product_edit_form.html`
    - 폼을 통해 상품 정보를 입력받아 수정

- [ ] **AJAX를 통한 비동기 처리**
    - 상품 추가, 수정, 삭제 시 페이지 새로고침 없이 비동기로 처리
    - `fetch` API를 사용하여 서버와 통신

## 기능 요구 사항

상품을 조회, 추가, 수정, 삭제할 수 있는 간단한 HTTP API를 구현한다.

- HTTP 요청과 응답은 JSON 형식으로 주고받는다.
- 현재는 별도의 데이터베이스가 없으므로 적절한 자바 컬렉션 프레임워크를 사용하여 메모리에 저장한다.

아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.

### Request

```
GET /api/products HTTP/1.1
```

### Response

```
HTTP/1.1 200
Content-Type: application/json

[
  {
    "id": 8146027,
    "name": "아이스 카페 아메리카노 T",
    "price": 4500,
    "imageUrl": "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"
  }
]
```

## 프로그래밍 요구 사항

- 자바 코드 컨벤션을 지키면서 프로그래밍한다.
    - 기본적으로[Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)를 원칙으로 한다.
    - 단, 들여쓰기는 '2 spaces'가 아닌 '4 spaces'로 한다.
- indent(인덴트, 들여쓰기) depth를 3이 넘지 않도록 구현한다. 2까지만 허용한다.
    - 예를 들어 while문 안에 if문이 있으면 들여쓰기는 2이다.
    - 힌트: indent(인덴트, 들여쓰기) depth를 줄이는 좋은 방법은 함수(또는 메서드)를 분리하면 된다.
- 3항 연산자를 쓰지 않는다.
- 함수(또는 메서드)의 길이가 15라인을 넘어가지 않도록 구현한다.
    - 함수(또는 메서드)가 한 가지 일만 잘 하도록 구현한다.
- else 예약어를 쓰지 않는다.
    - else를 쓰지 말라고 하니 switch/case로 구현하는 경우가 있는데 switch/case도 허용하지 않는다.
    - 힌트: if 조건절에서 값을 return하는 방식으로 구현하면 else를 사용하지 않아도 된다.

> ❗ 상품을 조회, 추가, 수정 ,삭제할 수 있는 간단한 HTTP API 구현

- 요청과 응답은 **JSON 형식**으로 주고받는다.
- 별도의 DB가 없으므로 **자바 컬렉션 프레임워크**를 사용하여 메모리에 저장한다.