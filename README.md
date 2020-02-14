# Concert Reservation App

## 개요

- 객체지향 SOLID 원칙과 자바 컨벤션을 엄격히 지켜가며 코드를 작성한다.
- 동료 개발자와 완전히 같은 내용의 어플리케이션을 제작해보고, 상호 리뷰를 통해 보완할 점을 찾아준다.

## 어플리케이션 내용
### 주제
공연 등록 & 예약 어플리케이션

### Entity
#### concert
 - id
 - concertName
 - startTime
 - endTime
 - seats

#### user
 - id
 - name
 - username
 - password
 
#### reservation
 - userId
 - concertId
 - seatNumber

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
