<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat, java.sql.Date, 
	 java.util.ArrayList" %>
<%
//Design d = (Design) request.getAttribute("design");
//Part p = (Part) request.getAttribute("part");

//SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
String item = request.getParameter("item");
//String date = df.format(new Date(Long.parseLong(request.getParameter("date"))*1000L));
String nick = (String) request.getParameter("nick");
String payType = (String) request.getParameter("pay_method");
int quan = Integer.parseInt(request.getParameter("quan"));
int price = Integer.parseInt(request.getParameter("price"));

switch(payType){
case "card": payType = "신용카드 결제"; break;
case "trans": payType = "실시간계좌이체 결제"; break;
case "vbank": payType = "가상계좌 결제"; break;
case "phone": payType = "휴대폰소액결제 결제"; break;
case "samsung": payType = "삼성페이 결제"; break;
case "kpay": payType = "KPay앱 결제"; break;
case "cultureland": payType = "문화상품권 결제"; break;
case "smartculture": payType = "스마트문상 결제"; break;
case "happymoney": payType = "해피머니 결제"; break;
default:
}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<title>Order Confirm</title>
	<!-- CSS FILES -->
    
</head>
<body>
	<%@ include file="../../header.jsp" %>
	<h1> 구매내역</h1>
	
	
	
	
</body>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<%@ include file="/footer.jsp"%>
</html>