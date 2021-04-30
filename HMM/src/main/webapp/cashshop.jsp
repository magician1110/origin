<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="resources/css/cashshop.css">
<link rel="stylesheet" href="resources/css/w3.css">
<!-- <link rel="stylesheet" type="text/css"
	href="resources/css/cashshop2.css"> -->
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>따루샵</title>
<style type="text/css">
td, tr {
	border: 1px solid black;
}
</style>
<script type="text/javascript">
								frmName = 0;
								member = '${sessionScope.member}';
									/* 스크롤 되는 메뉴 */
									$(document).ready(function () {
										var $doc = $(document);
										var position = 0;
										var top = $doc.scrollTop(); //현재 스크롤바 위치
										var screenSize = 0; // 화면크기
										var halfScreenSize = 0; // 화면의 반
										/*사용자 설정 값 시작*/
										var pageWidth = 1000; // 페이지 폭, 단위:px
										var leftOffet = 620; // 중앙에서의 폭(왼쪽 -, 오른쪽 +), 단위:px
										var leftMargin = 909; // 페이지 폭보다 화면이 작을때 옵셋, 단위:px, leftOffet과 pageWidth의 반만큼 차이가 난다.
										var speed = 1100; // 따라다닐 속도 : "slow", "normal", or "fast" or numeric(단위:msec)
										var easing = 'swing'; // 따라다니는 방법 기본 두가지 linear, swing
										var $layer = $('#floating'); // 레이어 셀렉팅
										var layerTopOffset = 550; // 레이어 높이 상한선, 단위:px
										$layer.css('z-index', 10); // 레이어 z-인덱스
										/*사용자 설정 값 끝*/
										//좌우 값을 설정하기 위한 함수
										function resetXPosition() {
											$screenSize = $('body').width(); // 화면크기
											halfScreenSize = $screenSize / 2;
											/*  / 화면의반 */
											xPosition = halfScreenSize + leftOffet;
											if ($screenSize < pageWidth)
												xPosition = leftMargin;
											$layer.css('left', xPosition);
										}
										// 스크롤 바를 내린 상태에서 리프레시 했을 경우를 위해
										if (top > 0)
											$doc.scrollTop(layerTopOffset + top);
										else
											$doc.scrollTop(0);

										// 최초 레이어가 있을 자리 세팅
										$layer.css('top', layerTopOffset);
										resetXPosition();
										//윈도우 크기 변경 이벤트가 발생하면
										$(window).resize(resetXPosition);
										//스크롤이벤트가 발생하면
										$(window).scroll(function () {
											yPosition = $doc.scrollTop() + layerTopOffset;
											$layer.animate({
												"top": yPosition
											}, {
												duration: speed,
												easing: easing,
												queue: false
											});
										});
									});

									/* 스크롤 메뉴 end */

									/* 페이지 이동  */
									$(document).ready(function () {
										$(".filter-button").click(function () {
											var value = $(this).attr('data-filter');

											if (value == "all") {
												$('.filter').show('1000');
											} else {
												$(".filter").not('.' + value).hide('3000');
												$('.filter').filter('.' + value).show('3000');

											}
										});

										if ($(".filter").removeClass("active")) {
											$(this).removeClass("active");
										}
										$(this).addClass("active");

									});

									/* 배너 마우스 오버 */
									$(function () {
										$("#flotImg").hover(function () {
											$(this).find("#banner").css('display', 'none');
											$(this).find("#test").show();
										}, function () {
											$(this).find("#banner").css('display', '');
											$(this).find("#test").hide();
										});
									});

									
									function buyModal(code) {
										if (member == null || member == '') {
											alert("로그인 후 이용해 주시기 바랍니다");
											$('#loginModal').modal('show');
										} else {
											frmName = "itemFrm" + code;
											$("#buyModal").modal('show');
										}
									}

									function purchase() {
										document.getElementById(frmName).submit();
										
									}
									function loginCheck() {
										if (member == null || member == '') {
											alert("로그인 후 이용해 주시기 바랍니다");
											$('#loginModal').modal('show');
										} else {
											location.href = "cashcharge.jsp"
										}
									}
										
										/* function delitem(){
											$('#itemitem').remove();
											
										} */
										
										
									
									
								</script>
<c:set var="ddaru" value="${member.ddaru}" scope="session" />
<c:set var="itemList" value="${list}" />
<%@ include file="/header.jsp"%>
</head>
<body id="body">

	<div id="jumbotron" class="jumbotron" style="margin-left: 20px; padding-top:0px; background-color: white;">
	<div class="w3-content w3-section" style="max-width:100%; background-color : #deeaee;">
  <img class="mySlides" src="resources/img/slider/sliderimg1.jpg" style=" height: 480px; width:99%">
  <img class="mySlides" src="resources/img/slider/sliderimg2.jpg" style="height: 480px; width:99%">
  <img class="mySlides" src="resources/img/slider/sliderimg3.jpg" style="height: 480px; width:99%">
  <img class="mySlides" src="resources/img/slider/sliderimg4.jpg" style="height: 480px; width:99%">
  <img class="mySlides" src="resources/img/slider/sliderimg5.jpg" style="height: 480px; width:99%">
  <div class="container text-center">
			<!-- <h1>Hmm CashShop</h1>
			<p style="font-size: 30px;">Cash shop</p> -->
		</div>
	</div>
</div>
	<script type="text/javascript">
	var slideIndex = 0;
	carousel();
	function carousel() {
	    var i;
	    var x = document.getElementsByClassName("mySlides");
	    for (i = 0; i < x.length; i++) {
	      x[i].style.display = "none"; 
	    }
	    slideIndex++;
	    if (slideIndex > x.length) {slideIndex = 1} 
	    x[slideIndex-1].style.display = "block"; 
	    setTimeout(carousel, 2000); // Change image every 2 seconds
	}
	</script>
		

	<div class="container">
		<div id="residualcash" style="margin: 0px;">
			<p style="text-align: center; width: 100%;">
				<b>남은 따루 : </b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
					id="cash" name="cash" type="text" value="${ddaru}  따루    " readonly>
				<button id="myitem" class="w3-button w3-round-xlarge w3-black"
					data-toggle="modal" data-target="#myitemes">내 아이템보기</button>
			</p>
		</div>
		<div class="row">
			<div class="col-sm-3">
				<div id="sidebar" class="left-sidebar">
					<h2 style="padding : 20px;">카테고리</h2>
					<div class="panel-group category-products" id="accordian">
						<!--category-productsr-->
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a class="filter-button" data-filter="all" style="cursor:pointer">ALL</a>

								</h4>
							</div>
						</div>
							<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordian"
										href="#level" class="collapsed filter-button"
										data-filter="LEVEL"> LEVELMEDAL </a>
								</h4>
							</div>
							<div id="medal" class="panel-collapse collapse"></div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordian"
										href="#emoticon" class="collapsed filter-button"
										data-filter="BAD&HAPPY&SAD&CRAZY"> <span
										class="badge pull-right"> <i class="fa fa-plus"></i>
									</span> <b>이모티콘</b>
									</a>
								</h4>
							</div>
							<div id="emoticon" class="panel-collapse collapse"
								style="height: 0px;">
								<div class="panel-body">
									<ul>
										<li><a class="filter-button" data-filter="BAD" style="cursor:pointer">Bad</a>
										</li>
										<li><a class="filter-button" data-filter="HAPPY" style="cursor:pointer">Happy</a>
										</li>
										<li><a class="filter-button" data-filter="SAD" style="cursor:pointer">Sad</a>
										</li>
										<li><a class="filter-button" data-filter="CRAZY" style="cursor:pointer">Crazy</a>

										</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordian" href="#edge"
										class="collapsed filter-button" data-filter="BORDER">
										<b>테두리</b> </a>
								</h4>
							</div>
							<div id="edge" class="panel-collapse collapse"></div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordian"
										href="#medal" class="collapsed filter-button"
										data-filter="MEDAL"> <b>메달</b> </a>
								</h4>
							</div>
							<div id="medal" class="panel-collapse collapse"></div>
						</div>
					</div>
					<!--/category-products-->

					<div class="shipping text-center">
						<!--shipping-->

					</div>
					<!--/shipping-->

				</div>
			</div>

			<div class="col-sm-9 padding-right">
				<div id="items" class="items">
					<!--features_items-->
					<h2 class="title text-center">아이템</h2>

					<!--------------------------------------------------emticon---------------------------------------------------------------------------- -->
					<c:forEach var="l" items="${itemList}">
						<div class="col-sm-4 filter emoticon ${l.mood}">
							<div class="product-image-wrapper" style="heigth : 10px; margin-bottom: 27px; margin-top : 0;">
								<div class="single-products">
									<div class="productinfo text-center">
										 <br><img
											style="width: 170px; height: 150px;" src="${l.filelink}">
										<form id="itemFrm${l.itemcode}" action="itemPurchase.do"
											method="POST">
											<input type="hidden" value="${l.itemcode}" name="itemcode">
										</form>
										<br>
									</div>
									<div class="product-overlay">
										<div class="overlay-content">
											<h2>${l.price}따루</h2>
											<a href="#" class="btn btn-sdefault add-to-cart"
												onclick="buyModal(${l.itemcode})"> <i
												class="fa fa-shopping-cart"></i>구매하기
											</a>
										</div>
									</div>
								</div>
								<div class="choose"></div>
							</div>
						</div>
					</c:forEach>
				</div>
				<!--features_items-->
				<div class="category-tab">
					<!--category-tab-->
				</div>
				<!--/category-tab-->
				<div class="recommended_items">
					<!--recommended_items-->
				</div>
				<!--/recommended_items-->

			</div>
		</div>
	</div>

	<!--구매 Modal -->
	<div class="modal fade" id="buyModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">구매하기</h4>
				</div>
				<div class="modal-body">정말로 구매 하시겠습니까 ?</div>
				<div class="modal-footer">
					<button id="thisbuy" type="button" data-dismiss="modal"
						onclick="purchase()">결제하기</button>
					<button type="button" class="cancelbtn" data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 아이템 보기 -->
	<div class="modal modal-center fade" id="myitemes" tabindex="-1"
		role="dialog" aria-labelledby="my80sizeCenterModalLabel">
		<div class="modal-dialog modal-80size modal-center" role="document"
			style="height: auto; width: 1300px;">
			<div id="myitemModal" class="modal-content modal-80size">
				<div class="modal-header">
					<h4 id="itemtitle" class="modal-title" id="myModalLabel">내 아이템</h4>
				</div>
				<div id="itemheight" class="modal-body" style="width: 1200px;">
					<table id="itemtable"
						style="margin-left: 100px; width: 900px; height: auto; padding: 0px;">
						<c:if test="${pList ne null }">
							<c:forEach var="pt" items="${pList}">
								<tr>
									<td colspan='2'></td>
									<td id="itemitem"><span style='float: right; margin: 10px;'>
											<button id='imagedel' class='close' type='button'
												style='color: black;' onclick="delitem();">&times;</button>
									</span> <a id='itemdetail' href='#itdetail' data-toggle='modal'> <img
											src='${pt.filelink}'></a> <br> <br>사용기한 :
										${pt.usagedate}</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>
				<div id="myitemfooter" class="modal-footer">
					<button type="button" class="cancelbtn" data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>

<!--스크롤 배너 -->

<div id="floating" style="cursor: pointer;">
	<a id="flotImg" onclick="loginCheck()" style="text-decoration: none;">
		<img id="banner"
		src="resources/img/icon/cash3.png"
		border="0" width="200px" height="130px">
		<div id="test" style="display: none; width: 200px; height: 130px; font-size: 30px; text-align: center;">
			<br>따루 충전하러<br> 가기~
		</div>
	</a>

</div>
<%@ include file="/footer.jsp"%>
</html>
