<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <form action="" method="POST">
            <div class="form-group">
	            <label for="user_id">아이디</label>
	                <input type="text" class="form-control" id="user_id" name="user_id" placeholder="ID" required>
	            <div class="check_font" id="id_check"></div>
            </div>
            <!-- 비밀번호 -->
            <div class="form-group">
                <label for="user_pw">비밀번호</label>
                    <input type="password" class="form-control" id="user_pw" name="user_pw" placeholder="PASSWORD" required>
                <div class="check_font" id="pw_check"></div>
            </div>
            <!-- 비밀번호 재확인 -->
            <div class="form-group">
                <label for="user_pw2">비밀번호 확인</label>
                    <input type="password" class="form-control" id="user_pw2" name="user_pw2" placeholder="Confirm Password" required>
                <div class="check_font" id="pw2_check"></div>
            </div>            
    </form>
</body>
</html>