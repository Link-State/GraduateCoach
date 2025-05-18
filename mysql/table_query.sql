
-- 기존의 모든 테이블 삭제
DROP TABLE IF EXISTS foreigncert;
DROP TABLE IF EXISTS communicationcert;
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
DROP TABLE IF EXISTS graduate;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS student;
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
  department INT         NOT NULL COMMENT '소속학부 고유번호',
  year       INT         NOT NULL COMMENT '신입학 연도',
  name       VARCHAR(64) NOT NULL COMMENT '자격명',
  score      INT         NULL     COMMENT '점수',
  PRIMARY KEY (department, year, name)
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
  type   INT NOT NULL COMMENT '기초=1, 전선=2, 전필=3',
  course INT NOT NULL COMMENT '강의 고유번호',
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
  major        INT   NOT NULL COMMENT '소속전공 고유번호',
  year         INT   NOT NULL COMMENT '신입학 연도',
  total_credit FLOAT NOT NULL COMMENT '필요 전공 이수학점',
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
  department INT         NOT NULL COMMENT '소속학부 고유번호',
  year       INT         NOT NULL COMMENT '신입학 연도',
  name       VARCHAR(64) NOT NULL COMMENT '자격명',
  score      INT         NULL     COMMENT '점수',
  PRIMARY KEY (department, year, name)
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
  UID                INT         NOT NULL AUTO_INCREMENT COMMENT '학생 고유번호',
  id                 VARCHAR(64) NOT NULL COMMENT '학번',
  year               INT         NOT NULL COMMENT '입학연도',
  foreign_cert       BOOL        NOT NULL COMMENT '외국어 인증 상태',
  communication_cert BOOL        NOT NULL COMMENT '정보 인증 상태',
  user               INT         NOT NULL COMMENT '유저 고유번호',
  PRIMARY KEY (UID)
) COMMENT '학생 정보';

CREATE TABLE StudentsCourse
(
  student INT   NOT NULL COMMENT '학생 고유번호',
  course  INT   NOT NULL COMMENT '강의 고유번호',
  grade   FLOAT NOT NULL COMMENT '성적',
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

