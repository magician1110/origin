<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="../css/search.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<script type="text/javascript">
	function idSearch() {
		var email = $('input[type=email]').val();
		if (email == '') {
			alert("이메일을 입력해 주세요!!");
			return;
		}
		$.ajax({
			type : "POST",
			url : "/hmm/idSearch.do",
			data : "email=" + email,
			dataType : "text",
			success : function(rData, textStatus, xhr) {
				if (rData == 0) {
					alert("유효하지 않은 이메일 입니다!!");
				} else if (rData == 1) {
					alert("등록 되지 않은 이메일 입니다!!");
				} else if (rData == 2) {
					alert("이메일 전송 실패!!");
				} else if (rData == 3) {
					alert("아이디가 이메일로 전송 되었습니다!!");
				}
			},
			error : function() {
				alert("이메일 전송 실패!!");
			}
		});
	}

	function pwdSearch() {
		var id = $('#searchID').val();
		var email = $('#searchEmail').val();
		if (id == '') {
			alert("아이디를 입력해 주세요!!");
			return;
		} else if (email == '') {
			alert("이메일을 입력해 주세요!!");
			return;
		}

		var search = {
			"id" : id,
			"email" : email
		};

		$.ajax({
			type : "POST",
			url : "/hmm/pwdSearch.do",
			data : search,
			dataType : "text",
			success : function(rData, textStatus, xhr) {
				if (rData == 0) {
					alert("유효하지 않은 이메일 입니다!!");
				} else if (rData == 1) {
					alert("등록 되지 않은 회원 입니다!!");
				} else if (rData == 2) {
					alert("이메일 전송 실패!!");
				} else if (rData == 3) {
					alert("임시 비밀번호가 이메일로 전송 되었습니다!!\n프로필 수정에서 비밀번호를 꼭 바꿔주세요!!");
				}
			},
			error : function() {
				alert("이메일 전송 실패!!");
			}
		});
	}
</script>

</head>
<body>
	<div class="search_idpw">
		<div class="search_idpw_head">
			<h2>아이디/패스워드 찾기</h2>
		</div>
		<div class="search_idpw_body">
			<h3>아이디 찾기</h3>
			<h4>
				이메일 : <input type="email" placeholder="이메일 주소를 입력해 주세요">
			</h4>
			<button type="button" onclick="idSearch()" id="idSearch">아이디
				찾기</button>
			<br>
			<hr>
			<h3>비밀번호 찾기</h3>
			<h4>
				아이디 : <input type="text" id="searchID" placeholder="아이디를 입력해 주세요">
			</h4>
			<h4>
				이메일 : <input type="email" id="searchEmail"
					placeholder="이메일 주소를 입력해 주세요">
			</h4>
			<button type="button" onclick="pwdSearch()">패스워드 찾기</button>
			<button class="cancelbtn" onclick="self.close()">창 닫기</button>
		</div>
	</div>
</body>
</html>
