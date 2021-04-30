<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="resources/css/login.css" rel="stylesheet" type="text/css">
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
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id"
	content="419809006981-bkqqk1p2e3bhevtice98fcc9efo5fhkp.apps.googleusercontent.com">

<!-- 로그인 모달 -->
<div class="container">

	<div id="loginModal" class="modal">

		<div class="container">
			<div class="modal-content animate">
				<div class="modal-header">
					<h4 class="modal-title">로그인</h4>
				</div>
				<div class="modal-body">
					<input type="text" name="id" placeholder="아이디를 입력해 주세요" required>
					<input type="password" name="password" placeholder="비밀번호를 입력해 주세요"
						required onkeydown='javascript:onEnterSubmit()'>
					<button type="button" id="loginbtn" onclick="onSubmit()">로그인</button>
					<br>
					<hr>
					<p>구글 계정으로 로그인하기:</p>
					<div class="g-signin2" data-width="100%" data-onsuccess="onSignIn"></div>
				</div>
				<span class="psw" style="float: left;">&nbsp; 혹시 <a href="#"
					style="text-decoration: none;" onclick="popupSearch()">아이디/비밀번호</a>를
					잊으셨나요?
				</span>
				<div class="modal-footer">
					<button type="button" id="cancelbtn" data-dismiss="modal">창
						닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 로그인 모달 -->

<script type="text/javascript">
	count = 0;
	function onEnterSubmit() {
		var keyCode = window.event.keyCode;
		if (keyCode == 13) {
			onSubmit();
		}
	}

	function onSubmit() {

		var id = $('input[name=id]').val();
		var pwd = $('input[name=password]').val();

		var i = pwd.indexOf("admin");
		if (i == -1) {
			i = pwd.indexOf("user");
		} else
			i = 1;
		if (i == -1) {
			if (id == '' || pwd == '') {
				alert("아이디/패스워드를 입력해 주세요!!");
				return;
			} else if (pwd.length <= 5) {
				pwd = $('input[name=password]').val();
			} else {
				pwd = SHA256(pwd);
			}
		} else
			var pwd = $('input[name=password]').val();
		var member = {
			"id" : $('input[name=id]').val(),
			"password" : pwd
		}

		$.ajax({
			type : "POST",
			url : "login.do",
			data : member,
			dataType : "text",
			success : function(rData, textStatus, xhr) {
				var chkRst = rData;
				if (chkRst == "true") {
					if (i == -1) {
						if (pwd.length == 5) {
							window.location.href = "updateProfile.do";
						} else {
							window.location.reload();
						}
					} else
						window.location.reload();
				} else {
					count = count + 1;
					if (count == 4) {
						popupSearch();
						count = 0;
						return;
					}
					alert("아이디/패스워드를 확인해 주세요!!");
					$('input[name=id]').val('');
					$('input[name=password]').val('');

				}
			},
			error : function() {
				alert("로그인 실패!!");
			}
		});

	}
	// Get the modal
	var modal = document.getElementById('loginModal');

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}

	function onSignIn(googleUser) {
		var profile = googleUser.getBasicProfile();
		console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
		console.log('Name: ' + profile.getName());
		console.log('Image URL: ' + profile.getImageUrl());
		console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
		var email = profile.getEmail();
		var id = email.substring(0, email.lastIndexOf('@'));

		var member = {
			"id" : id,
			"photo" : profile.getImageUrl(),
			"email" : profile.getEmail(),
			"password" : "googleLogin",
			"job" : "etc"
		};

		$.ajax({
			type : "POST",
			url : "google.do",
			data : member,
			dataType : "text",
			success : function(rData, textStatus, xhr) {
				var chkRst = rData;
				if (chkRst == "true") {
					var auth2 = gapi.auth2.getAuthInstance();
					auth2.signOut().then(function() {
						console.log('User signed out.');
					});
					window.location.reload();
				} else {
					$('input[name=id]').val('');
					$('input[name=password]').val('');
				}
			},
			error : function() {
				alert("로그인 실패!!");
			}
		});

	}

	function popupSearch() {
		var popUrl = "resources/search/Search.jsp";
		var popOption = "width=400, height=auto, resizable=yes, scrollbars=no, status=no;"; //팝업창 옵션(optoin)
		window.open(popUrl, "", popOption);
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
</script>
