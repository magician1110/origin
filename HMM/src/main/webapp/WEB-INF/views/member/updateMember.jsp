<%@page import="com.kh.hmm.member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<!-- 회원수정 모달 -->
<c:set var="job" value="${member.job }" scope="session" />
<c:set var="photo" value="${member.photo }" scope="session" />
<c:set var="quitDate" value="${member.quitdate}" scope="session" />
<c:set var="membercode" value="${member.membercode}" scope="session"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="resources/css/updateMember.css" rel="stylesheet"
	type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js"
	integrity="sha384-FzT3vTVGXqf7wRfy8k4BiyzvbNfeYjK+frTVqZeNDFl8woCbF0CYG6g2fMEFFo/i"
	crossorigin="anonymous"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript">
	fileExt = 0;
	$(function() {
		var quitDate = '${quitDate}';

		if (quitDate != '')
			alert(quitDate + "일에 회원 탈퇴될 예정 입니다!!");

		var job = "${job}";
		$('#updateJob option').each(function() {
			if ($(this).val() == job)
				$(this).attr('selected', 'selected');
		});

		$("#imgUpload").on('change', function() {
			fileExt = this.value;
			readURL(this);
		});

	});
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				$('#profileImg').attr('src', e.target.result);
			}

			reader.readAsDataURL(input.files[0]);
		}
	}


	function validationDate() {
		var x = document.updateForm.pwd.value;
		var y = document.updateForm.pwdc.value;
		var xleng = x.length;
		var yleng = y.length;
		var cfm = confirm("수정 하시겠습니까?");
		if (cfm) {
			if ((x == y) && ((x.length >= 6 && x.length <= 20) && (y.length >= 6 && y.length <= 20))){
				x = SHA256(x);
				$('input[name=password]').val(x);
				$('#updateForm').submit();
			}
			else if(xleng < 6 || xleng > 20 || yleng < 6 || yleng > 20){
				alert("비밀번호는 최소 6자리에서 최대 20자리 까지 입력 해야 합니다!!");
				return;
				}else{
				alert("비밀번호를 한번 더 확인해주세요!!");
			}
		} else {
			return;
		}

	}

	function validationFile() {
		var file = fileExt.substring(fileExt.lastIndexOf('.') + 1);
		if (file.toUpperCase() == "JPG" || file.toUpperCase() == "PNG"
				|| file.toUpperCase() == "GIF") {
			$('#pictureUpload').submit();
		} else {
			alert("jpg, png, gif 파일만 업로드 가능합니다!!");
			return;
		}
	}

	function deleteMember() {
		var cfm = confirm("회원 탈퇴 하시겠습니까?");
		if (cfm) {
			location.href = "deleteMember.do";
		} else {
			return;
		}
	}
	function SHA256(s) {

		var chrsz = 8;
		var hexcase = 0;

		function safe_add(x, y) {
			var lsw = (x & 0xFFFF) + (y & 0xFFFF);
			var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
			return (msw << 16) | (lsw & 0xFFFF);
		}

		function S(X, n) {
			return (X >>> n) | (X << (32 - n));
		}
		function R(X, n) {
			return (X >>> n);
		}
		function Ch(x, y, z) {
			return ((x & y) ^ ((~x) & z));
		}
		function Maj(x, y, z) {
			return ((x & y) ^ (x & z) ^ (y & z));
		}
		function Sigma0256(x) {
			return (S(x, 2) ^ S(x, 13) ^ S(x, 22));
		}
		function Sigma1256(x) {
			return (S(x, 6) ^ S(x, 11) ^ S(x, 25));
		}
		function Gamma0256(x) {
			return (S(x, 7) ^ S(x, 18) ^ R(x, 3));
		}
		function Gamma1256(x) {
			return (S(x, 17) ^ S(x, 19) ^ R(x, 10));
		}

		function core_sha256(m, l) {

			var K = new Array(0x428A2F98, 0x71374491, 0xB5C0FBCF, 0xE9B5DBA5,
					0x3956C25B, 0x59F111F1, 0x923F82A4, 0xAB1C5ED5, 0xD807AA98,
					0x12835B01, 0x243185BE, 0x550C7DC3, 0x72BE5D74, 0x80DEB1FE,
					0x9BDC06A7, 0xC19BF174, 0xE49B69C1, 0xEFBE4786, 0xFC19DC6,
					0x240CA1CC, 0x2DE92C6F, 0x4A7484AA, 0x5CB0A9DC, 0x76F988DA,
					0x983E5152, 0xA831C66D, 0xB00327C8, 0xBF597FC7, 0xC6E00BF3,
					0xD5A79147, 0x6CA6351, 0x14292967, 0x27B70A85, 0x2E1B2138,
					0x4D2C6DFC, 0x53380D13, 0x650A7354, 0x766A0ABB, 0x81C2C92E,
					0x92722C85, 0xA2BFE8A1, 0xA81A664B, 0xC24B8B70, 0xC76C51A3,
					0xD192E819, 0xD6990624, 0xF40E3585, 0x106AA070, 0x19A4C116,
					0x1E376C08, 0x2748774C, 0x34B0BCB5, 0x391C0CB3, 0x4ED8AA4A,
					0x5B9CCA4F, 0x682E6FF3, 0x748F82EE, 0x78A5636F, 0x84C87814,
					0x8CC70208, 0x90BEFFFA, 0xA4506CEB, 0xBEF9A3F7, 0xC67178F2);

			var HASH = new Array(0x6A09E667, 0xBB67AE85, 0x3C6EF372,
					0xA54FF53A, 0x510E527F, 0x9B05688C, 0x1F83D9AB, 0x5BE0CD19);

			var W = new Array(64);
			var a, b, c, d, e, f, g, h, i, j;
			var T1, T2;

			m[l >> 5] |= 0x80 << (24 - l % 32);
			m[((l + 64 >> 9) << 4) + 15] = l;

			for (var i = 0; i < m.length; i += 16) {
				a = HASH[0];
				b = HASH[1];
				c = HASH[2];
				d = HASH[3];
				e = HASH[4];
				f = HASH[5];
				g = HASH[6];
				h = HASH[7];

				for (var j = 0; j < 64; j++) {
					if (j < 16)
						W[j] = m[j + i];
					else
						W[j] = safe_add(safe_add(safe_add(Gamma1256(W[j - 2]),
								W[j - 7]), Gamma0256(W[j - 15])), W[j - 16]);

					T1 = safe_add(safe_add(safe_add(safe_add(h, Sigma1256(e)),
							Ch(e, f, g)), K[j]), W[j]);
					T2 = safe_add(Sigma0256(a), Maj(a, b, c));

					h = g;
					g = f;
					f = e;
					e = safe_add(d, T1);
					d = c;
					c = b;
					b = a;
					a = safe_add(T1, T2);
				}

				HASH[0] = safe_add(a, HASH[0]);
				HASH[1] = safe_add(b, HASH[1]);
				HASH[2] = safe_add(c, HASH[2]);
				HASH[3] = safe_add(d, HASH[3]);
				HASH[4] = safe_add(e, HASH[4]);
				HASH[5] = safe_add(f, HASH[5]);
				HASH[6] = safe_add(g, HASH[6]);
				HASH[7] = safe_add(h, HASH[7]);
			}
			return HASH;
		}

		function str2binb(str) {
			var bin = Array();
			var mask = (1 << chrsz) - 1;
			for (var i = 0; i < str.length * chrsz; i += chrsz) {
				bin[i >> 5] |= (str.charCodeAt(i / chrsz) & mask) << (24 - i % 32);
			}
			return bin;
		}

		function Utf8Encode(string) {
			string = string.replace(/\r\n/g, "\n");
			var utftext = "";

			for (var n = 0; n < string.length; n++) {

				var c = string.charCodeAt(n);

				if (c < 128) {
					utftext += String.fromCharCode(c);
				} else if ((c > 127) && (c < 2048)) {
					utftext += String.fromCharCode((c >> 6) | 192);
					utftext += String.fromCharCode((c & 63) | 128);
				} else {
					utftext += String.fromCharCode((c >> 12) | 224);
					utftext += String.fromCharCode(((c >> 6) & 63) | 128);
					utftext += String.fromCharCode((c & 63) | 128);
				}

			}

			return utftext;
		}

		function binb2hex(binarray) {
			var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
			var str = "";
			for (var i = 0; i < binarray.length * 4; i++) {
				str += hex_tab
						.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8 + 4)) & 0xF)
						+ hex_tab
								.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8)) & 0xF);
			}
			return str;
		}

		s = Utf8Encode(s);
		return binb2hex(core_sha256(str2binb(s), s.length * chrsz));

	}

	function profileImgDel()
	{
		location.href="profileImgDel.do?membercode="+${membercode};
	}
</script>

<title>회원 정보 수정</title>
</head>


<body>
	<%@ include file="/header.jsp"%>

	<div class="container">

		<div class="profile_board">
			<!-- Modal content-->
			<div class="profile">

				<div class="profile-heading">${member.id}님의프로필</div>
				<div class="profile-body">
					<form id="pictureUpload" name="pictureUpload"
						action="uploadFile.do" method="POST" enctype="multipart/form-data">
						<c:choose>
							<c:when test="${null eq photo}">
								<img id="profileImg" src="resources/img/defaultImg.jpg"
									alt="profileImg" />
							</c:when>
							<c:when test="${null ne photo}">
								<img id="profileImg" src="${photo}" alt="profileImg" />
							</c:when>
						</c:choose>
						<br> <br> <input type='file' id="imgUpload" name="photo"
							id="photo" /> <br>

						<button type="button" id="file_upload_btn"
							onclick="validationFile()">프로필 사진 업로드</button>
							<c:if test="${null ne photo}">
							<button type="button"  id="file_delete_btn" onclick="profileImgDel()">프로필 사진 삭제</button>
							</c:if>
					</form>
					<form id="updateForm" name="updateForm" action="update.do"
						method="POST">
						<input id="input_id" type="text" name="id" value="${member.id}"
							readonly> <input type="hidden" name="password"> <input
							type="password" name="pwd" placeholder="비밀번호" value="" required
							id="password"><br> <input type="password"
							id="passwordConfirm" name="pwdc" placeholder="비밀번호 확인" value=""
							required id="passwordConfirm"><br> <input
							type="email" name="email" placeholder="이메일"
							value="${member.email}" required /><br> <select name="job"
							id="updateJob">
							<option value="student">학생</option>
							<option value="business">회사원</option>
							<option value="jobless">무직</option>
							<option value="etc">기타</option>
						</select> <br>
						<button id="profile_update_btn" type="button"
							onclick="validationDate()">수정하기</button>

					</form>
				</div>

				<div class="profile-footer">
					<label>메달 갯수 : ${member.havmedal}</label> <br> <label>경험치
						: ${member.exp}</label> <br> <label>남은 캐시 : ${member.chash}</label> <br>
					<label>남은 따루 : ${member.ddaru}</label> <br> <label>가입일
						: ${member.enrolldate}</label>

					<button id="profile_delete_btn" type="button"
						onclick="deleteMember()">회원 탈퇴</button>

				</div>
			</div>
		</div>


	</div>
</body>
</html>
