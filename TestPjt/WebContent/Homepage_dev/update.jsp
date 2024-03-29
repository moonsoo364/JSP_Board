<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Board_module.BoardBean" %>
<!DOCTYPE html>
<html lang="kr">
<head>
    <link href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400" rel="stylesheet">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>수정하기</title>
   <link rel="stylesheet" href="css/style_index.css">
    <link rel="stylesheet" href="css/style_post.css">
    <script src="https://use.fontawesome.com/releases/v5.15.1/js/all.js" ></script>
    <script src="javascript/main.js" defer></script>
    <script src="javascript/update.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap" rel="stylesheet">
</head>
<body>
<%!String Idkey=null; %>
<%
if(session.getAttribute("key")!=null){
	Idkey=(String)session.getAttribute("key");
	System.out.printf("Id=%s 세션이 update.jsp에 로드되었습니다\n",Idkey);
	
}else{
	Idkey=null;
}


int num =Integer.parseInt(request.getParameter("num"));
String nowPage =request.getParameter("nowPage");
BoardBean bean=(BoardBean)session.getAttribute("bean");
String title =bean.getTitle();
String content =bean.getContent();



%>
<!-- navbar는 모든 페이지에 적용 됩니다.-->
    <nav class="navbar">
            <div class="navbar_title">
                <i class="fas fa-chalkboard-teacher"></i>
                <a href="Index.jsp">Hello IT World!</a>
            </div>

            <ul class="navbar_menu">
                <li><a href="Introduce.jsp">Introduce</a></li>
                <li><a href="list.jsp">Board</a></li>
                    
            </ul>
            <%if(Idkey == null) { %>
  			
  			<ul class="navbar_reg">
                <li><i class="fas fa-sign-in-alt"></i><a href="login.jsp">&nbsp;login</a></li> 
                <li><i class="fas fa-registered"></i><a href="member.jsp" target="_sub">&nbsp;register</a></li>
            </ul>
       
        <%} else { %> 
        <div id="login_box">
  			
  			<div><i class="far fa-user-circle fa-3x" id="user_icon"></i></div>
  			<div id="log_ment">&nbsp;&nbsp;안녕하세요! <%=Idkey %>님<br>&nbsp;&nbsp;오늘도 즐거운 하루 되세요.
  			<input type="button" value="로그아웃" onclick="javascript:location.href='logout.jsp'">
  			</div>
  			
        </div>
        	
        
       <%}%> 

            <a href="#" class="navbar_togle">
                <i class="fas fa-sliders-h"></i>
            </a>
    </nav>
    
    <div class="container" >
		<h2 class="post">수정하기</h2>
		<form name="updateform" method="post" action="BoardUpdate">
		
		<table class= "content_table">
				
				<tr>
					<td class="text_left" >제목</td>
					<td style="text-align:left;"><input id="title_box" name="title" size="21" maxlength="50"></td>
				</tr>
				
				<tr>
				<td class="text_left">
					분류
				</td>
				<td style="text-align:left;">
    				<select name="sort" id="box_font">
							<option value="공지">공지사항</option>
							<option value="잡담">잡담</option>
							<option value="리뷰">리뷰</option>
							<option value="이벤트">이벤트</option>
    			   </select>
    			</td>
    			</tr>
				<tr>
					<td class="text_left" >내용</td>
					<td style="text-align:left;"><textarea name="content" rows="10" cols="40" ></textarea></td>
				</tr>
				
				
				<tr>
					<td colspan="2"  >
						<input id="box_font" type="button" value="등록"onclick="javascript:writeCheck();">&nbsp;&nbsp;
						<input id="box_font" type="button" value="목록" onclick="javascript:location.href='list.jsp'">
					</td>
				</tr>
		
		
			</table>
			<input type="hidden" name="ip" value="<%=request.getRemoteAddr() %>">
			<input type="hidden" name="id" value="<%=Idkey %>">
			<input type="hidden" name="nowPage" value="<%=nowPage %>">
		</form>
	</div>
   <!-- footer는 모든 페이지에 적용 됩니다.-->
	<footer class="footer_style">
		<div class="footer_alarm">
			<div class="div_left"><i class="fas fa-exclamation-triangle"></i></div>
			<div class="div_right">
				<div class="footer_ment1">해당 게시판은 컴퓨터,모바일,하드웨어,소프트웨어 등의 뉴스와 정보를 공유하는 곳입니다
				<br>게시판에서 생기는 개인 및 단체의 권리 침해 문의는 aptx15hibr@naver.com로 해주세요.
				</div>
	 			
	 		</div>			
		</div>
	</footer>
</body>
</html>