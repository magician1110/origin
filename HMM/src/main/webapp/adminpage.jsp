<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="resources/img/신보선/신보선.jpg" type="image/gif" sizes="16x16">
  <link href="resources/css/adminpage.css" rel="stylesheet" type="text/css">
	<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
  <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
  <link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
  <script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>
<title>관리자 페이지</title>
</head>

</head>
<body>
<%@ include file="/header.jsp"%>
					<div class="jumbotron jumbotron-billboard">
		<div class="img"></div>
		<div class="container">
			<h1>Hmm...!</h1>
			<p id="demo"></p>
		</div>
	</div>
  <div class="container">
		<h4 align="left" style="text-align: right; padding-right: 5%; color: black; font-style: inherit; font-size: medium;">
		모든 메시지 : ${slsize}건 &nbsp; 읽은 메시지 : <span id="yy">${requestScope.yCnt}</span>건
		&nbsp; 읽지 않은 메시지 : <span id="nn">${requestScope.nCnt}</span>건</h4>
		<div class="board-body">
					<!-- 게시판 테이블 -->
					<div class="hmm_table">
						<table id="myTable" style="background: white;">
							<thead>
								<tr style="color: white;">
									<th>아이디</th>
									<th>이메일주소</th>
									<th>제목</th>
									<th>작성일자</th>
									<th>읽음여부</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="s" items="${slist}" varStatus="status">
							<tr>
							<td id="table_id" style="text-align: center;" name="test_check">${s.sername}</td>
							<td id="table_email" style="text-align: center;" name="test_check">${s.seradd}</td>
							<td id="td_title" style="text-align: center;" name="test_check">
							<input type="checkbox" name = "test_check" id="checkbox${status.index}"  style="float: left;" value="${status.index}" />
							<a onclick="hi(${status.index})">${s.sertitle}</a></td>
							<td style="text-align: center;" name="test_check">${s.serdate}</td>
							<td style="text-align: center;" id = "scheck${status.index}" name="test_check">${s.yncheck}</td>
							</tr>
							<tr>
							<td style="text-align: center; border: 1px #618685 solid; padding: 80px;" name="test_check" colspan="5" id="bwhidden${status.index}" class="hidden">${s.sercontent}</td>
							</tr>
							</c:forEach>

					<%-- 페이지 처리 --%>
				 	<tr align="center" height="20">
						<td colspan="8" style="text-align: center; font-size: large;" ><c:if test="${currentPage <= 1 }">&laquo;</c:if>
						<c:if test="${currentPage != 1}">
							<a href="adminlist.do?page=${currentPage-1 }" class="w3-button">&laquo;</a>
						</c:if>
						<c:forEach var="page" begin="${startPage}" end="${endPage}">
						<c:if test="${page eq currentPage}">
							<a href="#" class="w3-button">${page}</a></c:if>
						<c:if test="${page != currentPage}">
							<a href="adminlist.do?page=${page}" class="w3-button">${page}</a>
						</c:if>
						</c:forEach>
						<c:if test="${currentPage >= maxPage}">&raquo;</c:if>
						<c:if test="${currentPage != maxPage}">
							<a href="adminlist.do?page=${currentPage+1}" class="w3-button">&raquo;</a>
						</c:if></td>
					</tr>

				</tbody>
						</table>
						<br>
						<div id = "selectAll2" style="text-align: right; padding-right: 2%">
						<button onclick="checkAll(${slsize})" class="button5" id="select_deselect_btn">전체 선택 & 해제</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button onclick="ch()" class="button5" id="delete_btn">체크 된 목록 삭제</button>
						</div>
			</div>

      <a id="insertNewtech" href="newtechAdmin.do">신기술 찬/반 투표 주제 넣기</a>

				</div>

        </div>
				<script type="text/javascript">

				//타이틀 클릭시 히든 부분
					function hi(index){
					 	if($('#bwhidden' + index).attr('class')==""){
							$('#bwhidden' + index).attr('class','hidden')

						}
						 else{
								$('#bwhidden' + index).attr('class','');
							}

						$.ajax({
						url: 'servicecheck.do',
						type: 'GET',
						data: {"valueArrTest":index },

						success:function(data){
							if(data=="yes"){
								if($('td[id="scheck'+index+'"]').html() == 'N'){
									if($('#yy').html() == "<c:out value='${slsize}'/>"){
										$('#yy').html("<c:out value='${slsize}'/>");
									} else {
										$('#yy').html(Number($('#yy').html())+1);
									}
									if(Number($('#nn').html()) > 1){
					           			$('#nn').html(Number($('#nn').html())-1);
									} else {
										$('#nn').html(0);
									}
								}
				           		$('td[id="scheck'+index+'"]').html('읽음');
							}
							},
			            error:function(data){
			            	alert("체크 할 목록을 입력하세요");
			             }
					})

						}


					function ch(s){
						var checkArr = [];
						$("input[name='test_check']:checked").each(function(){
							checkArr.push($(this).val()); //체크된것만 뽑아서 배열에
						});
						alert("삭제 되었습니다!!");

						 location.href='checkboxlist.do?valueArrTest='+checkArr;

						  $.ajax({
				            type : "GET",
				            url : 'checkboxlist.do?valueArrTest='+checkArr,
				           	success:function(){

				           		location.href="adminlist.do";
				           	},
				            error:function(request,status,error){
				            	alert("실패");
				             }
				    	});

					}

					function checkAll(size){


					console.log(($('#checkbox' + 0).prop("checked")));
					if(!$('#checkbox' + 0).prop("checked")){


					for(var i = 0; i < size; i++){
						$("#checkbox" + i).prop("checked",true);
					}

					}else{
						for(var i = 0; i < size; i++){
							$("#checkbox" + i).prop("checked",false);
						}
					}
					}

			</script>

</body>
</html>
