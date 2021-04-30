<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<jsp:useBean id="today" class="java.util.Date" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>신기술 찬/반 투표 주제 넣기</title>
		<link href="resources/css/newtechAdmin.css" rel="stylesheet" type="text/css">
		<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

		<script type="text/javascript">
			function iOrEdit(wscode)
			{
				if($('.eidtB').length>0)
				{
					alert('수정 중인 주제가 있습니다.');
					return;
				}

				var title=$('#w_t'+wscode);
				var agree=$('#w_a'+wscode);
				var disagree=$('#w_d'+wscode);
				var button=$('#editButton'+wscode);

				var ctitle;
				var cagree;
				var cdisagree;

				if(title.is('span')) ctitle=title.text();
				else ctitle=title.val();

				if(agree.is('span')) cagree=agree.text();
				else cagree=agree.val();

				if(disagree.is('span')) cdisagree=disagree.text();
				else cdisagree=disagree.val();

				title.replaceWith("<input id=w_t"+wscode+" value=\'"+ctitle+"\'/>");

				agree.replaceWith("<input id=w_a"+wscode+" value=\'"+cagree+"\'/>");

           		disagree.replaceWith("<input id=w_d"+wscode+" value=\'"+cdisagree+"\'/>");

           		button.replaceWith("<button type=button id=edit"+wscode+" class=eidtB>수정하기</button><button type=button id=quit"+wscode+">취소하기</button>");

           		$('#quit'+wscode).click(function(){
           			alert('취소하셨습니다.');
           			window.location.reload(true);
           		});

           		$('#edit'+wscode).click(function(){

           			var ititle=$('#w_t'+wscode);
    				var iagree=$('#w_a'+wscode);
    				var idisagree=$('#w_d'+wscode);


           			var result = confirm('주제 : '+ititle.val()+
    						'\n찬성 : '+iagree.val()+
    						'\n반대 : '+idisagree.val()+
    						'\n위와 같이 수정 및 작성하시겠습니까?\n주제가 시작되기전까지 수정 가능합니다.');

    				if(result)
    				{
    					$.ajax({
    			            type : "POST",
    			            url : "insertNewtech.do",
    			            data : {"wscode":wscode,"title":ititle.val(),"agree":iagree.val(),"disagree":idisagree.val()},
    			           	success:function()
    			           	{
    			           		ititle.replaceWith("<span id=w_t"+wscode+">"+ititle.val()+"</span>");

    			           		iagree.replaceWith("<span id=w_a"+wscode+">"+iagree.val()+"</span>");

    			           		idisagree.replaceWith("<span id=w_d"+wscode+">"+idisagree.val()+"</span>");

    			           		$('#quit'+wscode).remove();
    			           		$('#edit'+wscode).replaceWith("<button type=button id=editButton"+wscode+" onclick=iOrEdit("+wscode+")>입력 및 수정하기</button>");

    			           	},
    			            error:function(request,status,error)
    			            {
    			                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    			            }
    			    	});
    				}
    				else
    				{
    					alert('취소하셨습니다.');
    					window.location.reload(true);
    				}
           		});
			}
		</script>
	</head>
	<body>
		<%@ include file="/header.jsp"%>

		<div class="container">


		<c:forEach var="wl" items="${wlist }">
			<div class="newtech_subject">
			시작일 : ${wl.startdate }
			주제 : <c:if test="${wl.title eq null}">
					<input id="w_t${wl.wscode }" placeholder="입력하셔야합니다."/>
				  </c:if>
				  <c:if test="${wl.title ne null}">
					<span id="w_t${wl.wscode }">${wl.title}</span>
				  </c:if>
			찬성 : <c:if test="${wl.agree  eq null}">
					<input id="w_a${wl.wscode }" placeholder="입력하셔야합니다."/>
				  </c:if>
				  <c:if test="${wl.agree  ne null}">
					<span id="w_a${wl.wscode }">${wl.agree }</span>
				  </c:if>
			반대 : <c:if test="${wl.disagree  eq null}">
					<input id="w_d${wl.wscode }" placeholder="입력하셔야합니다."/>
				  </c:if>
				  <c:if test="${wl.disagree  ne null}">
					<span id="w_d${wl.wscode }">${wl.disagree }</span>
				  </c:if>

			<fmt:formatDate value="${today}" pattern="yyyy-MM-dd" var="toDay"/>
			<fmt:formatDate value="${wl.startdate}" pattern="yyyy-MM-dd" var="startdate"/>

			<c:if test="${toDay<startdate}">
				<button type="button" id="editButton${wl.wscode}" onclick="iOrEdit(${wl.wscode})">입력 및 수정하기</button>
			</c:if>

				</div>
				<br>
		</c:forEach>


		</div>
	</body>
</html>
