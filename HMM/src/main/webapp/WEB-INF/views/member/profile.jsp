<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1">
				<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
					<link href="resources/css/profile.css" rel="stylesheet" type="text/css">
						<script type="text/javascript">
						$(function(){

							$.ajax({
											type : "POST",
											url : "leveling.do?exp=${writer.exp}",
											success : function(data) {

												$('#lev').val(data.level);
												$('#per').val(data.percent);
											},
											error:function(request,status,error){
													alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
												 }
								});
});
						</script>
						<title>프로필 상세보기</title>
					</head>
					<body>
						<%@ include file="/header.jsp"%>
						<div class="container">
							<div class="profile_area banner">

								<%-- 프로필 사진 공간 --%>
								<div class="profile_area profile_img">

									<c:choose>
										<c:when test="${null eq pInfo.photo}">
											<img id="profile_user_img" src="resources/img/defaultImg.jpg"/>
										</c:when>
										<c:when test="${null ne pInfo.photo}">
											<img id="profile_user_img" src="${pInfo.photo}"/>
										</c:when>
									</c:choose>

								</div>

								<div class="profile_id">

								<h1>${pInfo.id}</h1>
								<h4>회원 가입일 : ${pInfo.enrolldate}</h4>
								<h4>이메일 : ${pInfo.email}</h4>
								<br>
								<h4>경험치 : ${pInfo.exp}</h4>
								<br>
								<h4>수여받은 메달 갯수 : ${pInfo.medal}</h4>
								<h4>신고당한 횟수 : ${pInfo.report}</h4>
								<h4>작성한 글 갯수 : ${pInfo.boardCount}</h4>
								<h4>작성한 댓글 갯수 : ${pInfo.commentsCount}</h4>
								</div>
							</div>
						</div>

					</body>
				</html>
