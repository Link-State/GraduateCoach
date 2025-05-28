
-- 기존의 모든 테이블 삭제
DROP TABLE IF EXISTS foundationmajor;
DROP TABLE IF EXISTS foundationeducation;
DROP TABLE IF EXISTS essentialgeneraleducation;
DROP TABLE IF EXISTS optionalgeneraleducation;
DROP TABLE IF EXISTS coursetype;
DROP TABLE IF EXISTS earnmajor;
DROP TABLE IF EXISTS studentscourse;
DROP TABLE IF EXISTS studentsmajor;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS edugroup;
DROP TABLE IF EXISTS major;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS foreigncert;
DROP TABLE IF EXISTS communicationcert;
DROP TABLE IF EXISTS graduate;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS academy;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS university;

-- 테이블 생성
CREATE TABLE Academy
(
  UID  INT NOT NULL AUTO_INCREMENT COMMENT '학사관리 고유번호',
  user INT NOT NULL COMMENT '유저 고유번호',
  PRIMARY KEY (UID)
) COMMENT '학사팀 정보';

CREATE TABLE CommunicationCert
(
  UID        INT          NOT NULL AUTO_INCREMENT COMMENT '정보인증 고유번호',
  name       VARCHAR(64)  NOT NULL COMMENT '자격명',
  descript   VARCHAR(128) NOT NULL COMMENT '인증기준',
  score      INT          NULL     COMMENT '점수',
  department INT          NOT NULL COMMENT '소속학부 고유번호',
  year       INT          NOT NULL COMMENT '신입학 연도',
  PRIMARY KEY (UID)
) COMMENT '정보 인증';

CREATE TABLE Course
(
  UID    INT         NOT NULL AUTO_INCREMENT COMMENT '강의 고유번호',
  name   VARCHAR(64) NOT NULL COMMENT '강의명',
  code   VARCHAR(64) NOT NULL COMMENT '학정번호',
  level  INT         NOT NULL COMMENT '단위',
  credit FLOAT       NOT NULL COMMENT '학점',
  number INT         NULL     COMMENT '대학교양 영역번호',
  PRIMARY KEY (UID)
) COMMENT '강의 정보';

CREATE TABLE CourseType
(
  major  INT NOT NULL COMMENT '소속전공 고유번호',
  year   INT NOT NULL COMMENT '신입학 연도',
  course INT NOT NULL COMMENT '강의 고유번호',
  type   INT NOT NULL COMMENT '기초=1, 전선=2, 전필=3',
  PRIMARY KEY (major, year, course)
) COMMENT '과목 종별';

CREATE TABLE Department
(
  UID        INT         NOT NULL AUTO_INCREMENT COMMENT '학부 고유번호',
  name       VARCHAR(64) NOT NULL COMMENT '학부명',
  university INT         NOT NULL COMMENT '소속대학 고유번호',
  PRIMARY KEY (UID)
) COMMENT '학부 정보';

CREATE TABLE EarnMajor
(
  major            INT   NOT NULL COMMENT '소속전공 고유번호',
  year             INT   NOT NULL COMMENT '신입학 연도',
  opt_major_credit FLOAT NOT NULL COMMENT '필요 전공선택 이수학점',
  req_major_credit FLOAT NOT NULL COMMENT '필요 전공필수 이수학점',
  PRIMARY KEY (major, year)
) COMMENT '전공 이수 요건';

CREATE TABLE EduGroup
(
  number INT         NOT NULL COMMENT '대학교양 영역번호',
  name   VARCHAR(64) NOT NULL COMMENT '영역명',
  PRIMARY KEY (number)
) COMMENT '대학교양 영역';

CREATE TABLE EssentialGeneralEducation
(
  department INT NOT NULL COMMENT '소속학부 고유번호',
  year       INT NOT NULL COMMENT '신입학 연도',
  course     INT NOT NULL COMMENT '강의 고유번호',
  PRIMARY KEY (department, year, course)
) COMMENT '대학교양 필수이수';

CREATE TABLE ForeignCert
(
  UID        INT          NOT NULL AUTO_INCREMENT COMMENT '외국어인증 고유번호',
  name       VARCHAR(64)  NOT NULL COMMENT '자격명',
  descript   VARCHAR(128) NOT NULL COMMENT '인증기준',
  score      INT          NULL     COMMENT '점수',
  department INT          NOT NULL COMMENT '소속학부 고유번호',
  year       INT          NOT NULL COMMENT '신입학 연도',
  PRIMARY KEY (UID)
) COMMENT '외국어 인증';

CREATE TABLE FoundationEducation
(
  department INT NOT NULL COMMENT '소속학부 고유번호',
  year       INT NOT NULL COMMENT '신입학 연도',
  course     INT NOT NULL COMMENT '강의 고유번호',
  PRIMARY KEY (department, year, course)
) COMMENT '필수교양';

CREATE TABLE FoundationMajor
(
  department INT NOT NULL COMMENT '소속학부 고유번호',
  year       INT NOT NULL COMMENT '신입학 연도',
  course     INT NOT NULL COMMENT '강의 고유번호',
  PRIMARY KEY (department, year, course)
) COMMENT '전공탐색';

CREATE TABLE Graduate
(
  department       INT  NOT NULL COMMENT '소속학부 고유번호',
  year             INT  NOT NULL COMMENT '신입학 연도',
  total_credit     INT  NOT NULL COMMENT '총 이수학점',
  total_level      INT  NOT NULL COMMENT '총 3000단위 이상 과목 학점',
  second_major     BOOL NOT NULL COMMENT '제2전공 필수 여부',
  foundation_edu   INT  NOT NULL COMMENT '필요 교양기초 학점',
  general_edu      INT  NOT NULL COMMENT '필요 대학교양 학점',
  foundation_major INT  NOT NULL COMMENT '필요 전공탐색 학점',
  optional_edu     INT  NOT NULL COMMENT '필요 선택교양 학점',
  least_cut        INT  NOT NULL COMMENT '선택교양 최소 이수영역 수',
  PRIMARY KEY (department, year)
) COMMENT '졸업 요건';

CREATE TABLE Major
(
  UID        INT         NOT NULL AUTO_INCREMENT COMMENT '전공 고유번호',
  name       VARCHAR(64) NOT NULL COMMENT '전공명',
  department INT         NOT NULL COMMENT '소속학부 고유번호',
  PRIMARY KEY (UID)
) COMMENT '전공';

CREATE TABLE OptionalGeneralEducation
(
  department INT NOT NULL COMMENT '소속학부 고유번호',
  year       INT NOT NULL COMMENT '신입학 연도',
  number     INT NOT NULL COMMENT '대학교양 영역번호',
  PRIMARY KEY (department, year, number)
) COMMENT '대학교양 선택이수 영역';

CREATE TABLE Student
(
  UID          INT         NOT NULL AUTO_INCREMENT COMMENT '학생 고유번호',
  id           VARCHAR(64) NOT NULL COMMENT '학번',
  year         INT         NOT NULL COMMENT '입학연도',
  user         INT         NOT NULL COMMENT '유저 고유번호',
  foreign_cert INT         NULL     COMMENT '외국어인증 고유번호',
  comm_cert    INT         NULL     COMMENT '정보인증 고유번호',
  PRIMARY KEY (UID)
) COMMENT '학생 정보';

CREATE TABLE StudentsCourse
(
  student INT   NOT NULL COMMENT '학생 고유번호',
  course  INT   NOT NULL COMMENT '강의 고유번호',
  grade   FLOAT NOT NULL COMMENT '성적',
  state   INT   NOT NULL COMMENT '상태',
  PRIMARY KEY (student, course)
) COMMENT '이수 강의';

CREATE TABLE StudentsMajor
(
  student     INT NOT NULL COMMENT '학생 고유번호',
  major_order INT NOT NULL COMMENT '전공 순서',
  major       INT NOT NULL COMMENT '전공 고유번호',
  PRIMARY KEY (student, major_order)
) COMMENT '학생 전공';

CREATE TABLE University
(
  UID  INT         NOT NULL AUTO_INCREMENT COMMENT '대학 고유번호',
  name VARCHAR(64) NOT NULL COMMENT '대학명',
  PRIMARY KEY (UID)
) COMMENT '대학 정보';

CREATE TABLE User
(
  UID        INT         NOT NULL AUTO_INCREMENT COMMENT '유저 고유번호',
  id         VARCHAR(64) NOT NULL COMMENT '유저 아이디',
  password   VARCHAR(64) NOT NULL COMMENT '유저 패스워드',
  email      VARCHAR(64) NOT NULL COMMENT '이메일',
  university INT         NOT NULL COMMENT '소속대학 고유번호',
  PRIMARY KEY (UID)
) COMMENT '유저 정보';

ALTER TABLE Department
  ADD CONSTRAINT FK_University_TO_Department
    FOREIGN KEY (university)
    REFERENCES University (UID);

ALTER TABLE User
  ADD CONSTRAINT FK_University_TO_User
    FOREIGN KEY (university)
    REFERENCES University (UID);

ALTER TABLE Student
  ADD CONSTRAINT FK_User_TO_Student
    FOREIGN KEY (user)
    REFERENCES User (UID);

ALTER TABLE StudentsMajor
  ADD CONSTRAINT FK_Student_TO_StudentsMajor
    FOREIGN KEY (student)
    REFERENCES Student (UID);

ALTER TABLE Major
  ADD CONSTRAINT FK_Department_TO_Major
    FOREIGN KEY (department)
    REFERENCES Department (UID);

ALTER TABLE StudentsMajor
  ADD CONSTRAINT FK_Major_TO_StudentsMajor
    FOREIGN KEY (major)
    REFERENCES Major (UID);

ALTER TABLE StudentsCourse
  ADD CONSTRAINT FK_Student_TO_StudentsCourse
    FOREIGN KEY (student)
    REFERENCES Student (UID);

ALTER TABLE StudentsCourse
  ADD CONSTRAINT FK_Course_TO_StudentsCourse
    FOREIGN KEY (course)
    REFERENCES Course (UID);

ALTER TABLE Academy
  ADD CONSTRAINT FK_User_TO_Academy
    FOREIGN KEY (user)
    REFERENCES User (UID);

ALTER TABLE EarnMajor
  ADD CONSTRAINT FK_Major_TO_EarnMajor
    FOREIGN KEY (major)
    REFERENCES Major (UID);

ALTER TABLE Graduate
  ADD CONSTRAINT FK_Department_TO_Graduate
    FOREIGN KEY (department)
    REFERENCES Department (UID);

ALTER TABLE FoundationEducation
  ADD CONSTRAINT FK_Course_TO_FoundationEducation
    FOREIGN KEY (course)
    REFERENCES Course (UID);

ALTER TABLE FoundationMajor
  ADD CONSTRAINT FK_Course_TO_FoundationMajor
    FOREIGN KEY (course)
    REFERENCES Course (UID);

ALTER TABLE ForeignCert
  ADD CONSTRAINT FK_Graduate_TO_ForeignCert
    FOREIGN KEY (department, year)
    REFERENCES Graduate (department, year);

ALTER TABLE CommunicationCert
  ADD CONSTRAINT FK_Graduate_TO_CommunicationCert
    FOREIGN KEY (department, year)
    REFERENCES Graduate (department, year);

ALTER TABLE CourseType
  ADD CONSTRAINT FK_EarnMajor_TO_CourseType
    FOREIGN KEY (major, year)
    REFERENCES EarnMajor (major, year);

ALTER TABLE CourseType
  ADD CONSTRAINT FK_Course_TO_CourseType
    FOREIGN KEY (course)
    REFERENCES Course (UID);

ALTER TABLE EssentialGeneralEducation
  ADD CONSTRAINT FK_Graduate_TO_EssentialGeneralEducation
    FOREIGN KEY (department, year)
    REFERENCES Graduate (department, year);

ALTER TABLE FoundationEducation
  ADD CONSTRAINT FK_Graduate_TO_FoundationEducation
    FOREIGN KEY (department, year)
    REFERENCES Graduate (department, year);

ALTER TABLE OptionalGeneralEducation
  ADD CONSTRAINT FK_Graduate_TO_OptionalGeneralEducation
    FOREIGN KEY (department, year)
    REFERENCES Graduate (department, year);

ALTER TABLE FoundationMajor
  ADD CONSTRAINT FK_Graduate_TO_FoundationMajor
    FOREIGN KEY (department, year)
    REFERENCES Graduate (department, year);

ALTER TABLE Course
  ADD CONSTRAINT FK_EduGroup_TO_Course
    FOREIGN KEY (number)
    REFERENCES EduGroup (number);

ALTER TABLE OptionalGeneralEducation
  ADD CONSTRAINT FK_EduGroup_TO_OptionalGeneralEducation
    FOREIGN KEY (number)
    REFERENCES EduGroup (number);

ALTER TABLE EssentialGeneralEducation
  ADD CONSTRAINT FK_Course_TO_EssentialGeneralEducation
    FOREIGN KEY (course)
    REFERENCES Course (UID);

ALTER TABLE Student
  ADD CONSTRAINT FK_ForeignCert_TO_Student
    FOREIGN KEY (foreign_cert)
    REFERENCES ForeignCert (UID);

ALTER TABLE Student
  ADD CONSTRAINT FK_CommunicationCert_TO_Student
    FOREIGN KEY (comm_cert)
    REFERENCES CommunicationCert (UID);




-- **************** 데이터 추가 부분 -- ****************




INSERT INTO University (UID, name)
VALUES (1, "연세대학교 미래캠퍼스");

-- 학사 계정 password : asdfzxcv1234
INSERT INTO User (UID, id, password, email, university)
VALUES (1, "Y100000", "$2a$10$SXKRFNjxt7jepEq6vKqTCOclUIoddF.jq0sCLKZweB5oI7IJowERK", "", 1);

-- 학생 계정 password : qwerasdf1234
INSERT INTO User (UID, id, password, email, university)
VALUES (2, "2022000000", "$2a$10$/amkQHcwxAuPO1OV3VmtOOaN60NH4hHbWAsSFJtWoxySl9DAs5N92", "test@test.com", 1);

INSERT INTO Department (UID, name, university)
VALUES (1, "소프트웨어", 1);

INSERT INTO Student (UID, id, year, user, foreign_cert, comm_cert)
VALUES (1, "2022000000", 2022, 2, NULL, NULL);

INSERT INTO Academy (UID, user)
VALUES (1, 1);

INSERT INTO Major (UID, name, department)
VALUES (1, "소프트웨어전공", 1);

INSERT INTO Major (UID, name, department)
VALUES (2, "소프트웨어심화전공", 1);

INSERT INTO EarnMajor (major, year, req_major_credit, opt_major_credit)
VALUES (1, 2021, 9, 27);

INSERT INTO EarnMajor (major, year, req_major_credit, opt_major_credit)
VALUES (2, 2021, 3, 33);

INSERT INTO EarnMajor (major, year, req_major_credit, opt_major_credit)
VALUES (1, 2022, 9, 27);

INSERT INTO EarnMajor (major, year, req_major_credit, opt_major_credit)
VALUES (2, 2022, 3, 33);

INSERT INTO Graduate (department, year, total_credit, total_level, second_major, foundation_edu, general_edu, foundation_major, optional_edu, least_cut)
VALUES (1, 2022, 135, 45, true, 22, 5, 21, 15, 5);

INSERT INTO Graduate (department, year, total_credit, total_level, second_major, foundation_edu, general_edu, foundation_major, optional_edu, least_cut)
VALUES (1, 2021, 135, 45, false, 22, 5, 21, 15, 5);

INSERT INTO StudentsMajor (student, major_order, major)
VALUES (1, 1, 1);

INSERT INTO StudentsMajor (student, major_order, major)
VALUES (1, 2, 2);

INSERT INTO EduGroup (number, name)
VALUES (1, "문학과예술");

INSERT INTO EduGroup (number, name)
VALUES (2, "인간과역사");

INSERT INTO EduGroup (number, name)
VALUES (3, "언어와표현");

INSERT INTO EduGroup (number, name)
VALUES (4, "가치와윤리");

INSERT INTO EduGroup (number, name)
VALUES (5, "국가와사회");

INSERT INTO EduGroup (number, name)
VALUES (6, "지역과세계");

INSERT INTO EduGroup (number, name)
VALUES (7, "논리와수리");

INSERT INTO EduGroup (number, name)
VALUES (8, "자연과우주");

INSERT INTO EduGroup (number, name)
VALUES (9, "생명과환경");

INSERT INTO EduGroup (number, name)
VALUES (10, "정보와기술");

INSERT INTO EduGroup (number, name)
VALUES (11, "체육과건강");

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (1, "채플", "YHA1002", 0000, 2, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (2, "기독교의이해", "YHB1001", 0000, 3, NULL);

-- INSERT INTO Course (UID, name, code, level, credit, number)
-- VALUES (2, "성서와기독교", "YHB1002", 0000, 3, NULL);

-- INSERT INTO Course (UID, name, code, level, credit, number)
-- VALUES (3, "기독교역사와문화", "YHB1003", 0000, 3, NULL);

-- INSERT INTO Course (UID, name, code, level, credit, number)
-- VALUES (4, "기독교와과학", "YHB1004", 0000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (5, "글쓰기", "YHC1001", 0000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (6, "교양영어1", "YHD1001", 0000, 2, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (7, "교양영어2", "YHD1002", 0000, 2, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (8, "리더십개발", "YHE1001", 0000, 2, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (9, "리더십실습", "YHE1002", 0000, 2, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (10, "대학학문의세계", "YHE1007", 0000, 1, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (11, "컴퓨팅사고", "YHX1001", 0000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (12, "진로지도", "YHZ1001", 0000, 0, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (13, "경력개발", "YHZ1002", 0000, 2, NULL);

-- INSERT INTO Course (UID, name, code, level, credit, number)
-- VALUES (14, "산업과기업의이해", "YHZ1003", 0000, 2, NULL);

-- INSERT INTO Course (UID, name, code, level, credit, number)
-- VALUES (15, "커리어디자인", "YHZ1004", 0000, 2, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (16, "SW소양영어", "YHH1030", 0000, 2, 3);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (17, "자바프로그래밍", "YHX1004", 0000, 3, 10);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (18, "결혼과가족", "YHQ1001", 0000, 3, 5);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (19, "서양음악의이해", "YHF1004", 0000, 3, 1);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (20, "부모교육", "YHQ1005", 0000, 3, 4);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (21, "한국전통문화의유산", "YHG1007", 0000, 3, 2);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (22, "축제문화의이해", "YHK1005", 0000, 3, 6);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (23, "미분적분학과벡터해석1", "YHL1007", 1000, 3, 7);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (24, "미분적분학과벡터해석2", "YHL1008", 1000, 3, 7);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (25, "데이터사이언스입문", "YHL1036", 1000, 3, 7);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (26, "일반물리학및실험1", "YHV1001", 1000, 3, 8);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (27, "컴퓨터프로그래밍", "YHX1009", 1000, 3, 10);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (28, "공업수학1", "YHL1010", 2000, 3, 7);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (29, "공업수학2", "YHL1011", 2000, 3, 7);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (30, "선형대수입문", "SWE2012", 2000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (31, "웹프로그래밍", "SWE2009", 2000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (32, "알고리즘기초", "SWE2007", 2000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (33, "이산구조", "YHL1012", 2000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (34, "데이터구조론", "SWE2001", 2000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (35, "오픈소스SW와리눅스", "SWE2002", 2000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (36, "객체지향프로그래밍", "SWE2003", 2000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (37, "논리회로설계", "SWE2004", 2000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (38, "회로이론", "SWE2005", 2000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (39, "시스템프로그래밍", "SWE2008", 2000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (40, "컴퓨터구조론", "SWE2010", 2000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (41, "신호및시스템", "SWE2011", 2000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (42, "SW창의설계", "SWE2013", 2000, 2, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (43, "운영체제", "SWE3001", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (44, "프로그래밍언어구조론", "SWE3002", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (45, "윈도우프로그래밍", "SWE3003", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (46, "확률및랜덤변수", "SWE3004", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (47, "선형대수활용", "SWE3005", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (48, "AI수학", "SWE3006", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (49, "SW프로젝트실전영어", "SWE3010", 3000, 2, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (50, "소프트웨어공학", "SWE3013", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (51, "병렬프로그래밍", "SWE3014", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (52, "모바일프로그래밍", "SWE3015", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (53, "인공지능", "SWE3016", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (54, "데이터베이스", "SWE3017", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (55, "데이터마이닝", "SWE3018", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (56, "디지털신호처리", "SWE3019", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (57, "수치해석과최적화", "SWE3020", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (58, "임베디드시스템", "SWE3022", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (59, "컴퓨터네트워크", "SWE3023", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (60, "정보보안", "SWE3024", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (61, "오픈소스SW응용과제", "SWE3025", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (62, "컴파일러설계", "SWE4001", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (63, "웹서비스응용", "SWE4002", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (64, "소프트웨어검증", "SWE4013", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (65, "알고리즘분석", "SWE4014", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (66, "SW엔지니어소양세미나", "SWE4027", 4000, 1, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (67, "마이크로프로세서", "SWE3007", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (68, "통신시스템", "SWE3008", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (69, "암호학", "SWE3009", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (70, "영상처리", "SWE3011", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (71, "디지털통신", "SWE3012", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (72, "그래프이론과활용", "SWE3021", 3000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (73, "기계학습개론", "SWE4003", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (74, "빅데이터처리", "SWE4004", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (75, "지능형멀티미디어시스템", "SWE4005", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (76, "수치편미분방정식", "SWE4006", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (77, "로봇알고리즘", "SWE4007", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (78, "임베디드하드웨어설계", "SWE4008", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (79, "산학공동프로젝트", "SWE4011", 4000, 2, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (80, "학부연구(1)", "SWE4012", 4000, 2, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (81, "자연어처리", "SWE4015", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (82, "바이오컴퓨팅", "SWE4016", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (83, "정보검색", "SWE4017", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (84, "스마트미디어통신", "SWE4018", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (85, "모델링및시뮬레이션", "SWE4019", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (86, "산업수학과역문제", "SWE4020", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (87, "IoT응용프로그래밍", "SWE4021", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (88, "정보보안응용", "SWE4022", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (89, "디지털포렌식", "SWE4023", 4000, 3, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (90, "융합캡스톤디자인", "SWE4028", 4000, 2, NULL);

INSERT INTO Course (UID, name, code, level, credit, number)
VALUES (91, "학부연구(2)", "SWE4026", 4000, 2, NULL);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 1, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 2, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 5, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 6, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 7, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 8, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 9, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 10, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 11, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 12, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 13, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 16, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 17, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 18, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 19, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 20, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 21, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 22, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 23, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 24, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 25, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 26, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 27, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 28, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 29, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 30, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 31, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 32, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 34, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 35, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 43, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 45, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 48, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 50, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 52, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 53, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 54, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 63, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 70, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 73, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 76, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 81, 0, 0);

INSERT INTO StudentsCourse (student, course, grade, state)
VALUES (1, 82, 0, 0);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 32, 3);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 34, 3);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 43, 3);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 50, 3);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 30, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 31, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 35, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 36, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 37, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 38, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 39, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 40, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 41, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 42, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 44, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 45, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 46, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 47, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 48, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 49, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 51, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 52, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 53, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 54, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 55, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 56, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 57, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 58, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 59, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 60, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 61, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 62, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 63, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 64, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 65, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 66, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 67, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 68, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 69, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 70, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 71, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 72, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 73, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 74, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 75, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 76, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 77, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 78, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 79, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 80, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 81, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 82, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 83, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 84, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 85, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 86, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 87, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 88, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 89, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 90, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (1, 2022, 91, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (2, 2022, 48, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (2, 2022, 50, 3);

INSERT INTO CourseType (major, year, course, type)
VALUES (2, 2022, 53, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (2, 2022, 54, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (2, 2022, 55, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (2, 2022, 56, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (2, 2022, 57, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (2, 2022, 58, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (2, 2022, 59, 2);

INSERT INTO CourseType (major, year, course, type)
VALUES (2, 2022, 60, 2);

INSERT INTO FoundationMajor (department, year, course)
VALUES (1, 2022, 23);

INSERT INTO FoundationMajor (department, year, course)
VALUES (1, 2022, 24);

INSERT INTO FoundationMajor (department, year, course)
VALUES (1, 2022, 25);

INSERT INTO FoundationMajor (department, year, course)
VALUES (1, 2022, 26);

INSERT INTO FoundationMajor (department, year, course)
VALUES (1, 2022, 27);

INSERT INTO FoundationMajor (department, year, course)
VALUES (1, 2022, 28);

INSERT INTO FoundationMajor (department, year, course)
VALUES (1, 2022, 29);

INSERT INTO FoundationMajor (department, year, course)
VALUES (1, 2022, 33);

INSERT INTO FoundationEducation (department, year, course)
VALUES (1, 2022, 1);

INSERT INTO FoundationEducation (department, year, course)
VALUES (1, 2022, 2);

INSERT INTO FoundationEducation (department, year, course)
VALUES (1, 2022, 5);

INSERT INTO FoundationEducation (department, year, course)
VALUES (1, 2022, 6);

INSERT INTO FoundationEducation (department, year, course)
VALUES (1, 2022, 7);

INSERT INTO FoundationEducation (department, year, course)
VALUES (1, 2022, 8);

INSERT INTO FoundationEducation (department, year, course)
VALUES (1, 2022, 9);

INSERT INTO FoundationEducation (department, year, course)
VALUES (1, 2022, 10);

INSERT INTO FoundationEducation (department, year, course)
VALUES (1, 2022, 11);

INSERT INTO FoundationEducation (department, year, course)
VALUES (1, 2022, 12);

INSERT INTO FoundationEducation (department, year, course)
VALUES (1, 2022, 13);

INSERT INTO EssentialGeneralEducation (department, year, course)
VALUES (1, 2022, 16);

INSERT INTO EssentialGeneralEducation (department, year, course)
VALUES (1, 2022, 17);

INSERT INTO OptionalGeneralEducation (department, year, number)
VALUES (1, 2022, 1);

INSERT INTO OptionalGeneralEducation (department, year, number)
VALUES (1, 2022, 2);

INSERT INTO OptionalGeneralEducation (department, year, number)
VALUES (1, 2022, 3);

INSERT INTO OptionalGeneralEducation (department, year, number)
VALUES (1, 2022, 4);

INSERT INTO OptionalGeneralEducation (department, year, number)
VALUES (1, 2022, 5);

INSERT INTO OptionalGeneralEducation (department, year, number)
VALUES (1, 2022, 6);

INSERT INTO OptionalGeneralEducation (department, year, number)
VALUES (1, 2022, 9);

INSERT INTO ForeignCert (UID, name, descript, score, department, year)
VALUES (1, "TOEIC", "650점 이상", 650, 1, 2022);

INSERT INTO CommunicationCert (UID, name, descript, score, department, year)
VALUES (1, "TOPCIT", "200점 이상", 200, 1, 2022);

INSERT INTO CommunicationCert (UID, name, descript, score, department, year)
VALUES (2, "정보처리기사", "정보처리기사 자격증 취득", 0, 1, 2022);
