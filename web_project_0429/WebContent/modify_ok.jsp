<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>
<jsp:useBean id="dao" class="board.DAO"/>
<jsp:useBean id="vo" class="board.VO"/>

			
<%	
	request.setCharacterEncoding("UTF-8");
   String name=null;
   String title=null;
   String memo=null;
   String password=null;
   String files=null;
   
	
   String savePath = "D:/myjava_10gi/javaee_workspace_web/web_project_0429/WebContent/upload";
	// 최대 업로드 파일 크기 5MB로 제한
	int uploadFileSizeLimit = 5 * 1024 * 1024;
	ServletContext context = getServletContext();
	String uploadFilePath = savePath;
	System.out.println("서블릿 연결됨");
	System.out.println("서버상의 실제 디렉토리 :");
	System.out.println(uploadFilePath);
	
	try {
		MultipartRequest multi = new MultipartRequest(request,  // request 객체
				uploadFilePath, // 서버상의 실제 디렉토리
				uploadFileSizeLimit, // 최대 업로드 파일 크기
				// 동일한 이름이 존재하면 새로운 이름이 부여됨
				new DefaultFileRenamePolicy());
		// 업로드된 파일의 이름 얻기
		String fileName = multi.getFilesystemName("files");
		
		if (fileName == null) { // 파일이 업로드 되지 않았을때
			System.out.print("파일 업로드 되지 않았음");
			name=multi.getParameter("name");
			title=multi.getParameter("title");
			memo=multi.getParameter("memo");
			password=multi.getParameter("password");
		} else if(fileName!=null) { // 파일이 업로드 되었을때
			out.println("<br> 글쓴이 : " + multi.getParameter("name"));
			out.println("<br> 제 &nbsp; 목 : " + multi.getParameter("title"));
			out.println("<br> 파일명 : " + fileName);
			name=multi.getParameter("name");
			title=multi.getParameter("title");
			memo=multi.getParameter("memo");
			password=multi.getParameter("password");
			files=fileName;
			System.out.println("<br> 글쓴이 : " + name);
			System.out.println("<br> 제 &nbsp; 목 : " + multi.getParameter("title"));
			System.out.println("<br> 파일명 : " + fileName);
			
		}// else
	} catch (Exception e) {
		System.out.print("예외 발생 : " + e);
	}// catch
%>
<jsp:setProperty name="vo" property="name" value="<%=name %>"/>
<jsp:setProperty name="vo" property="title" value="<%=title %>"/>
<jsp:setProperty name="vo" property="memo" value="<%=memo %>"/>
<jsp:setProperty name="vo" property="password" value="<%=password %>"/>
<jsp:setProperty name="vo" property="files" value="<%=files %>"/>
<%
	int idx = Integer.parseInt(request.getParameter("idx"));
	int pg = Integer.parseInt(request.getParameter("pg"));
	boolean ch = dao.checkPassword(vo, idx);
	if(ch) {
			dao.modify(vo, idx);
		%>
			<script language=javascript>
				self.window.alert("글이 수정되었습니다.");
				location.href="view.jsp?idx=<%=idx%>&pg=<%=pg%>";
			</script>
		<%
	} else {
		%>
			<script language=javascript>
				self.window.alert("비밀번호를 틀렸습니다.");
				location.href="javascript:history.back()";
			</script>
		<%
	}
%>
