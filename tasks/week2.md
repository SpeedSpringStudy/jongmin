# spring-gift-wishlist

## step1 - 유효성 검사 및 예외처리

### 기능 구현
1. 상품 추가, 수정 시 유효성 검사

   상품을 추가하거나 수정하는 경우, 클라이언트로부터 잘못된 값이 전달될 수 있다. 잘못된 값이 전달되면 클라이언트가 어떤 부분이 왜 잘못되었는지 인지할 수 있도록 응답을 제공한다.
    - [x] 상품 이름은 공백 포함 최대 15자까지 입력 가능
    - [x] 가능한 특수 문자: ( ), [ ], +, -, &, /, _
    - [X] "카카오"가 포함된 문구는 담당 MD와 협의한 경우에만 사용 가능

## 힌트

### validation

**`spring-boot-starter-validation`** 의존성을 명시적으로 추가한다.

```java
implementation 'spring-boot-starter-validation'
```

### 스프링 프레임워크 문서

- [Validating Form Input](https://spring.io/guides/gs/validating-form-input)
- [Validation in Spring Boot](https://www.baeldung.com/spring-boot-bean-validation)

## step2 - 인증

사용자가 로그인하고 사용자별 기능을 사용할 수 있도록 구현한다.

아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.

### Request

```
POST /login/token HTTP/1.1
content-type: application/json
host: localhost:8080

{
    "password": "password",
    "email": "admin@email.com"
}

```

### Response

```
HTTP/1.1 200
Content-Type: application/json

{
    "accessToken": ""
}
```

### 로그인 및 회원가입
- [x] email, password 를 통한 회원가입
- [x] 유저 정보를 기반으로 한 로그인

### 토큰 발급
- [x] 로그인이 완료된다면 토큰 제공 (bearer 방식 -> JWT 사용)

## step3 - 위시리스트

### **기능 요구 사항**

이전 단계에서 로그인 후 받은 토큰을 사용하여 사용자별 위시 리스트 기능을 구현한다.

- 위시 리스트에 등록된 상품 목록을 조회할 수 있다.
- 위시 리스트에 상품을 추가할 수 있다.
- 위시 리스트에 담긴 상품을 삭제할 수 있다.

### **실행 결과**

사용자 정보는 요청 헤더의 **Authorization** 필드를 사용한다.

- **`Authorization: <유형> <자격증명>`**

    ```bash
    Authorization: Bearer token
    ```

### **힌트: 사용자 시나리오**

- **위시 리스트 상품 추가**

<img alt="Image" src="https://github.com/user-attachments/assets/616efc65-6989-4175-b499-73ed2069daa4" width="50%" />


- **위시 리스트 상품 삭제**

<img alt="Image" src="https://github.com/user-attachments/assets/77e0e767-089d-44a5-a5c8-95444b893426" width="50%" />


### **HandlerMethodArgumentResolver**

컨트롤러 메서드에 진입하기 전처리를 통해 객체를 주입할 수 있다.

```java
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberService memberService;

    public LoginMemberArgumentResolver(MemberService memberService) {
        this.memberService = memberService;
    }

@Overridepublic boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginMember.class);
    }

@Overridepublic Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    ...
            return new LoginMember(member.getId(), member.getName(), member.getEmail(), member.getRole());
    }

```

```java
@PostMapping("/wishes")
public void create(
    @RequestBody WishRequest request,
    @LoginMember Member member
) {
}
```

## 진행 방식

- 미션은 **과제 진행 요구 사항**, **기능 요구 사항**, **프로그래밍 요구 사항** 세 가지로 구성되어 있다.
- 세 개의 요구 사항을 만족하기 위해 노력한다. 특히 기능을 구현하기 전에 기능 목록을 만들고, 기능 단위로 커밋 하는 방식으로 진행한다.
- **기능 요구 사항에 기재되지 않은 내용은 스스로 판단하여 구현한다.**

## 과제 진행 요구 사항

- **기능을 구현하기 전 `README.md`에 구현할 기능 목록을 정리**해 추가한다.
- Git의 커밋 단위는 앞 단계에서 **`README.md`** 에 정리한 기능 목록 단위로 추가한다.
    - [AngularJS Git Commit Message Conventions](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)을 참고해 커밋 메시지를 작성한다.