# bank
## 아이템 선정
- 시큐리티를 다룬 프로젝트를 경험해봤지만, JWT 인증과 인가 방법을 적용시켜 본 적이 없었다.
- 그래서 그 부분을 중점으로 프로젝트 주제와 참고할 강의를 찾게 되었고, 실제 내가 이용해본 적이 있는 은행 입출금 업무를 바탕으로 회원관리 JWT 인증과 인가 방법을 공부해보고 싶어 선택하게 되었다.

## 개요
- 프로젝트 명칭: bank
- 개발인원: 1명
- 개발기간: 2023-03-03 ~ 
- 주요기능:
  - 비회원
    - [X] 회원가입을 할 수 있다.(이름, 비밀번호, 이메일)
  - 회원
    - [X] 로그인을 할 수 있다. (이름, 비밀번호)
    - [X] 계좌를 생성할 수 있다.(비밀번호 필요)
    - [ ] 생성한 계좌들의 목록을 볼 수 있다.
    - [ ] 계좌 상세 내역을 볼 수 있다.
      - [ ] 계좌 전체 내역을 볼 수 있다.(입 출금 포함)
      - [ ] 계좌 입금 내역을 볼 수 있다. 
      - [ ] 계좌 출금 내역을 볼 수 있다.
    - [ ] 입금 가능하다.
    - [ ] 출금 가능하다.
    - [ ] 이체 가능하다.
- 개발 언어: Java 11
- 개발 환경: SpringBoot 2.7.9, gradle
- 형상관리 툴: GitHub

## 요구사항 분석
- 회원가입 페이지
- 로그인 페이지
- 메인 화면 페이지
- 계좌 등록 페이지
- ATM기계로 입금하기(페이지 아님)
- ATM기계로 출금하기(페이지 아님)
- 이체하기 페이지
- 본인 계좌 상세보기 페이지

### JPA LocalDateTime 자동으로 생성하는 법
- @EnableJpaAuditing (Main 클래스)
- @EntityListeners(AuditingEntityListener.class) (Entity 클래스)
```java
    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;
```
