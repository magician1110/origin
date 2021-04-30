<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<c:set var="member" value="${member}" scope="session" />
	<%
		System.out.println("홈 페이지 세션 값 : " + session.getAttribute("member"));
	%>
	<c:choose>
		<c:when test="${null ne member }">
			<c:redirect url="/" />
		</c:when>
		<c:when test="${null eq member }">
			<c:redirect url="/" />
		</c:when>
	</c:choose>
</body>
</html>
