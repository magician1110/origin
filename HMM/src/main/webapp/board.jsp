<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:useBean id="today" class="java.util.Date" />

<!DOCTYPE html>
<html>
<head>
<title>Hmm 게시판</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- index.css 사용가능? --%>
<link href="resources/css/index.css" rel="stylesheet" type="text/css">
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css"
	rel="stylesheet">
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
<link
	href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css"
	rel="stylesheet">
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>


<script type="text/javascript">
	// 타자기
	window.onload = typeWriter;

	var i = 0;
	var txt = '모두가 하나되는 국내 최고 IT 커뮤니티에 여러분을 초대합니다!';
	var speed = 80;

	function typeWriter() {
		if (i < txt.length) {
			document.getElementById("demo").innerHTML += txt.charAt(i);
			i++;
			setTimeout(typeWriter, speed);
		}
	}
	function viewcount(bcode)
	{
		$.ajax({
            type : "GET",
            url : "viewcount.do?bcode="+bcode,
           	success:function(){},
            error:function(request,status,error){
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
               }
    	});
	};

	function checkBoard(bcode){
		viewcount(bcode);
		location.href="boardOne.do?bcode="+bcode;
	}

	function checkWrite()
	{
		if('${member}' ==''){
			alert("로그인 후 이용 바랍니다");
			$("#loginModal").modal('show');
			return;
		}
		location.href="boardcode.do?dis=${dis}";
	}


	function onEnterSearch()
	{
		var keyCode = window.event.keyCode;
		var keyword = $('input[name=search]').val();
		if (keyCode == 13) {
			location.href="boardSearch.do?dis="+'${dis}'+"&keyword="+keyword;
		}
	}

	$(function(){
		$('#sort').bind('change',function(){
			var val=$(this).val();

			window.location.href="sortList.do?sm="+val+"&dis=${dis}&first=1";
		});
	});

	function loadMore(first)
	{
		var val=$('#sort').val();
		var now = new Date();
		var tdate=now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate();

		$.ajax({
            type : "GET",
            url : "loadMore.do?dis=${dis}&first="+first+"&sm="+val,
           	success:function(mlist){
           		var board=mlist.list;
           		var levels=mlist.levels;
           		
           		if(board.length<=0) $('#iloadMore').remove();

           		for(var i=0;i<board.length;i++)
           		{
           			var pdate=board[i].postdate.substring(0,10);

           			if(pdate==tdate) pdate=board[i].postdate.substring(11,19);
           			
           			var photoSrc;
           			if(!board[i].photo)
           				photoSrc = "resources/img/defaultImg.jpg"
           			else
           				photoSrc = board[i].photo;

           			var levelImg;
           			if(board[i].levelitem=='Y')
           				levelImg="<img src=resources/img/bw/"+levels[i]+".gif>";
           			else levelImg="";	
           			
           			$('#myTable > tbody:last').append(
           					"<tr>"+
							"<td id=table_num>"+first+"</td>"+
							"<td id=td_title>"+
							"<a href=# onclick=checkBoard("+board[i].bcode+")>"+board[i].title+
							"<span id=reply_num>&nbsp;["+board[i].isdelete+"]</span>"+
							"</a></td>"+
							"<td id=table_category>"+board[i].code.name+"</td>"+	
							"<td>"+
							"<div id=tooltip onmouseover=havMedal('"+board[i].writerid+"',"+first+")>"+							
							levelImg+
							"<img class='profile_pics' src="+photoSrc+"/><a href=profile.do?profileId="+board[i].writerid+">"+board[i].writerid+"</a>"+
								"<span id=tooltiptext>"+
									"<div class=tooltip_1>"+
											"<span><a href=boardWriterList.do?writerId="+board[i].writerid+"}>작성한 글</a></span>"+
												"<br>"+
											"<span><a href=boardCommentsList.do?writerId="+board[i].writerid+"}>작성한 댓글</a></span>"+
													"<br>"+
											"<span id='havMedal_"+first+"'>총 받은 메달 : ${pInfo.havmedal}</span>"+
											"<br>"+
										"</div>"+
										"<div class=tooltip_2>"+
											"</div>"+
								"</span>"+
							"</div>"+
							"</td>"+
							"<td id=table_point>"+board[i].point.cal+"</td>"+
							"<td id=table_viewcount>"+board[i].point.viewnum+"</td>"+
							"<td id=table_date>"+pdate+"</td>"+
						"</tr>"
           			);
           			first+=1;
           		}
            	$('#iloadMore').attr('onclick','loadMore('+first+')');

           	},
            error:function(request,status,error){
                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
               }
    	});
	}
	
	function havMedal(profileId,index){
		var profileId=profileId;
 			$.ajax({
			type : "POST",
			url : "profileHavMedal.do",
			data : "profileId="+profileId,
			dataType : "text",
			success : function(rData) {
				var havMedal = rData;
				 $('#havMedal_'+index).text('총 받은 메달 : '+havMedal);
			},
			error : function() {
				console.log("프로필 메달 갯수 가져오기 실패!!");
			}
		});
	}
</script>

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
		<!-- 게시판 영역 -->
		<div class="board_area">
			<!-- 검색창, 검색 정렬들의 패널 -->
			<div class="board">

				<div class="board-heading">

					<%-- 글쓰기 버튼 --%>
					<div id="writebutton">
						<button id="write" type="button" onclick="checkWrite()">글쓰기</button>
					</div>

					<%-- 검색바 --%>
					<div id="search_bar">
						<select id="searchCheck">
							<option value="1" selected>제목&내용</option>
							<option value="2">작성자</option>
						</select> <input id="search_input" type="text" name="search"
							placeholder="검색어를 입력하세요.." onkeydown='javascript:onEnterSearch()'>

						<%-- 게시글 정렬 --%>
						<div class="sort_options">
							<select class="selectpicker" id="sort">
								<option value="r" ${sFlag eq null or sFlag eq 'r'?"selected":""}>최신순</option>
								<option value="f" ${sFlag eq 'f'?"selected":""}>인기순</option>
								<option value="g" ${sFlag eq 'g'?"selected":""}>추천순</option>
							</select>
						</div>

					</div>



				</div>
				<div class="board-body">
					<!-- 게시판 테이블 -->
					<div class="hmm_table">
						<table id="myTable">
							<thead>
								<tr>
									<th>글번호</th>
									<th>제목</th>
									<th>카테고리</th>
									<th>작성자</th>
									<th>추천 점수</th>
									<th>조회수</th>
									<th>작성일자</th>
								</tr>
							</thead>
							<tbody>
								<c:set var="num" value="1" />
								<c:forEach var="l" items="${list }" varStatus="status">

									<tr>
										<td id="table_num">${num }</td>
										<td id="td_title"><a href="#"
											onclick="checkBoard(${l.bcode})">${l.title }<span
												id="reply_num">&nbsp;[${l.isdelete}]</span>
										</a></td>

										<c:if test="${l.code.discode eq 3 }">
											<td id="table_category"
												onclick="location.href='weeksubject.do?sm=r&first=1'">${l.code.name}</td>
										</c:if>

										<c:if test="${l.code.discode ne 3 }">
											<td id="table_category"
												onclick="location.href='boardLists.do?dis=${l.code.discode}&first=1'">${l.code.name}</td>
										</c:if>

										<td>

											<div id="tooltip"
												onmouseover="havMedal('${l.writerid}',${num})">

												<c:if test="${l.levelitem eq 'Y'}">
													<img src="resources/img/bw/${levels[status.index]}.gif">
												</c:if>

												<c:if test="${empty l.photo}">
													<img class="profile_pics"
														src="resources/img/defaultImg.jpg" />
												</c:if>
												<c:if test="${!empty l.photo}">
													<img class="profile_pics" src='${l.photo}' />
												</c:if>
												<a href="profile.do?profileId=${l.writerid }">${l.writerid }</a>
												<span id="tooltiptext">
													<div class="tooltip_1">
														<span><a
															href="boardWriterList.do?writerId=${l.writerid}">작성한
																글</a></span> <br> <span><a
															href="boardCommentsList.do?writerId=${l.writerid}">작성한
																댓글</a></span> <br> <span id="havMedal_${num}">총 받은 메달 :
															${pInfo.havmedal}</span> <br>
													</div>
													<div class="tooltip_2"></div>

												</span>
											</div>
										</td>
										<td id="table_point">${l.point.cal }</td>
										<td id="table_viewcount">${l.point.viewnum }</td>

										<fmt:formatDate value="${today}" pattern="yyyy-MM-dd"
											var="toDay" />
										<c:set var="postdate"
											value="${fn:substring(l.postdate,0,10) }" />
										<c:set var="tpostdate"
											value="${fn:substring(l.postdate,10,19) }" />


										<c:if test="${toDay eq postdate }">
											<td id="table_date">${tpostdate }</td>
										</c:if>

										<c:if test="${toDay ne postdate }">
											<td id="table_date">${postdate }</td>
										</c:if>
									</tr>
									<c:set var="num" value="${num+1 }" />
								</c:forEach>
							</tbody>
						</table>
						<c:if test="${empty keyword && empty sComment && empty writerS}">
							<button id="iloadMore" onclick="loadMore(${num})">더보기</button>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
