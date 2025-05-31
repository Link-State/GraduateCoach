# 졸업코치

## 프로젝트 스펙
---

- **프로젝트명** : 졸업코치 (Graduate Coach)
- **목표** : 학생 및 학사팀을 위한 졸업요건 관리 및 이수 현황 대시보드 제공
- **프로젝트 기간** : 2025.04.01(화) ~ 2025.05.31(토)
- **주요 기능**
  - 학생/학사팀 로그인 및 회원가입
  - 학생 대시보드 : 이수 학점, 졸업 인증, 과목별 이수 현황 시각화
  - 학사팀 대시보드 : 학부/전공별 졸업요건 관리, 과목/인증 추가 및 삭제
- **기술 스택**
  - Backend : Spring Boot, Java
  - Frontend : HTML, CSS, JS, Thymeleaf, Bootstrap
  - Database : MySQL
  - 기타 : Gradle, Docker (개발환경)
- **주요 요구사항**
  - 사용자 인증 및 권한 분리(학생/학사팀)
  - 실시간 대시보드 데이터 반영
  - 반응형 UI 지원
  - RESTful API 및 서버사이드 렌더링 혼합

## API
---

### 📌웰컴 스크린 진입

> 📄 웰컴스크린 (페이지)

|요청메소드|GET|
|----|----|
|URL|/welcome|
|HTML|welcome.html|

<br>

---

<br>

### 📌로그인

> 📄 로그인 (페이지)

|요청메소드|GET|
|----|----|
|URL|/login|
|HTML|login.html|

<br>
<br>

> 📡 로그인 요청 (API)

|요청메소드|POST|
|----|----|
|URL|/login|

<br>

|요청|||
|----|----|----|
|필드명|타입|설명|
|user_id|String|학번 또는 학사팀 아이디|
|user_pwd|String|비밀번호|

<br>

|응답||
|----|----|
|학생 로그인 성공 시|학생 대시보드 페이지로 이동|
|학사팀 로그인 성공 시|학사팀 대시보드 페이지로 이동|
|실패 시|로그인 페이지로 리다이렉트|

<br>

|(1) 응답 성공 시 - 학생 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|* 학생 대시보드 필드 참조|||

<br>

|(2) 응답 성공 시 - 학사팀 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|* 학사팀 대시보드 필드 참조|||

<br>

|(3) 응답 실패 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|msg|String|실패 원인|

<br>

------

<br>

### 📌회원가입

> 📄 회원가입 (페이지)

|요청메소드|GET|
|----|----|
|URL|/signup|
|HTML|signup.html|

<br>

|응답||
|----|:----:|
|성공 시|회원가입 페이지로 이동|
|실패 시|-|

<br>

|(1) 응답 성공 시 - 회원가입 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|university_list|List&lt;UniversityEntity&gt;|대학 리스트|
|major_list|List&lt;MajorEntity&gt;|(연세대학교 미래캠퍼스) 학과 리스트|

<br>

|(2) 응답 실패 시 - 회원가입 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|-|-|-|

<br>
<br>

> 📡 회원가입 요청 (API)

|요청메소드|POST|
|----|----|
|URL|/signup|

<br>

|요청|||
|----|----|----|
|필드명|타입|설명|
|name|String|이름|
|email|String|이메일|
|univ|Integer|소속대학 고유번호|
|major|Integer|전공 고유번호|
|id|String|학번|
|pwd|String|비밀번호|
|pwd_check|String|비밀번호 확인|

<br>

|응답||
|----|----|
|성공 시|로그인 페이지로 이동|
|실패 시|회원가입 페이지로 리다이렉트|

<br>

|(1) 응답 성공 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|success_signup_msg|String|회원가입 성공 알림|

<br>

|(2) 응답 실패 시 - 회원가입 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|university_list|List&lt;UniversityEntity&gt;|대학 리스트|
|major_list|List&lt;MajorEntity&gt;|(연세대학교 미래캠퍼스) 학과 리스트|
|name|String|이름|
|email|String|이메일|
|univ|Integer|소속대학 고유번호|
|major|Integer|전공 고유번호|
|id|String|학번|
|pwd|String|비밀번호|
|msg|String|실패 원인|

<br>

------

<br>

### 📌로그아웃

> 📡 로그아웃 요청 (API)

|요청메소드|POST|
|----|----|
|URL|/logout|

<br>

|응답||
|----|:----:|
|성공 시|웰컴페이지로 이동|
|실패 시|-|

<br>

------

<br>

### 📌학생 대시보드

> 📄 학생 대시보드 (페이지)

|요청메소드|GET|
|----|----|
|URL|/student-dashboard|
|HTML|student_dashboard.html|

<br>

|응답||
|----|:----:|
|(로그인 세션 존재) 성공 시|학생 대시보드 페이지로 이동|
|(로그인 세션 미존재) 실패 시| 로그인 페이지로 이동|

<br>

|(1) 응답 성공 시 - 학생 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|univ|String|학교명|
|major|String|전공명|
|id|String|학번|
|foreign_cert|ForeignCertEntity|외국어 인증 여부 (null일 경우 미인증 상태를 의미)|
|comm_cert|CommunicationCertEntity|정보 인증 여부 (null일 경우 미인증 상태를 의미)|
|total_credit|Float|총 이수학점|
|total_req_credit|Float|필요 이수학점|
|jeontam_credit|Float|전공탐색 이수학점|
|jeontam_req_credit|Float|전공탐색 필요학점|
|pilgyo_credit|Float|필수교양 이수학점|
|pilgyo_req_credit|Float|필수교양 필요학점|
|daegyo_credit|Float|대학교양(필수) 이수학점|
|daegyo_req_credit|Float|대학교양(필수) 필요학점|
|seongyo_credit|Float|대학교양(선택) 이수학점|
|seongyo_req_credit|Float|대학교양(선택) 필요학점|
|advanced_credit|Float|3000단위 이상 과목 이수학점|
|advanced_req_credit|Float|3000단위 이상 과목 필요학점|
|jeonpil_credit|Float|전공필수 이수학점|
|jeonpil_req_credit|Float|전공필수 필요학점|
|jeonseon_credit|Float|전공선택 이수학점|
|jeonseon_req_credit|Float|전공선택 필요학점|
|pilgyo_list|List&lt;CourseEntity&gt;|이수한 필수교양(교양기초) 과목 목록|
|daegyo_list|List&lt;CourseEntity&gt;|이수한 대학교양(필수) 과목 목록|
|seongyo_list|List&lt;CourseEntity&gt;|이수한 대학교양(선택) 과목 목록|
|jeontam_list|List&lt;CourseEntity&gt;|이수한 전공탐색 과목 목록|
|advanced_list|List&lt;CourseEntity&gt;|이수한 3000단위이상 과목 목록|
|jeonpil_list|List&lt;CourseEntity&gt;|이수한 전공필수 과목 목록|
|jeonseon_list|List&lt;CourseEntity&gt;|이수한 전공선택 과목 목록|
|recom_pilgyo_list|List&lt;CourseEntity&gt;|이수 필요한 필수교양(교양기초) 과목 목록|
|recom_daegyo_list|List&lt;CourseEntity&gt;|이수 필요한 대학교양(필수) 과목 목록|
|recom_jeontam_list|List&lt;CourseEntity&gt;|이수 필요한 전공탐색 과목 목록|
|recom_jeonpil_list|List&lt;CourseEntity&gt;|이수 필요한 전공필수 과목 목록|

<br>

|(2) 응답 실패 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|msg|String|실패 원인|

<br>

------

<br>

### 📌학사팀 대시보드

> 📄 학사팀 대시보드 (페이지)

|요청메소드|GET|
|----|----|
|URL|/academy-dashboard|
|HTML|academy_dashboard.html|

<br>

|응답||
|----|:----:|
|(로그인 세션 존재) 성공 시|학사팀 대시보드 페이지로 이동|
|(로그인 세션 미존재) 실패 시| 로그인 페이지로 이동|

<br>

|(1) 응답 성공 시 - 학사 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|department_list|List&lt;DepartmentEntity&gt;|학부/학과 목록|
|major_list|List&lt;MajorEntity&gt;|전공 목록|
|selected_dept|DepartmentEntity|검색한 학부/학과 (기본값)|
|selected_major|MajorEntity|검색한 전공 (기본값)|
|selected_year|Integer|검색한 연도 (기본값)|
|pilgyo_list|List&lt;CourseEntity&gt;|교양기초 과목 리스트|
|daegyo_list|List&lt;CourseEntity&gt;|대학교양(필수) 과목 리스트|
|seongyo_number_list|List&lt;Integer&gt;|대학교양(선택) 이수영역 번호 리스트|
|jeontam_list|List&lt;CourseEntity&gt;|전공탐색 과목 리스트|
|jeonpil_list|List&lt;CourseEntity&gt;|전공필수 과목 리스트|
|jeonseon_list|List&lt;CourseEntity&gt;|전공선택 과목 리스트|
|pilgyo_req_credit|Float|필수교양 필요학점|
|daegyo_req_credit|Float|대학교양(필수) 필요학점|
|seongyo_req_credit|Float|대학교양(선택) 필요학점|
|jeontam_req_credit|Float|전공탐색 필요학점|
|advanced_req_credit|Float|3000단위 이상 과목 필요학점|
|jeonpil_req_credit|Float|전공필수 필요학점|
|jeonseon_req_credit|Float|전공선택 필요학점|
|foreign_cert_list|List&lt;ForeignCertEntity&gt;|외국어 인증 리스트|
|comm_cert_list|List&lt;CommunicationCertEntity&gt;|정보 인증 리스트|

<br>

|(2) 응답 실패 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|msg|String|실패 원인|

<br>
<br>

> 📡 학사팀 대시보드 (API)

|요청메소드|POST|
|----|----|
|URL|/academy-dashboard|

<br>

|요청|||
|----|----|----|
|필드명|타입|설명|
|department|Integer|검색한 학부의 고유번호|
|year|Integer|검색한 입학연도|
|major|Integer|검색한 전공의 고유번호|

<br>

|응답||
|----|:----:|
|(로그인 세션 존재) 성공 시|학사팀 대시보드 페이지로 이동|
|(로그인 세션 미존재) 실패 시| 로그인 페이지로 이동|

<br>

|(1) 응답 성공 시 - 학사팀 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|department_list|List&lt;DepartmentEntity&gt;|학부/학과 목록|
|major_list|List&lt;MajorEntity&gt;|전공 목록|
|selected_dept|DepartmentEntity|검색한 학부/학과|
|selected_major|MajorEntity|검색한 전공|
|selected_year|Integer|검색한 연도|
|pilgyo_list|List&lt;CourseEntity&gt;|교양기초 과목 리스트|
|daegyo_list|List&lt;CourseEntity&gt;|대학교양(필수) 과목 리스트|
|seongyo_number_list|List&lt;Integer&gt;|대학교양(선택) 이수영역 번호 리스트|
|jeontam_list|List&lt;CourseEntity&gt;|전공탐색 과목 리스트|
|jeonpil_list|List&lt;CourseEntity&gt;|전공필수 과목 리스트|
|jeonseon_list|List&lt;CourseEntity&gt;|전공선택 과목 리스트|
|pilgyo_req_credit|Float|필수교양 필요학점|
|daegyo_req_credit|Float|대학교양(필수) 필요학점|
|seongyo_req_credit|Float|대학교양(선택) 필요학점|
|jeontam_req_credit|Float|전공탐색 필요학점|
|advanced_req_credit|Float|3000단위 이상 과목 필요학점|
|jeonpil_req_credit|Float|전공필수 필요학점|
|jeonseon_req_credit|Float|전공선택 필요학점|
|foreign_cert_list|List&lt;ForeignCertEntity&gt;|외국어 인증 리스트|
|comm_cert_list|List&lt;CommunicationCertEntity&gt;|정보 인증 리스트|

<br>

|(2) 응답 실패 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|msg|String|실패 원인|

<br>

------

<br>

### 📌과목 삭제

> 📡 과목 삭제 (API)

|요청메소드|POST|
|----|----|
|URL|/exclude-course|

<br>

|요청|||
|----|----|----|
|필드명|타입|설명|
|department|Integer|검색한 학부의 고유번호|
|year|Integer|검색한 입학연도|
|major|Integer|검색한 전공의 고유번호|
|type|Integer|1=교양기초, 2=대학교양, 3=전공탐색, 4=전공필수|
|courses|List&lt;Integer&gt;|강의 고유번호 리스트 (form태그 내 모든 checkbox들의 name 값을 courses)|

<br>

|응답||
|----|:----:|
|(로그인 세션 존재) 성공 시|학사팀 대시보드 페이지로 이동|
|(로그인 세션 미존재) 실패 시|로그인 페이지로 이동|

<br>

|(1) 응답 성공 시 - 학사팀 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|* 기존 학사팀 대시보드 필드|||
|exclude_course_msg|String|과목삭제 성공 또는 실패 메세지|

<br>

|(2) 응답 실패 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|msg|String|실패 원인|

<br>

------

<br>

### 📌학점 편집

> 📡 학점 편집 (API)

|요청메소드|POST|
|----|----|
|URL|/edit-credit|

<br>

|요청|||
|----|----|----|
|필드명|타입|설명|
|department|Integer|검색한 학부의 고유번호|
|year|Integer|검색한 입학연도|
|major|Integer|검색한 전공의 고유번호|
|daegyo_credit|Integer|대학교양 학점|
|pilgyo_credit|Integer|필수교양 학점|
|advanced_credit|Integer|3000단위이상과목 필요학점|
|jeontam_credit|Integer|전공탐색 필요학점|
|jeonpil_credit|Integer|전공필수 필요학점|
|jeonseon_credit|Integer|전공선택 필요학점|

<br>

|응답||
|----|:----:|
|(로그인 세션 존재) 성공 시|학사팀 대시보드 페이지로 이동|
|(로그인 세션 미존재) 실패 시|로그인 페이지로 이동|

<br>

|(1) 응답 성공 시 - 학사팀 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|* 기존 학사팀 대시보드 필드|||
|edit_credit_msg|String|학점 편집 성공 또는 실패 메세지|

<br>

|(2) 응답 실패 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|msg|String|실패 원인|

<br>

------

<br>

### 📌이수영역 편집

> 📡 이수영역 편집 (API)

|요청메소드|POST|
|----|----|
|URL|/edit-number|

<br>

|요청|||
|----|----|----|
|필드명|타입|설명|
|department|Integer|검색한 학부의 고유번호|
|year|Integer|검색한 입학연도|
|major|Integer|검색한 전공의 고유번호|
|numbers|List&lt;Integer&gt;|이수영역번호 리스트 (form태그 내 모든 checkbox들의 name 값을 numbers)|

<br>

|응답||
|----|:----:|
|(로그인 세션 존재) 성공 시|학사팀 대시보드 페이지로 이동|
|(로그인 세션 미존재) 실패 시|로그인 페이지로 이동|

<br>

|(1) 응답 성공 시 - 학사팀 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|* 기존 학사팀 대시보드 필드|||
|edit_number_msg|String|이수영역번호 수정 성공 및 실패 메세지|

<br>

|(2) 응답 실패 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|msg|String|실패 원인|

<br>

------

<br>

### 📌외국어/정보인증 추가

> 📡 외국어/정보인증 추가 (API)

|요청메소드|POST|
|----|----|
|URL|/add-cert|

<br>

|요청|||
|----|----|----|
|필드명|타입|설명|
|department|Integer|검색한 학부의 고유번호|
|year|Integer|검색한 입학연도|
|major|Integer|검색한 전공의 고유번호|
|cert_type|Integer|1=외국어인증, 2=정보인증|
|name|String|자격명|
|descript|String|인증기준|
|score|Float|점수|

<br>

|응답||
|----|:----:|
|(로그인 세션 존재) 성공 시|학사팀 대시보드 페이지로 이동|
|(로그인 세션 미존재) 실패 시|로그인 페이지로 이동|

<br>

|(1) 응답 성공 시 - 학사팀 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|* 기존 학사팀 대시보드 필드|||
|add_cert_msg|String|외국어/정보인증 추가 성공 또는 실패 메세지|

<br>

|(2) 응답 실패 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|msg|String|실패 원인|

<br>

------

<br>

### 📌외국어/정보인증 삭제

> 📡 외국어/정보인증 삭제 (API)

|요청메소드|POST|
|----|----|
|URL|/delete-cert|

<br>

|요청|||
|----|----|----|
|필드명|타입|설명|
|department|Integer|검색한 학부의 고유번호|
|year|Integer|검색한 입학연도|
|major|Integer|검색한 전공의 고유번호|
|type|Integer|1=외국어인증, 2=정보인증|
|certs|List&lt;Integer&gt;|외국어/정보인증 고유번호 리스트 (해당 form태그 내 모든 checkbox들의 name 값을 certs)|

<br>

|응답||
|----|:----:|
|(로그인 세션 존재) 성공 시|학사팀 대시보드 페이지로 이동|
|(로그인 세션 미존재) 실패 시|로그인 페이지로 이동|

<br>

|(1) 응답 성공 시 - 학사팀 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|* 기존 학사팀 대시보드 필드|||
|delete_cert_msg|String|외국어/정보인증 삭제 성공 또는 실패 메세지|

<br>

|(2) 응답 실패 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|msg|String|실패 원인|

<br>
<br>
<br>

------

<br>
<br>
<br>

### 📌ERD

![ERD](https://github.com/user-attachments/assets/5f50cc17-6ef7-4db4-ad1d-784c2cffae47)

<br>
