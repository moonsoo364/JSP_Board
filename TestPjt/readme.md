IT 기기 정보를 공유하는 커뮤니티 게시판 만들기(2021-10-12에 완성됨)

개발 환경 : Html,CSS,JAVA,JSP,TOMCAT,Mysql
사용한 JAVA Library : Mysql-Connector-java

페이지 설명
1.Index.jsp : 일상에서 자주 사용하는 전자기기를 표지로 사용했음 
2.login.jsp : id,password를 입력한 후 로그인을 함 로그인을 하면 Login,Register 버튼이 사라지고 '안녕하세요! (ID)님 오늘도 좋은 하루되세요'로 바뀜
		
3.Introduce.jsp : 게시판을 만든 설명이 들어가는 부분, 상단에 제목으로 '정보를 나누세요!'(하얀색)을 적고 하늘색 사선으로 애니메이션 효과를 줬음
4.member.jsp : 회원가입 페이지이다. 
 - id,비밀번호,비밀번호 확인,이름,성별,생년월일,email,주소,상세주소 항목이 들어간다.
 - ID값은 DB에서 Primary Key로 지정했다 그래서 중복확인 버튼을 넣어줌(idCheck.jsp에서 Select문으로 DB에 해당 ID값이 있는 지 확인한다.)
 - check.js : 회원가입 형식에 맞게 DB에 담기도록 예외처리한 js
 - isbirthday(): 생년월일 6자리를 2자리식 자른다, 
	- month가 4,6,9,11이고 day=31일 경우 alert("4,6,9,11월 달은 30일 까지 있습니다"); 예외처리 
	- month가 2인데 29일보다 크거나 leap(윤년)이 아니고 29일 경우  alert("2월달은 28,29일 까지 있습니다");로 예외처리
 - checkId(id),checkPassword(id, pwd, repwd) : 아이디와 비밀번호를 영대소문자와 숫자로 구성되게 정규식 지정해줌, 비밀번호와 비밀번호 확인
 	- ex)var pwdRegExp = /^[a-zA-z0-9]{8,16}$/; //비밀번호를 대소문자와 숫자 8~16자리로 비밀번호를 설정함 
 	- checkEmail(email) : 이메일 형식을 정규 표현식으로 설정한 함수 
 - 이메일 정규표현식 : /^[A-Za-z0-9\-_]+@[A-Za-z0-9\-_]+\.[A-Za-z0-9\-]+/;
 	- '/' 안에 있는 내용은 정규표현식 검증에 사용되는 패턴이 이 안에 위치함
 	- '^' 표시는 처음 시작하는 부분부터 일치한다는 표시이다.
 	- '[0-9a-zA-Z\-_]' 하나의 문자가 []안에 위치한 규칙을 따른다는 뜻이다,
  	- 0-9a-zA-Z는 숫자와 영어 대소문자를 사용한다는 뜻이고 _는 언더바,하이픈 앞에 백슬래쉬(\)를 붙이는 이유는하이픈이 범위 지정 기호로 쓰이기 때문에 이스케이프 처리를 해서 지정기호가 아닌 별개의 문자로 처리 하게끔한다.
   	- +표시는 그 조건에 해당하는 문자열이 하나는 있어야함을 의미합니다.
	- '+@' 골뱅이는 첫번째 문자와 두번째 문자사이에 항상 있어야한다.
 	- '\.' .은 두번째 문자와 세번째 문자 사이에 항상있어야한다.  
		
- checkAddress(address),checkDetailaddress(address) : 주소와 상세주소란이 공백인지 확인, 주소는 다음 api를 이용해서 구현해서 따로 정규 표현식으로 제한하지 않았음
		
5. list.jsp : 게시판 페이지
- 게시글 총 개수,현재 게시글 페이지를 표시한다 ex)게시글 수 : 4 게시판 페이지 : 5페이지 중 1페이지 보는 중
- 게시글이 없으면 '등록된 게시글이 없습니다.' 라고 표시
- 번호,분류,제목,작성자,조회수를 Colomn으로 테이블로서 글을 생성한다.
- 게시글을 누르면 get방식으로 해당글 페이지로 들어간다.
- 로그인 하면 글쓰기 버튼이 보이고 작성이 가능하다.(post.jsp로 넘어감)
- post.jsp: 글쓰기 페이지 제목,분류,내용으로 글을 작성할 수 있다.
- 현재 로그인된 아이디와 글을 작성한 사람의 id가 일치하면 글페이지에 들어갔을 때 삭제와 수정이 가능하다.
- list.jsp 페이지에 게시글은 10개까지 나오고 10개 이후의 게시글은 목록 하단에 숫자 버튼을 클릭하면 다음페이지에서 글을 확인할 수 있다
- 검색 창에는 글 제목,글 내용,작성자로 분류해서 글 찾기 기능을 사용 가능하다.

6. 데이터 베이스
   - Board_module : 게시판에 사용될 Java 패키지
   - BoardBean : 게시판 테이블에 있는 Column을 변수화한 Bean Class
   - BoardMgr : 게시판 기능을 구현하기 위해 sql문을 함수화한 Class
   	- getBoardList(keyfield,keyword,start,end) : 게시글을 읽어오는 sql 함수,keyfield는 글제목,글내용,작성자 중 하나가 들어가고 keyword는 검색창에 입력한 값이 들어감 start,end는 한 페이지에 담기는 글 갯수(0,10)
   	- getTotalCount(keyfield,keyword) : DB에 담긴 게시글 갯수를 세는 SQL함수,keyword가 비어 있을 때 DB 전체 게시글을 출력해주고 keyword가 있을 때는 where 'keyfield' like keyword 문으로 찾기한 게시글 수를 센다.
   	- insertBoard(HttpServletRequest) : 작성한 게시글을 DB에 입력하는 함수 post.jsp에서 글을 작성하면 id,글종류,제목,내용,ip 값을 BoardPostServlet.java에서 post 방식으로 값을 받고 insertBoard()함수에서 request로 그 값들을 읽고 insert문으로 DB에 입력한다.
   	- getBoard(int num) : 게시글을 누르면 해당하는 번호에 맞는 글을 DB에서 가지고 오는 함수, 글쓴이,등록일자,제목,글내용을 read.jsp에 표시해준다.
   	- upCount(int num) : 게시글을 읽으면 조회수를 올려주는 함수, 글번호를 기준으로 조회수를 1씩 올려준다.
   	- deleteBoard(int num) : 게시글을 삭제하는 함수, 해당하는 글번호에 글을 지운다.
   	- updateBoard(Board bean) : 게시글을 수정하는 함수, 제목,글내용,글분류,작성 시간을 수정해준다.
   - DBConnectionMgr : 사용할 mysql 정보가 담기는 Class
   - Homepage_module : 로그인,회원가입에 사용될 Java 패키지
   	- MemberBean : 회원 테이블에 있는 Colomn을 변수화한 Bean Class
   	- MemberMng :  회원가입,로그인을 구현하기 위해 sql문을 함수화한 Class
   	- checkId(String id) : 회원 가입할 때 해당하는 id가 DB의 id에 있는 값과 중복되는 지 확인하는 함수
   	- insertMember(MemberBean bean) : 회원가입 페이지(member.jsp)에서 기입한 내용을 DB에 입력해주는 함수
   	- loginMember(String id, String pwd) : 로그인 할 때 아이디,비밀번호에 일치하는 데이터가 있는 지 확인하는 함수
   - DBConnectionMgr : 사용할 mysql 정보가 담기는 Class
					
					
					
				 
				
					
			
