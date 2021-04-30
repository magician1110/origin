<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<link href="resources/css/newtechResult.css" rel="stylesheet" type="text/css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<title>신기술 투표 결과</title>
	</head>

	<body onload="move();move2();participants_num1();participants_num2();">
		<%@ include file="/header.jsp"%>

		<h1 class="title">${weeksubject.title }</h1>

		<div class="container">
			<div class="chart">
				<div id="option-yes" class="the_options">
					<div class="results">
						<span class="on" id="on1"></span>
					</div>

					<h3 class="option-label">"${weeksubject.agree }"
						<span class="count" id="chart_on1"></span><br>
						<span id="head_counts1"></span>
					</h3>
				</div>

				<div class="the_vs_ball_area">
					<div class="the_vs_ball">VS</div>
				</div>

				<div id="option-no" class="the_options">
					<div class="results">
						<span class="on" id="on2"></span>
					</div>

					<h3 class="option-label">"${weeksubject.disagree }"
						<span class="count" id="chart_on2"></span><br>
						<span id="head_counts2"></span>
					</h3>
				</div>
			</div>
		</div>

		<div id="participants">
			<h2>총 투표 참여자 : ${sum }</h2>
		</div>

		<script>
			function move()
			{
				var elem = document.getElementById("on1");
				var elem2 = document.getElementById("chart_on1");

				var width = 0.0;
				var id = setInterval(frame, 0.5);
				function frame()
				{
					if (width >= '${agreePercent}')
					{
						clearInterval(id);
					}
					else
					{
						width+=0.1;

						if(width>100)	width=100.0;

						elem.style.width = width.toFixed(1) + '%';
						elem2.innerHTML = width.toFixed(1)  + '%';
					}
				}
			}

			function participants_num1()
			{
				var elem = document.getElementById("head_counts1");
				var width = 0;
				var id = setInterval(frame, 30);
				function frame()
				{
					if (width >= '${agreeNum}')
					{
						clearInterval(id);
					}
					else
					{
						width++;
						elem.innerHTML = width * 1 + '명';
					}
				}
			}

			function move2()
			{
				var elem = document.getElementById("on2");
				var elem3 = document.getElementById("chart_on2");

				var width = 0.0;
				var id = setInterval(frame, 0.5);

				function frame()
				{
					if (width >= '${disagreePercent}')
					{
						clearInterval(id);
					}
					else
					{
						width+=0.1;

						if(width>100)	width=100.0;

						elem.style.width = width.toFixed(1) + '%';
						elem3.innerHTML = width.toFixed(1) + '%';
					}
				}
			}

			function participants_num2()
			{
				var elem = document.getElementById("head_counts2");
				var width = 0;
				var id = setInterval(frame, 30);
				function frame()
				{
					if (width >= '${disagreeNum}')
					{
						clearInterval(id);
					}
					else
					{
						width++;
						elem.innerHTML = width * 1 + '명';
					}
				}
			}
		</script>
	</body>
</html>
