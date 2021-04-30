<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="resources/css/insertMember.css" rel="stylesheet"
	type="text/css">
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
	joinCode = 0;
	interval = 0;
	$(function() {
		$('#emailCode').hide();
		$('#codeCheck').hide();

		$('#emailCheck').click(function(){
			var email = $('input[name=email]').val();
			if(email == '')
				{
				alert("이메일을 입력해 주세요!!");
				return;
				}
			var SetTime = 180;		// 최초 설정 시간(기본 : 초)
			$('#emailCode').show('slow');
			$('#codeCheck').show('slow');
			$.ajax({
				type : "POST",
				url : "sendMail.do",
				data : "email=" + email,
				dataType : "text",
				success : function(rData, textStatus, xhr) {
					if (rData != "emailDup") {
						joinCode = rData;
						alert("인증코드가 이메일로 전송 되었습니다!!");
						interval = setInterval(function Timer(){
							m = Math.floor((SetTime) / 60) + "분 " + (SetTime % 60) + "초";	// 남은 시간 계산
							var msg = m;
							$('#codeCheck').text(msg);
							SetTime--;					// 1초씩 감소

							if(setTime == 0){
								alert("인증에 실패했습니다 다시 인증해 주세요!!");
								clearInterval(interval);
								$('#codeCheck').text("인증확인");
							}
						},1000);
					}
					else if(rData == "emailDup")
						{
						alert("이메일이 이미 등록 되어있습니다!!");
						}
					else if(rData == "fail"){
						alert("유효하지 않은 이메일 입니다!!");
					}
				},
				error : function() {
					alert("이메일 전송 실패!!");
				}
			});
		});
		$('#codeCheck').click(function(){
			var code = $('#emailCode').val();
			code.trim();

			if(joinCode != 0 &&code == joinCode){
				alert("인증에 성공했습니다!!");
				clearInterval(interval);
				$('#codeCheck').text("인증성공");
				$('#codeCheck').attr("disabled");
				$('#insertChk').val("YY");
			}
			else
				{
				alert("인증에 실패했습니다!!");
				clearInterval(interval);
				$('#codeCheck').text("인증확인");
				}
		});

	});
	function validationCheck() {
		var password = $('#password').val();
		var passwordConfirm = $('#passwordConfirm').val();
		if ((password != '' || passwordConfirm != '')
				&& password == passwordConfirm){
		if($('#insertChk').val() == "YY")
		{
			var pwd = $('#password').val();
			pwd = SHA256(pwd);
			$('#password').val(pwd);
			/* $('form').submit(); */
			$('#insetForm').submit();
		}
		else if($('#insertChk').val() == "Y")
		{
			alert("이메일 인증을 완료해주세요!!");
			return;
		}
		else
		{
			alert("아이디 중복확인을 해주세요!!");
			return;
		}
		}
		else if(password.length < 6 || password.length > 20)
			{
			alert("비밀번호는 최소 6자리에서 최대 20자리 까지 입력 할수 있습니다!!");
			return;
			}
		else{
			alert("비밀번호를 한번 더 확인해주세요!!");
			return;
		}
	}

	function chkDup() {

		var id = $('#insertID').val();
		if (id == '') {
			alert('ID를 입력해주세요.');
			return;
		}
		
		var pattern = /^[A-Za-z0-9]{6,20}$/;
		if (!pattern.test(id)) {
			alert('영문대/소문자, 숫자 합쳐서 6~20자리로 입력해 주세요.');
			return;
		}

		$.ajax({
			type : "POST",
			url : "idCheck.do",
			data : "id=" + id,
			dataType : "text",
			success : function(rData, textStatus, xhr) {
				var chkRst = rData;

				if (chkRst == 1) {
					alert("등록 가능 합니다!!");
					$('#insertChk').val("Y");
				} else {
					alert("아이디 중복 입니다!!");
				}
			},
			error : function() {
				alert("아이디 중복 확인 실패!!");
			}
		});
	}

	   function SHA256(s){

	        var chrsz   = 8;
	        var hexcase = 0;

	        function safe_add (x, y) {
	            var lsw = (x & 0xFFFF) + (y & 0xFFFF);
	            var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
	            return (msw << 16) | (lsw & 0xFFFF);
	        }

	        function S (X, n) { return ( X >>> n ) | (X << (32 - n)); }
	        function R (X, n) { return ( X >>> n ); }
	        function Ch(x, y, z) { return ((x & y) ^ ((~x) & z)); }
	        function Maj(x, y, z) { return ((x & y) ^ (x & z) ^ (y & z)); }
	        function Sigma0256(x) { return (S(x, 2) ^ S(x, 13) ^ S(x, 22)); }
	        function Sigma1256(x) { return (S(x, 6) ^ S(x, 11) ^ S(x, 25)); }
	        function Gamma0256(x) { return (S(x, 7) ^ S(x, 18) ^ R(x, 3)); }
	        function Gamma1256(x) { return (S(x, 17) ^ S(x, 19) ^ R(x, 10)); }

	        function core_sha256 (m, l) {

	            var K = new Array(0x428A2F98, 0x71374491, 0xB5C0FBCF, 0xE9B5DBA5, 0x3956C25B, 0x59F111F1,
	                0x923F82A4, 0xAB1C5ED5, 0xD807AA98, 0x12835B01, 0x243185BE, 0x550C7DC3,
	                0x72BE5D74, 0x80DEB1FE, 0x9BDC06A7, 0xC19BF174, 0xE49B69C1, 0xEFBE4786,
	                0xFC19DC6, 0x240CA1CC, 0x2DE92C6F, 0x4A7484AA, 0x5CB0A9DC, 0x76F988DA,
	                0x983E5152, 0xA831C66D, 0xB00327C8, 0xBF597FC7, 0xC6E00BF3, 0xD5A79147,
	                0x6CA6351, 0x14292967, 0x27B70A85, 0x2E1B2138, 0x4D2C6DFC, 0x53380D13,
	                0x650A7354, 0x766A0ABB, 0x81C2C92E, 0x92722C85, 0xA2BFE8A1, 0xA81A664B,
	                0xC24B8B70, 0xC76C51A3, 0xD192E819, 0xD6990624, 0xF40E3585, 0x106AA070,
	                0x19A4C116, 0x1E376C08, 0x2748774C, 0x34B0BCB5, 0x391C0CB3, 0x4ED8AA4A,
	                0x5B9CCA4F, 0x682E6FF3, 0x748F82EE, 0x78A5636F, 0x84C87814, 0x8CC70208,
	                0x90BEFFFA, 0xA4506CEB, 0xBEF9A3F7, 0xC67178F2);

	            var HASH = new Array(0x6A09E667, 0xBB67AE85, 0x3C6EF372, 0xA54FF53A, 0x510E527F, 0x9B05688C, 0x1F83D9AB, 0x5BE0CD19);

	            var W = new Array(64);
	            var a, b, c, d, e, f, g, h, i, j;
	            var T1, T2;

	            m[l >> 5] |= 0x80 << (24 - l % 32);
	            m[((l + 64 >> 9) << 4) + 15] = l;

	            for ( var i = 0; i<m.length; i+=16 ) {
	                a = HASH[0];
	                b = HASH[1];
	                c = HASH[2];
	                d = HASH[3];
	                e = HASH[4];
	                f = HASH[5];
	                g = HASH[6];
	                h = HASH[7];

	                for ( var j = 0; j<64; j++) {
	                    if (j < 16) W[j] = m[j + i];
	                    else W[j] = safe_add(safe_add(safe_add(Gamma1256(W[j - 2]), W[j - 7]), Gamma0256(W[j - 15])), W[j - 16]);

	                    T1 = safe_add(safe_add(safe_add(safe_add(h, Sigma1256(e)), Ch(e, f, g)), K[j]), W[j]);
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

	        function str2binb (str) {
	            var bin = Array();
	            var mask = (1 << chrsz) - 1;
	            for(var i = 0; i < str.length * chrsz; i += chrsz) {
	                bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (24 - i%32);
	            }
	            return bin;
	        }

	        function Utf8Encode(string) {
	            string = string.replace(/\r\n/g,"\n");
	            var utftext = "";

	            for (var n = 0; n < string.length; n++) {

	                var c = string.charCodeAt(n);

	                if (c < 128) {
	                    utftext += String.fromCharCode(c);
	                }
	                else if((c > 127) && (c < 2048)) {
	                    utftext += String.fromCharCode((c >> 6) | 192);
	                    utftext += String.fromCharCode((c & 63) | 128);
	                }
	                else {
	                    utftext += String.fromCharCode((c >> 12) | 224);
	                    utftext += String.fromCharCode(((c >> 6) & 63) | 128);
	                    utftext += String.fromCharCode((c & 63) | 128);
	                }

	            }

	            return utftext;
	        }

	        function binb2hex (binarray) {
	            var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
	            var str = "";
	            for(var i = 0; i < binarray.length * 4; i++) {
	                str += hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8+4)) & 0xF) +
	                hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8  )) & 0xF);
	            }
	            return str;
	        }

	        s = Utf8Encode(s);
	        return binb2hex(core_sha256(str2binb(s), s.length * chrsz));

	    }


</script>
<!-- 회원가입 모달 -->
<body>
	<div class="container">


		<!-- Modal -->
		<div class="modal" id="insertModal" role="dialog">

			<!-- Modal content-->
			<div class="modal-content animate">
				<div class="modal-header">
					<h4 class="modal-title">회원가입</h4>
				</div>
				<div class="modal-body insert_body">
					<form action="enroll.do" method="POST" id="insetForm">
						<input type="hidden" value="N" id="insertChk" /> <input
							type="text" name="id"
							placeholder="아이디	ex) 영문 대소문자 + 숫자, 최소 6 자리에서 20자리까지 가능."
							id="insertID" maxlength="20" required> &nbsp; &nbsp;
						<button type="button" style="color: white" onclick="chkDup()">중복확인</button>
						<br> <input type="password" name="password"
							placeholder="비밀번호	ex) 최소 6 자리에서 20자리까지 가능." maxlength="20" required
							id="password"><br> <input type="password"
							name="passwordConfirm"
							placeholder="비밀번호확인	ex) 최소 6 자리에서 20자리까지 가능." maxlength="20" required
							id="passwordConfirm"><br> <input type="email"
							name="email" placeholder="이메일" required />
						<button id="emailCheck" style="color: white" type="button">이메일
							인증</button>
						<br> <input type="text" id="emailCode"><br>
						<button type="button" id="codeCheck" style="color: white">인증확인</button>
						<select name="job">
							<option value="student">학생</option>
							<option value="business">회사원</option>
							<option value="jobless">무직</option>
							<option value="etc">기타</option>
						</select> <br> &nbsp;&nbsp;
						<button type="button" style="color: white"
							onclick="validationCheck()">회원가입</button>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="cancelbtn" data-dismiss="modal">창
						닫기</button>
				</div>
			</div>

		</div>

	</div>
	<!-- 회원가입 모달 -->

	<script>
		// Get the modal
		var modal = document.getElementById('insertModal');
		var form = document.getElementById('insetForm');

		// When the user clicks anywhere outside of the modal, close it
		window.onclick = function(event) {
			if (event.target == modal) {
				modal.style.display = "none";
				clearInterval(interval);
				$('#codeCheck').text("인증확인");
				form.reset();
			}
		}
	</script>
</body>
