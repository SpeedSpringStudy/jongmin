# spring-gift-wishlist

## step1 - 유효성 검사 및 예외처리

### 기능 구현
1. 상품 추가, 수정 시 유효성 검사

   상품을 추가하거나 수정하는 경우, 클라이언트로부터 잘못된 값이 전달될 수 있다. 잘못된 값이 전달되면 클라이언트가 어떤 부분이 왜 잘못되었는지 인지할 수 있도록 응답을 제공한다.
    - [ ] 상품 이름은 공백 포함 최대 15자까지 입력 가능
    - [ ] 가능한 특수 문자: ( ), [ ], +, -, &, /, _
    - [ ] "카카오"가 포함된 문구는 담당 MD와 협의한 경우에만 사용 가능

## 진행 방식

- 미션은 **과제 진행 요구 사항**, **기능 요구 사항**, **프로그래밍 요구 사항** 세 가지로 구성되어 있다.
- 세 개의 요구 사항을 만족하기 위해 노력한다. 특히 기능을 구현하기 전에 기능 목록을 만들고, 기능 단위로 커밋 하는 방식으로 진행한다.
- **기능 요구 사항에 기재되지 않은 내용은 스스로 판단하여 구현한다.**

## 과제 진행 요구 사항

- **기능을 구현하기 전 `README.md`에 구현할 기능 목록을 정리**해 추가한다.
- Git의 커밋 단위는 앞 단계에서 **`README.md`**에 정리한 기능 목록 단위로 추가한다.
    - [AngularJS Git Commit Message Conventions](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)을 참고해 커밋 메시지를 작성한다.

## 힌트

### validation

**`spring-boot-starter-validation`** 의존성을 명시적으로 추가한다.

```java
implementation 'spring-boot-starter-validation'
```

### 스프링 프레임워크 문서

- [Validating Form Input](https://spring.io/guides/gs/validating-form-input)
- [Validation in Spring Boot](https://www.baeldung.com/spring-boot-bean-validation)