--유저
INSERT INTO MEMBER VALUES(MEM_SEQ.NEXTVAL,'admin','admin','eamil',null,0,0,0,0,0,0,0,null,sysdate,null);
INSERT INTO MEMBER VALUES(MEM_SEQ.NEXTVAL,'user','user','email',null,0,0,0,0,0,0,0,null,sysdate,null);
INSERT INTO MEMBER VALUES(MEM_SEQ.NEXTVAL,'user1','user1','first@hotmail.com',null,0,0,0,0,0,0,0,null,sysdate,null);
INSERT INTO MEMBER VALUES(MEM_SEQ.NEXTVAL,'user2','user2','second@naver.com',null,0,0,0,0,0,0,0,null,sysdate,null);
INSERT INTO MEMBER VALUES(MEM_SEQ.NEXTVAL,'user3','user3','third@hanmail.net',null,0,0,0,0,0,0,0,null,sysdate,null);
INSERT INTO MEMBER VALUES(MEM_SEQ.NEXTVAL,'user4','user4','fourth@google.com',null,0,0,0,0,0,0,0,null,sysdate,null);
INSERT INTO MEMBER VALUES(MEM_SEQ.NEXTVAL,'user5','user5','fifth@yahoo.com',null,0,0,0,0,0,0,0,null,sysdate,null);
INSERT INTO MEMBER VALUES(MEM_SEQ.NEXTVAL,'user6','user6','sixth@hotmail.com',null,0,0,0,0,0,0,0,null,sysdate,null);
INSERT INTO MEMBER VALUES(MEM_SEQ.NEXTVAL,'user7','user7','seventh@hotmail.com',null,0,0,0,0,0,0,0,null,sysdate,null);
INSERT INTO MEMBER VALUES(MEM_SEQ.NEXTVAL,'user8','user8','eighth@daum.net',null,0,0,0,0,0,0,0,null,sysdate,null);
INSERT INTO MEMBER VALUES(MEM_SEQ.NEXTVAL,'user9','user9','ninth@google.com',null,0,0,0,0,0,0,0,null,sysdate,null);
INSERT INTO MEMBER VALUES(MEM_SEQ.NEXTVAL,'user10','user10','tenth@kh.org',null,0,0,0,0,0,0,0,null,sysdate,null);

--게시판 코드
INSERT INTO BOARDCODE VALUES (1,'기업');
INSERT INTO BOARDCODE VALUES (2,'QnA');
INSERT INTO BOARDCODE VALUES (3,'신기술');
INSERT INTO BOARDCODE VALUES (4,'아무말대잔치');
INSERT INTO BOARDCODE VALUES (5,'프로젝트/소스');

-- 기업 게시판
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'그린캣소프트(주)에서 함께 서비스를 개발하실 신입/초급 개발자를 모십니다','그린캣소프트(주)에서 함께 서비스를 개발하실 신입/초급 개발자를 모십니다',1,'admin',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'[프리랜서] 단기/ 웹사이트 구축 JAVA 고급 개발자 구인합니다.','[프리랜서] 단기/ 웹사이트 구축 JAVA 고급 개발자 구인합니다.',1,'user1',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'[e커머스 선두업체 정규직] 서비스 개발자를 찾고있습니다.','[e커머스 선두업체 정규직] 서비스 개발자를 찾고있습니다.',1,'user6',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'(주)제이씨엔터테인먼트 Java 웹 프로그래머 모집 공고','(주)제이씨엔터테인먼트 Java 웹 프로그래머 모집 공고',1,'user7',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'(주)사이냅소프트 2007년 신입 및 경력 사원 모집','(주)사이냅소프트 2007년 신입 및 경력 사원 모집',1,'user9',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'[충무로]보육통합정보시스템 기능개선_중급1명,PL1명','[충무로]보육통합정보시스템 기능개선_중급1명,PL1명',1,'user10',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'대기업 MES 개발_중~고급_7개월_C#.NET','대기업 MES 개발_중~고급_7개월_C#.NET',1,'admin',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'대기업) 네비게이션 시스템 SM운영_Java 개발자 (의왕/중급)','대기업) 네비게이션 시스템 SM운영_Java 개발자 (의왕/중급)',1,'user9',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'보험사_관리업무 구축_Java, Web개발자(JavaScript) [ 광화문 / 중,고급 ]','보험사_관리업무 구축_Java, Web개발자(JavaScript) [ 광화문 / 중,고급 ]',1,'user9',SYSDATE,NULL,NULL);

-- Q&A 게시판
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'MySQL 한글 질문입니다.','MySQL 한글 질문입니다.',2,'user9',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'개발 편의를 위해서 VO 및 DAO 안쓰면 어떤가요?','개발 편의를 위해서 VO 및 DAO 안쓰면 어떤가요?',2,'user1',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'JSP 공부 중 WEB-INF 폴더안에 jsp 파일 관련해서 질문드립니다','JSP 공부 중 WEB-INF 폴더안에 jsp 파일 관련해서 질문드립니다',2,'user7',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'이 방식이 무리가 많이 가는 코딩방식일까요?','이 방식이 무리가 많이 가는 코딩방식일까요?',2,'user3',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'자바스크립트 스코프 질문드립니다.','자바스크립트 스코프 질문드립니다.',2,'admin',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'스프링 프로젝트 war로 만들어서 톰캣에 올리는데 오류가 납니다.','스프링 프로젝트 war로 만들어서 톰캣에 올리는데 오류가 납니다.',2,'user5',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'[질문수정]MySQL 날짜 비교하여 가장 최근값 가져오기','[질문수정]MySQL 날짜 비교하여 가장 최근값 가져오기',2,'user10',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'jsp 한글파일명 다운로드 질문입니다.','jsp 한글파일명 다운로드 질문입니다.',2,'user2',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'ajax json에러 해결방법 도와주세요! (errorThrown: SyntaxError: Unexpected end of JSON input)','ajax json에러 해결방법 도와주세요! (errorThrown: SyntaxError: Unexpected end of JSON input)',2,'user4',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'ORA-01722: invalid number 에러 이해가 잘안됩니다.','ORA-01722: invalid number 에러 이해가 잘안됩니다.',2,'user7',SYSDATE,NULL,NULL);

-- 신기술 게시판
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'"개발자 없어서 못 뽑는다" 10대 프로그래밍 기술 - CIO Korea','"개발자 없어서 못 뽑는다" 10대 프로그래밍 기술 - CIO Korea',3,'user8',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'IT 경력 개발을 위한 8가지 트렌드 파악법 - CIO Korea','IT 경력 개발을 위한 8가지 트렌드 파악법 - CIO Korea',3,'user10',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'"두둑한 연봉을 위한" 2017년에 배울 만한 10가지 프로그래밍 언어','"두둑한 연봉을 위한" 2017년에 배울 만한 10가지 프로그래밍 언어',3,'user3',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'"개발자 없어서 못 뽑는다" 10대 프로그래밍 기술','"개발자 없어서 못 뽑는다" 10대 프로그래밍 기술',3,'user8',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'프로그래밍까지 진출한 AI··· "개발자는 데이터 과학자 돼야"','프로그래밍까지 진출한 AI··· "개발자는 데이터 과학자 돼야"',3,'user3',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'AWS-애저-구글, 기업용 클라우드 플랫폼 최강자는 누가 될까?','AWS-애저-구글, 기업용 클라우드 플랫폼 최강자는 누가 될까?',3,'user1',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'이매지네이션, 머신러닝 가속 기능 내장한 칩 디자인 소개','이매지네이션, 머신러닝 가속 기능 내장한 칩 디자인 소개',3,'user7',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'데이터베이스를 재정의하는 신기술 8가지','데이터베이스를 재정의하는 신기술 8가지',3,'user3',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'프로그래밍까지 진출한 AI··· "개발자는 데이터 과학자 돼야"','프로그래밍까지 진출한 AI··· "개발자는 데이터 과학자 돼야"',3,'admin',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'어도비 CIO가 IT 조직의 정체성을 재정의한 방법','어도비 CIO가 IT 조직의 정체성을 재정의한 방법',3,'user1',SYSDATE,NULL,NULL);

-- 아무말 대잔치
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'오늘 생일이네요','오늘 생일이네요',4,'admin',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'토익 준비해야되나요?(재취업하고싶어요.)','토익 준비해야되나요?(재취업하고싶어요.)',4,'admin',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'안드로이드 스터디는 어디서 구할 수 있을까요...?','안드로이드 스터디는 어디서 구할 수 있을까요...?',4,'user3',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'채용공고는 수습기간이 지나야 사라지나요?','채용공고는 수습기간이 지나야 사라지나요?',4,'user7',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'26살 개발자를 꿈꾸고 있습니다. 조언부탁드립니다.','26살 개발자를 꿈꾸고 있습니다. 조언부탁드립니다.',4,'user9',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'연봉협상이란게 이런거 였네요','연봉협상이란게 이런거 였네요',4,'user2',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'취준생 입니다. 조언좀 해주세요','취준생 입니다. 조언좀 해주세요',4,'user4',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'취업성공패키지 학원을 고르고있는데 팁같은게있을까요?','취업성공패키지 학원을 고르고있는데 팁같은게있을까요?',4,'user6',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'업계 선배님들은 어떻게 처음을 시작하셨나요?','업계 선배님들은 어떻게 처음을 시작하셨나요?',4,'user10',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'27살에 두번째 학원을 가려합니다','27살에 두번째 학원을 가려합니다',4,'user8',SYSDATE,NULL,NULL);

-- 프로젝트 & 소스
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'node.js의 passport-LocalStrategy에서 alert창을 띄우려고 합니다.','node.js의 passport-LocalStrategy에서 alert창을 띄우려고 합니다.',5,'user2',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'쇼핑몰 LG U+ 모바일 결제 시스템 구축하고있거든요.','쇼핑몰 LG U+ 모바일 결제 시스템 구축하고있거든요.',5,'user7',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'CHECKOUT 받을때마다 라이브러리를 따로 추가해야 해서 귀찮습니다.','CHECKOUT 받을때마다 라이브러리를 따로 추가해야 해서 귀찮습니다.',5,'user9',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'Maven 프로젝트를 Git으로 관리할 때 pom.properties 문제','Maven 프로젝트를 Git으로 관리할 때 pom.properties 문제',5,'user3',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'spring 업로드 이미지 다시 불러오기','spring 업로드 이미지 다시 불러오기',5,'user1',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'mysql/mariaDB group by 하기 전에 정렬 하는 방법','mysql/mariaDB group by 하기 전에 정렬 하는 방법',5,'user6',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'하이브리드앱과 SPA(Single Page Application)','하이브리드앱과 SPA(Single Page Application)',5,'user9',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'[css, jquery, javascript] 큰화면(div) 안에 여러 블럭(div) inline-block','[css, jquery, javascript] 큰화면(div) 안에 여러 블럭(div) inline-block',5,'user9',SYSDATE,NULL,NULL);
INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL,'CHECKOUT 받을때마다 라이브러리를 따로 추가해야 해서 귀찮습니다.','CHECKOUT 받을때마다 라이브러리를 따로 추가해야 해서 귀찮습니다.',5,'user9',SYSDATE,NULL,NULL);

--댓글 대댓글
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'1','user1',SYSDATE,1,NULL,1);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'2','user3',SYSDATE,1,NULL,2);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'3','user6',SYSDATE,1,NULL,3);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'4','user8',SYSDATE,1,NULL,4);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'5','user10',SYSDATE,1,NULL,5);

INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'6','admin',SYSDATE,1,NULL,1);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'7','admin',SYSDATE,1,NULL,2);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'8','admin',SYSDATE,1,NULL,3);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'9','admin',SYSDATE,1,NULL,4);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'10','admin',SYSDATE,1,NULL,5);

INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'11','admin',SYSDATE,1,NULL,1);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'12','admin',SYSDATE,1,NULL,2);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'13','admin',SYSDATE,1,NULL,3);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'14','admin',SYSDATE,1,NULL,4);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'15','admin',SYSDATE,1,NULL,5);

INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'16','admin',SYSDATE,1,NULL,1);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'17','admin',SYSDATE,1,NULL,2);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'18','admin',SYSDATE,1,NULL,3);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'19','admin',SYSDATE,1,NULL,4);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'20','admin',SYSDATE,1,NULL,5);

INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'21','admin',SYSDATE,1,NULL,1);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'22','admin',SYSDATE,1,NULL,2);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'23','admin',SYSDATE,1,NULL,3);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'24','admin',SYSDATE,1,NULL,4);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'25','admin',SYSDATE,1,NULL,5);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'26','admin',SYSDATE,2,1,1);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'27','admin',SYSDATE,2,1,1);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'28','admin',SYSDATE,2,1,1);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'29','admin',SYSDATE,3,26,1);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'30','admin',SYSDATE,3,26,1);

INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'31','admin',SYSDATE,2,2,2);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'32','admin',SYSDATE,2,2,2);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'33','admin',SYSDATE,2,2,2);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'34','admin',SYSDATE,3,31,2);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'35','admin',SYSDATE,3,31,2);

INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'36','admin',SYSDATE,2,3,3);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'37','admin',SYSDATE,2,3,3);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'38','admin',SYSDATE,2,3,3);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'39','admin',SYSDATE,3,36,3);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'40','admin',SYSDATE,3,36,3);

INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'41$','admin',SYSDATE,2,4,4);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'42','admin',SYSDATE,2,4,4);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'43','admin',SYSDATE,2,4,4);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'44','admin',SYSDATE,3,41,4);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'45','admin',SYSDATE,3,41,4);

INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'46','admin',SYSDATE,2,5,5);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'47','admin',SYSDATE,2,5,5);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'48','admin',SYSDATE,2,5,5);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'49','admin',SYSDATE,3,46,5);
INSERT INTO COMMENTS VALUES(COMMENTS_SEQ.NEXTVAL,'50','admin',SYSDATE,3,46,5);


--아이템무드
INSERT INTO ITEMMOOD VALUES(1,'CRAZY');
INSERT INTO ITEMMOOD VALUES(2,'HAPPY');
INSERT INTO ITEMMOOD VALUES(3,'SAD');
INSERT INTO ITEMMOOD VALUES(4,'BAD');

--신기술 주제
INSERT INTO WEEKSUBJECT VALUES
(
	NEWTECH_SEQ.NEXTVAL,'첫번째 주제 : 반장의 연애는 30대 전까지 가능하다?',SYSDATE-10
);

INSERT INTO WEEKSUBJECT VALUES
(
	NEWTECH_SEQ.NEXTVAL,'두번째 주제 : 반장의 연애는 40대 전까지 가능하다?',SYSDATE-3
);

COMMIT;
