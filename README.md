# Concert Reservation App

## 개요

- 객체지향 SOLID 원칙과 자바 컨벤션을 엄격히 지켜가며 코드를 작성한다.
- 동료 개발자와 완전히 같은 내용의 어플리케이션을 제작해보고, 상호 리뷰를 통해 보완할 점을 찾아준다.

## 어플리케이션 내용
### 주제
공연 등록 & 예약 어플리케이션

### 어플리케이션 기능 (application layer)
- 회원가입
- 로그인
- 자신의 사용자 정보 조회
- 공연 정보 조회
- 공연 예약, 취소, 변경
- 공연에  예약 현황 조회

### api list (controller, UI layer)
- POST /api/v1/user/register
    - 회원가입
- POST /api/v1/user/login
    - 로그인
- GET /api/v1/user/my_page
    - 자신의 사용자 정보 조회
- GET /api/v1/concerts
    - 공연 정보 조회
- POST /api/v1/reservation/concerts/{concertId}/seats/{seatNumber}
    - 공연 예약, 취소, 변경
- GET /api/v1/reservation/concerts/{concertId}
    - 공연에 대한 예약 현황 조회


## focus
### 1. 도메인 주도 패키지화
대부분의 백엔드 프레임워크의 경우 프레임워크가 자체적으로 스캐폴딩해주는 기본적인 디렉토리 구조가 있다. 스프링의 경우는 디렉토리 구조를 자동으로 스캐폴딩하지는 않지만, 아래와 같이 주로 사용되는 디렉토리 구조가 존재한다.
```
  com
  └── example
      └── demo
          ├── DemoApplication.java
          ├── config
          ├── controller
          ├── dao
          ├── domain
          ├── exception
          └── service
```
스프링에서 주요하게 다루는 레이어들을 기준으로 디렉토리를 구분한 구조이다. 작은 규모의 어플리케이션에서는 전체적인 구조를 빠르게 파악할 수 있다는 장점이 있지만, 규모가 커짐에 따라 한 디렉토리에 너무 많은 파일들이 모이게 되며 우리가 해결하고자 하는 도메인 영역의 문제를 파악하는데 긴 시간이 걸린다고 생각한다.

DDD에서는 이러한 '인프라스트럭쳐 주도 패키지화'를 지양해야 하며, 패키지 구조에서 도메인 영역을 어느정도 명확하게 표현할 수 있어야 한다고 주장한다. 이번 실습에서는 아래와 같은 도메인 중심의 디렉토리 구조를 구축했다.

```
  com
  └── example
      └── demo
          ├── ContertReservationApplcation.kt
          ├── domain
          |   ├── concert
          |   |   ├── applicaion
          |   |   ├── controller
          |   |   ├── domain
          |   |   ├── exception
          |   |   └── repository
          |   ├── reservation
          |   |   ├── applicaion
          |   |   ├── controller
          |   |   ├── domain
          |   |   ├── dto
          |   |   ├── exception
          |   |   └── repository
          |   └── user
          |       ├── applicaion
          |       ├── controller
          |       ├── domain
          |       ├── dto
          |       ├── exception
          |       └── repository
          └── global
              ├── config
              ├── error
              └── exception
```
위와 같은 디렉토리 구조는 컨텍스트 간 경계를 보다 확실히 나타내며, 개발 과정에서 타 컨텍스트와의 결합도를 느슨하게 만드는 데에도 집중할 수 있다고 생각한다.

### 2. Aggregate의 코드에서의 표현
본 실습에서는 공연에 대한 정보를 담고 있는 Concert 객체와 공연 좌석 정보를 나타내는 Seat 객체가 밀접한 관련을 갖고 있다. Seat 객체는 Concert 객체에 무조건 종속적인 개념이고, Concert가 있음으로써 식별성을 부여받기 때문에 Seat 객체는 Concert를 root로 하는 aggregate의 구성원이 되어야 한다고 생각했다.

처음엔 `@embeddable`와 `@embedded` annotation을 이용해 관계를 정의하고 싶었는데, DB 쿼리가 의도하는 대로 작동하지 않아서 `@OneToMany` annotation을 이용했다.

`Concert.kt`
```kotlin
@Entity
data class Concert(
        @Id @GeneratedValue
        var id: Long? = null,

        var concertName: String? = null,

        var startTime: LocalDateTime? = null,

        var endTime: LocalDateTime? = null,

        @OneToMany(targetEntity = Seat::class)
        var seats: MutableSet<Seat>? = null
)
```
`Seat.kt`
```kotlin
@Entity @IdClass(SeatId::class)
data class Seat (
        @Id
        var concertId: Long? = null,
        @Id
        var seatNumber: Int? = null,
        var isTaken: Boolean
) {
    constructor(): this(isTaken = false)
}

class SeatId(
        var concertId: Long? = null,
        var seatNumber: Int? = null
): Serializable
```

Aggregate를 만들면서 신경썼던 점들은 아래와 같다.
- Root Aggregate는 전역 식별성(concertId)을 가지며, aggregate 원소 entity는 지역 식별성(seatNumber)을 가진다.
- aggregate의 원소 entity의 business logic 및 invarinat는 모두 root aggregate에서 관리해야 한다.
- 외부에서 aggregate로의 접근은 root를 통해서만 가능하다.

한 가지 생각해봐야할 점은 원소 entity인 Seat 객체는 지역 식별성만을 가져도 충분한데, JPA에서 entity로 객체를 정의하기 위해서는 무조건 전역 식별성을 가져야 하기 때문에 이 둘 사이에 간극이 존재한다는 것이다. Aggregate를 JPA로 효과적으로 표현하는 방법에 대해 좀 더 공부를 해봐야겠다.

### 3. Service 네이밍
도메인 주도 설계에서 Entity나 Value Object가 사람 혹은 사물을 표현한다면, Service는 entity와 value object에 포함되기에는 어색한 '행위'를 표현하기 위한 객체이다. 그러한 태생적인 역할에 맞게 Service 객체는 도메인 모델과 1대1 대응이 되도록 작성하지 않고 해당 도메인 내에서 수행해야할 행위를 기반으로 세분화했다.

예를 들어 Concert 컨텍스트에 존재하는 Service를 단순히 ConcertService로 명명하고 세부 메서드를 모아놓지 않고, 아래와 같이 Service 객체를 세분화했다.
```
  com
  └── example
      └── demo
          ├── ContertReservationApplcation.kt
          └── domain
              └── concert
                  ├── applicaion
                  |   ├── GetConcertService.kt
                  |   ├── MakeDummyConcertService.kt
                  |   └── TakeConcertSeatService.kt 
                  ├── controller
                  ├── domain
                  ├── exception
                  └── repository
                       .
                       .
                       .
```
또한 이렇게 행위를 기반으로 Service 객체를 세분화하면 객체의 책임이 명확해지고 불필요한 의존성을 줄일 수 있다는 장점이 있다.

### 4. Service의 역할
DDD에 따르면 Service 객체는 다음 예시와 같이 여러 layer에 존재할 수 있다.

**자금 이체 응용 서비스**
- 입력 내용의 암호화
- 이체 처리를 위한 도메인 서비스로의 메세지 전송
- 이체 확인 대기
- 인프라스트럭처 서비스를 이용한 통지 결정

**자금 이체 도메인 서비스**
- 금액 인출/입금에 필요한 Account(계좌)와 Ledger(원장) 객체 간의 상호작용
- 이체 결과 확인 정보 제공

본 실습에서는 도메인 layer에 위치시킬 만한 Service가 딱히 없기 때문에 application layer에만 Service를 두고 있다.

처음 계획하기로는 Application layer에 위치하는 Service는 애플리케이션 사용자에게 제공하는 직접적인 기능들만을 담고 있도록 하려고 했다. 하지만 개발 과정에서 타 컨텍스트의 기능을 필요로 하는 순간을 마주했다. 예를 들어, Reservation에 관한 service는 당연하게도 User 정보와 Concert 정보를 모두 필요로 하기 때문에 이 컨텍스트에 있는 기능을 주입받아야 했다.

이런 경우 기능을 캡슐화하지 않은 채로 repository 등을 주입받아 기능을 직접 작성하는 것은 타 모듈과의 결합도를 높이고, 중복 코드의 발생 가능성을 높이기 때문에 지양해야 한다고 생각했다. 따라서 이런 경우도 Service 객체를 만들어서 exception 처리 등을 포함해 자체적으로 완전한 기능을 할 수 있는 객체를 주입시켜주기로 했다. 

예시) `GetReservationServiceImpl.kt`
```kotlin
@Service
class GetReservationServiceImpl(
        @Autowired private val reservationRepository: ReservationRepository,
        @Autowired private val userFindService: UserFindService,
        @Autowired private val getConcertService: GetConcertService
): GetReservationService {
    override fun getReservationsByUser(username: String): List<Reservation> {
        val user = userFindService.findByUsername(username)
        return reservationRepository.findByUsername(username)
    }

    override fun getReservationsByConcert(concertId: Long): List<Reservation> {
        val concert = getConcertService.getConcert(concertId)
        return reservationRepository.findByConcertId(concertId)
    }
}
```
단, 이렇게 하게 되면 Service 객체가 사용자에게 직접적으로 노출되는 기능과는 1대1 대응이 되지 않기 때문에, Service 객체를 애플리케이션 내에서만 사용하는 internalService와, 밖으로 노출시킬 externalService로 구분하는 방법 등을 생각해보았다.
(하지만 굳이 구분을 해야 할까?)

### 5. Exception global handling
`@ControllerAdvice` annotation을 통해 애플리케이션 전역에서 특정 exception을 원하는대로 handling할 수 있다. 또한 ErrorResponse 객체를 둠으로써 오류 발생 시의 응답 형식을 일관적으로 유지할 수 있다.

하지만 `@ControllerAdvice`를 남발하면 애플리케이션의 흐름을 통제할 수 없게 되므로 아주 특별한 상황에서만 사용해야 한다는 말도 있다. exception을 효과적으로 handling 하는 방법에 대해서는 좀 더 찾아봐야겠다.

### 6. 모든 응답은 ResponseEntity로 감싸기
Http 통신에서 요청자는 응답의 본문 내용에 앞서 응답의 상태를 신뢰해야 한다. 컨트롤러 단에서 단지 본문 내용만 실어서 응답을 보내준다면 응답 코드는 항상 200이 되는데, 이는 바람직하지 않다. 따라서 각 상황에 맞는 응답 코드를 실을 수 있게 내용을 항상 ResponseEntity로 감싸서 보내도록 하였다.

단, 데이터를 ResponseEntity로 감싸는 것은 애플리케이션 기능을 담당하는 application layer의 역할이 아니므로, controller 단에서 이 작업을 처리하도록 책임을 부여했다.
