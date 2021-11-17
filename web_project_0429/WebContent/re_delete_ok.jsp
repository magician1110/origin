<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="board.*"%>

<jsp:useBean id="dao" class="board.DAO" />
<jsp:useBean id="vo1" class="board.VO" />
<%
	int idx = Integer.parseInt(request.getParameter("idx"));
	int pg = Integer.parseInt(request.getParameter("pg"));
	VO vo = dao.getView(idx);
%>
<script language="javascript">
		<%
			String savePath = "D:/myjava_10gi/javaee_workspace_web/web_project_0429/WebContent/upload";
			File file = new File(savePath + "/" + vo.getFiles());
			System.out.println(savePath + "/" + vo.getFiles());
			file.delete();
			new DAO().filedel(Integer.parseInt(request.getParameter("idx")));
		%>
			alert('첨부파일이 삭제 되었습니다');
			window.location='modify.jsp?idx=<%=idx%>&pg=<%=pg%>';
</script>