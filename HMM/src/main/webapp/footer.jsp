<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Hmm | 전 세계의 개발자들을 널리 이롭게 하리라.</title>
		<meta charset="utf-8">
			<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
				<link href="resources/css/footer.css?ver=1" rel="stylesheet" type="text/css">
					<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
					<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
				</head>
				<body>

					<!-- Container (연락처) -->
					<div id="contact" class="container-fluid bg-grey">
						<h2 class="text-center">Hmm 연락처</h2>
						<div class="row">
							<div class="col-sm-5">
								<p>전문 상담원이 24시간 항시 대기하고 있습니다.</p>
								<p>
									<span class="glyphicon glyphicon-map-marker"></span>
									서울시 강남구</p>
								<p>
									<span class="glyphicon glyphicon-phone"></span>
									+82 01082986600</p>
								<p>
									<span class="glyphicon glyphicon-envelope"></span>
									jhlee90909@gmail.com</p>
							</div>
							<form action="bwlist.do" method="POST"  onSubmit="alert('성공적으로 운영자에게 메시지 전달하였습니다!');">

							<div class="col-sm-7 slideanim">
								<div class="row">
									<div class="col-sm-6 form-group">
									<c:if test="${member eq null }">
										<input class="form-control" id="name" name="sername" placeholder="성함" type="text" required>
										</c:if>
										<c:if test="${member ne null }">
										<input class="form-control" id="name" name="sername" value="${member.id}" placeholder="성함" type="text" readonly="readonly">
										</c:if>
										</div>
										<!-- <div class="col-sm-6 form-group">
											<input class="form-control" id="email" name="seradd" value="wkdgma91@gmail.com" placeholder="이메일 주소" type="email" readonly="readonly">
											</div> -->
										</div>
										<input class="form-control" id="title" name="sertitle" placeholder="제목" type="text" style="width: 47%; margin-right:5%; float:left;" required>
										<input class="form-control" id="email" name="seradd" value="wkdgma91@gmail.com" style="width: 47%;" placeholder="이메일 주소" type="email" readonly="readonly">
										<br>
										<textarea class="form-control" id="comments" name="sercontent" placeholder="남기실 말씀" rows="5" style="clear:both;" required></textarea>
										<br>
											<div class="row">
												<div class="col-sm-12 form-group">
													 <button id="footer_submit" class="btn btn-default pull-right" type="submit">전송하기</button>
												</div>
											</div>
										</div>
										</form>
									</div>
								</div>

								<!-- 구글 맵 추가 -->
								<div id="googleMap" style="height:400px;width:100%;"></div>

								<script>
									function myMap() {
										var myCenter = new google.maps.LatLng(37.498993, 127.032909);
										var mapProp = {
											center: myCenter,
											zoom: 15,
											scrollwheel: false,
											draggable: false,
											mapTypeId: google.maps.MapTypeId.ROADMAP
										};
										var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
										var marker = new google.maps.Marker({
											position: myCenter,
											// 구글맵 마커 통통튀는 애니메이션
											animation: google.maps.Animation.BOUNCE
										});
										marker.setMap(map);

										// 구글맵 마커 정보
										var infowindow = new google.maps.InfoWindow({content: "Hmm 본사는 바로 이 곳에 위치해 있습니다!"});
										infowindow.open(map, marker);
									}
								</script>
								<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCgwD8ZjMQqq0qJyRPf3Lfb7-DfrbjV6Js&callback=myMap"></script>

								<div id="footer_end">
									<a class="up-arrow" href="#myPage" data-toggle="tooltip" title="홈페이지 위로 이동하기">
									    <span class="glyphicon glyphicon-chevron-up"></span>
									  </a>
								</div>
							</body>
						</html>
