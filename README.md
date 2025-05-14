# 졸업코치

### 웰컴 스크린 진입

> 웰컴스크린 (페이지)

|요청메소드|GET|
|----|----|
|URL|/welcome|
|HTML|welcome.html|

<br>

---

<br>

### 로그인

> 로그인 (페이지)

|요청메소드|GET|
|----|----|
|URL|/login|
|HTML|login.html|

<br>

> 로그인 요청 (API)

|요청메소드|POST|
|----|----|
|URL|/login|

|요청|||
|----|----|----|
|필드명|타입|설명|
|user_id|String|학번 또는 학사팀 아이디|
|user_pwd|String|비밀번호|

|응답||
|----|----|
|학생 로그인 성공 시|학생 대시보드 페이지로 이동|
|학사팀 로그인 성공 시|학사팀 대시보드 페이지로 이동|
|실패 시|로그인 페이지로 리다이렉트|

|(1) 응답 성공 시 - 학생 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|univ|String|학교명|
|major|String|전공명|
|id|String|학번|
|foreign_cert|Boolean|외국어 인증 여부|
|comm_cert|Boolean|정보 인증 여부|
|total_credit|Float|총 이수학점|
|total_req_credit|Float|필요 이수학점|
|jeon_tam_credit|Float|전공탐색 이수학점|
|jeon_tam_req_credit|Float|전공탐색 필요학점|
|pil_gyo_credit|Float|필수교양 이수학점|
|pil_gyo_req_credit|Float|필수교양 필요학점|
|dae_gyo_credit|Float|필수대학교양 이수학점|
|dae_gyo_req_credit|Float|필수대학교양 필요학점|
|seon_gyo_credit|Float|선택대학교양 이수학점|
|seon_gyo_req_credit|Float|선택대학교양 필요학점|
|advanced_credit|Float|3000단위 이상 과목 이수학점|
|advanced_req_credit|Float|3000단위 이상 과목 필요학점|
|jeon_pil_credit|Float|전공필수 이수학점|
|jeon_pil_req_credit|Float|전공필수 필요학점|
|jeon_seon_credit|Float|전공선택 이수학점|
|jeon_seon_req_credit|Float|전공선택 필요학점|

|(2) 응답 성공 시 - 학사팀 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|-|-|-|

|(3) 응답 실패 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|msg|String|실패 원인|

<br>

------

<br>

### 회원가입

> 회원가입 (페이지)

|요청메소드|GET|
|----|----|
|URL|/signup|
|HTML|signup.html|

|응답||
|----|:----:|
|성공 시|회원가입 페이지로 이동|
|실패 시|-|

|(1) 응답 성공 시 - 회원가입 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|university_list|ArrayList\<Integer>|대학 리스트|
|university_id_list|ArrayList\<Integer>|대학 고유번호 리스트|

|(2) 응답 실패 시 - 회원가입 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|-|-|-|

<br>

> 회원가입 요청 (API)

|요청메소드|POST|
|----|----|
|URL|/signup|

|요청|||
|----|----|----|
|필드명|타입|설명|
|name|String|이름|
|email|String|이메일|
|univ|String|대학명|
|major|String|전공명|
|id|String|학번|
|pwd|String|비밀번호|
|pwd_check|String|비밀번호 확인|

|응답||
|----|----|
|성공 시|로그인 페이지로 이동|
|실패 시|회원가입 페이지로 리다이렉트|

|(1) 응답 성공 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|success_signup_msg|String|회원가입 성공 알림|

|(2) 응답 실패 시 - 회원가입 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|university_list|ArrayList\<Integer>|대학 리스트|
|university_id_list|ArrayList\<Integer>|대학 고유번호 리스트|
|name|String|이름|
|email|String|이메일|
|univ|String|대학명|
|major|String|전공명|
|id|String|학번|
|pwd|String|비밀번호|
|msg|String|실패 원인|

<br>

------

<br>

### 로그아웃

> 로그아웃 요청 (API)

|요청메소드|GET|
|----|----|
|URL|/logout|

|응답||
|----|:----:|
|성공 시|웰컴페이지로 이동|
|실패 시|-|

<br>

------

<br>

### 학생 대시보드

> 학생 대시보드 (페이지)

|요청메소드|GET|
|----|----|
|URL|/student-dashboard|
|HTML|student_dashboard.html|

|응답||
|----|:----:|
|(로그인 세션 존재) 성공 시|학생 대시보드 페이지로 이동|
|(로그인 세션 미존재) 실패 시| 로그인 페이지로 이동|

|(1) 응답 성공 시 - 학생 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|univ|String|학교명|
|major|String|전공명|
|id|String|학번|
|foreign_cert|Boolean|외국어 인증 여부|
|comm_cert|Boolean|정보 인증 여부|
|total_credit|Float|총 이수학점|
|total_req_credit|Float|필요 이수학점|
|jeon_tam_credit|Float|전공탐색 이수학점|
|jeon_tam_req_credit|Float|전공탐색 필요학점|
|pil_gyo_credit|Float|필수교양 이수학점|
|pil_gyo_req_credit|Float|필수교양 필요학점|
|dae_gyo_credit|Float|필수대학교양 이수학점|
|dae_gyo_req_credit|Float|필수대학교양 필요학점|
|seon_gyo_credit|Float|선택대학교양 이수학점|
|seon_gyo_req_credit|Float|선택대학교양 필요학점|
|advanced_credit|Float|3000단위 이상 과목 이수학점|
|advanced_req_credit|Float|3000단위 이상 과목 필요학점|
|jeon_pil_credit|Float|전공필수 이수학점|
|jeon_pil_req_credit|Float|전공필수 필요학점|
|jeon_seon_credit|Float|전공선택 이수학점|
|jeon_seon_req_credit|Float|전공선택 필요학점|

|(2) 응답 실패 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|msg|String|실패 원인|

<br>

------

<br>

### 학사팀 대시보드

> 학사팀 대시보드 (페이지)

|요청메소드|GET|
|----|----|
|URL|/academy-dashboard|
|HTML|academy_dashboard.html|

|응답||
|----|:----:|
|(로그인 세션 존재) 성공 시|학사팀 대시보드 페이지로 이동|
|(로그인 세션 미존재) 실패 시| 로그인 페이지로 이동|

|(1) 응답 성공 시 - 학사팀 대시보드 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|-|-|-|

|(2) 응답 실패 시 - 로그인 페이지 Thymeleaf|||
|----|----|----|
|필드명|타입|설명|
|msg|String|실패 원인|

<br>

------

<br>
