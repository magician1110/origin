<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="board.*"%>

<jsp:useBean id="dao" class="board.DAO" />
<jsp:useBean id="vo1" class="board.VO" />
<%
	int idx = Integer.parseInt(request.getParameter("idx"));
	int pg = Integer.parseInt(request.getParameter("pg"));
	VO vo = dao.getView(idx);
%>

<script language="javascript">

	function modifyCheck() {
		var form = document.modifyform;

		if (!form.password.value) {
			alert("비밀번호를 적어주세요");
			form.password.focus();
			return;
		}

		if (!form.title.value) {
			alert("제목을 적어주세요");
			form.title.focus();
			return;
		}

		if (!form.memo.value) {
			alert("내용을 적어주세요");
			form.memo.focus();
			return;
		}
		form.submit();
	}
	
	function del() {
		window.location='re_delete_ok.jsp?idx=<%=idx%>&pg=<%=pg%>';
		<%-- dao.checkPassword(vo, idx);
		if(ch==false) {
				self.window.alert("비밀번호를 틀렸습니다.");
				location.href="javascript:history.back()";
		}else{
		window.location='re_delete_ok.jsp?idx=<%=idx%>&pg=<%=pg%>';
	} --%>
	}
</script>

<!DOCTYPE html>
<html>
<head>
<title>게시판</title>
</head>
<body>
	<table>
		<form name="modifyform" method="post" action="modify_ok.jsp?idx=<%=idx%>&pg=<%=pg%>" enctype="multipart/form-data">
			<tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr
							style="background: url('img/table_mid.gif') repeat-x; text-align: center;">
							<td width="5"><img src="img/table_left.gif" width="5"
								height="30" /></td>
							<td>수정</td>
							<td width="5"><img src="img/table_right.gif" width="5"
								height="30" /></td>
						</tr>
					</table>
					<table>
						<tr>
							<td>&nbsp;</td>
							<td align="center">제목</td>
							<td><input type=text name=title size=50 maxlength=50
								value="<%=vo.getTitle()%>"></td>
							<td>&nbsp;</td>
						</tr>
						<tr height="1" bgcolor="#dddddd">
							<td colspan="4"></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="center">이름</td>
							<td><%=vo.getName()%><input type=hidden name=name size=50
								maxlength=50 value="<%=vo.getName()%>"></td>
							<td>&nbsp;</td>
						</tr>
						<tr height="1" bgcolor="#dddddd">
							<td colspan="4"></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="center">비밀번호</td>
							<td><input type=password name="password" id="pass" size=50
								maxlength=50></td>
							<td>&nbsp;</td>
						</tr>
						<tr height="1" bgcolor="#dddddd">
							<td colspan="4"></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="center">내용</td>
							<td><textarea name=memo cols=50 rows=13><%=vo.getMemo()%></textarea></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<%
								if (vo.getFiles() == null) {
							%>
							<td>&nbsp;</td>
							<td align="center">파일 첨부</td>
							<td><input type="file" name="files" cols="50" rows="13"></td>
							<td>&nbsp;</td>
						</tr>
						<%
							} else {
						%>
						
						<tr height="1" bgcolor="#dddddd">
							<td colspan="4" width="407"></td>
						</tr>
						<tr>
							<td width="0">&nbsp;</td>
							<td align="center" width="76">첨부파일</td>
							<td width="319"><a	href="/web_project_0429/upload/<%=vo.getFiles()%>" target="blank"><%=vo.getFiles()%></a> &nbsp;
							<input type="button" value="삭제" onclick="del();"></td>
							<td width="0">&nbsp;</td>
							<% } %>
						</tr>
						<tr height="1" bgcolor="#dddddd">
							<td colspan="4"></td>
						</tr>
						<tr height="1" bgcolor="#82B5DF">
							<td colspan="4"></td>
						</tr>
						<tr align="center">
							<td>&nbsp;</td>
							<td colspan="2">
							<input type="button" value="수정" OnClick="javascript:modifyCheck();">
							<input type=button value="취소" OnClick="javascript:history.back(-1);">
							<td>&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
		</form>
	</table>
</body>
</html>