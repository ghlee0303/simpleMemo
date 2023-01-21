# Simple Memo

## 1. 프로젝트 개요

- 프로젝트 명칭 : Simple Memo
- 개발 기간 : 2022.11.10 ~ 2023.
- 주요 기능 :
    - 메모 작성 - CRUD 기능, 저장 후 이전에 작성한 메모는 이전 메모로 덧붙임
    - 이전 메모 - 이전에 작성한 메모 조회
    - 사용자 - Security 회원가입 및 로그인, 리멤버미, 유효성 검사 및 중복 검사
- 개발 언어 : Java 11
- 개발 환경 : SpringBoot, Spring Security, jpa(Spring Data JPA), QueryDsl
- 데이터베이스 : MySQL
- 형상관리 툴 : GitHub
- 소개 : 간단한 메모를 작성할 수 있는 웹 페이지이다. index 페이지로 접속 시 가장 최근에 작성한 메모를 바로 이어서 작성 할 수 있으며, 저장 후에는 이전에 작성했던 내용을 조회 할 수있다. 

## 2. 요구사항

### 1. 회원 가입

-   #### 유효성 검사
    - 이메일 형식 패턴 적용해 확인
    - 한 개의 칸이라도 공백 혹은 빈칸이 있는지 확인하고 있다면, "OOO는 필수 입력 값입니다."의 메시지 보여주기
    - 비밀번호는 최소 8~16자 이상이며, 영문 대 소문자, 숫자, 특수문자를 사용하게 하기

-   #### 중복확인
    - 데이터베이스에 존재하는 이메일을 입력한 채 회원가입 버튼을 누른 경우 "이미 사용중인 이메일입니다." 의 메시지를 보여주기
    - 모든 검사가 통과되었다면 로그인 페이지로 이동시키기

### 2. 로그인

-   #### 로그인을 하지 않은 경우 아래 페이지만 이용가능

    - 회원가입 페이지
    - 로그인 페이지
    - 그 외 로그인을 하지 않거나 올바르지 않은 경로로 접속한 사용자가 로그인이 필요한 경로에 접속한 경우 로그인 페이지로 이동
    - 로그인시 자동으로 리멤버 미 적용

-   #### 로그인 검사 
    - 아이디와 비밀번호가 일치하지 않을 시 "아이디 또는 비밀번호가 맞지 않습니다. "의 메시지를 출력
    - 이외의 다른 에러 메시지 또한 처리
    - 모든 검사가 통과되었다면 로그인 후 index 페이지로 이동

### 3. 회원정보 수정

- 회원정보 수정은 이메일, 비밀번호
- 회원가입 유효성 검사와 동일
- 수정 완료 시 수정 날짜 업데이트

### 4. 메모 리스트 조회

- 본인이 작성한 메모만 조회
- 10개씩 끊어서 조회
- 리스트 형식
- 각 항목별로 검색

|#|제목|작성일|열람일|수정일|
|-|----|-----|-----|-----|
|1|제목|2023-01-15|05:03|02:23|

### 5. 메모 작성

- index 페이지로 접속 시 가장 최근에 작성한 메모 페이지로 이동
- 메모 작성 시 제목과 내용은 공백 혹은 빈칸으로 작성하지 않도록 하기
- 입력 후 2.5초 동안 추가입력이 없을 시 자동 저장
- 새 메모 작성 시 생성 후 수정
-- 메모 입력 즉시 자동 저장 될 수 있게끔
- 이미지 업로드
-- 이미지 업로드 경로 "\image\data\" + 업로드날짜 + 랜덤 문자열
- HTML 에디터

### 6. 사이드 바
- 메모목록, 새 메모 작성, 열람한 메모, 이전 메모, 로그아웃 기능
- 열람한 메모는 최근에 열람한 10개의 메모를 조회
- 이전 메모는 이전 메모를 조회함

## 3. DB 설계

  깃헙에서 이미지 넣으시오

## 4 API 설계

### 메모 관련 API
|기능|Method|URL|Return|
|---|------|----|-----|
|메모 조회|GET|memo/{id}|해당 메모 페이지
|메모 데이터 요청|GET|memo?id={번호}|해당 메모 JSON
|메모 수정 요청|PUT|memo?id={번호}|해당 메모 수정
|메모 삭제 요청|DELETE|memo?id={번호}|해당 메모 삭제
|새 메모 작성|GET|memo/new|새 메모 생성 후 해당 메모 페이지로 이동
|가장 최근에 작성한 메모 요청|GET|memo/latest|해당 메모 JSON
|최근에 조회한 10개의 메모 리스트 요청|GET|memo/viewedList|해당 메모 JSON 리스트
|메모 페이징 목록|GET|memo/page?page={번호}|메모 페이징 목록 페이지
|메모 페이징 리스트|GET|memo/list?page={번호}|메모 JSON 리스트
|이전 메모 데이터 요청|GET|memoTemp/{번호}|해당 이전 메모 JSON

### 로그인 관련 API
|기능|Method|URL|Return|
|---|------|----|-----|
|회원가입|GET|join|회원가입 페이지
|회원가입|POST|join|회원가입 후 로그인 페이지
|로그인|GET|login|로그인 페이지
|로그인|POST|login|로그인 처리 후 메모 페이지
|로그아웃|GET|logout|로그아웃 후 로그인 페이지

### 이미지 업로드 관련 API
|기능|Method|URL|Return|
|---|------|----|-----|
|이미지 업로드|POST|image/upload|이미지 URL
|이미지 요청|GET|image/{타입}/{이미지명}|이미지 데이터
