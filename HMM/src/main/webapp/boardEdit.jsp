<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>글 수정</title>
		<link href="resources/css/write.css" rel="stylesheet" type="text/css">
		<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
		<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
		<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
		<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
		<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>
	</head>
	<body>
		<%@ include file="/header.jsp"%>

		<div class="container">
			<div class="post">
				<input type="hidden" name="bcode" value="${board.bcode }">
				<input type="hidden" name="writerid" value="${board.writerid }">

				<div id="the_post_title">제목&nbsp;&nbsp;&nbsp;
					<input id="post_title" type="text" name="title"	maxlength="120" value="${board.title }"></input>
					<div id="title_feedback">40자 제한</div>
				</div>

				<%-- 글쓴이 아이디 숨김 --%>
				<span style="display:none">${sessionScope.member.id }</span>
				<div id="post_categories">
					<button id="boardDelete">글 삭제</button><span id="current_category">${board.code.name  } &nbsp;게시판</span>
				</div>

				<div id="post_contents">
					<%-- 첨부파일 리스트 --%>
					<c:if test="${flist ne null}">
							<c:set var="num" value="1" />
							<c:forEach var="f" items="${flist}">
								<div id="fileList${f.atcode }">
									file${num } : <a href="filedown.do?atcode=${f.atcode }">${f.originname }</a>
									<button type="button" onclick="fileDelete(${f.atcode })">파일 삭제</button><br>
								</div>
								<c:set var="num" value="${num+1 }" />
							</c:forEach>

					</c:if>

				<!-- 섬머노트 부분 -->
					<div class="sn">
						<div class="content">
							<textarea id="summernote" name="content" maxlength="4000">${board.content }</textarea>
							<div id="content_feedback">2000자 제한</div>
						</div>
					</div>

					<div id="fileUpload" class="dragAndDropDiv">
						<span id="fileUpload_text">추가 업로드를 위해서는 이 곳에 파일을 끌어다 놓으세요</span>
					</div>
				</div>

				<div class="button_area">
					<button type="button" id="beditComplete">글 수정</button>&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="quit_post" onclick="goBack()">수정 취소</button>
				</div>
			</div>
		</div>

		<%-- 등록 취소 버튼 --%>
		<script>
			function goBack() {window.history.back();}
		</script>

	<%-- 써머노트 --%>
	<script>
	      $('#summernote').summernote({
	        placeholder: '여기에 글을 입력하세요',
	        tabsize: 2,
					height: 300,
					minHeight: null,             // set minimum height of editor
					maxHeight: null,             // set maximum height of editor
					focus: true,
					disableResizeEditor: true,
					toolbar: [
    // [groupName, [list of button]]
    ['style', ['bold', 'italic', 'underline', 'clear']],
    ['font', ['strikethrough', 'superscript', 'subscript']],
    ['fontsize', ['fontsize']],
    ['color', ['color']],
    ['para', ['ul', 'ol', 'paragraph']],
    ['height', ['height']]
  ]

	      });
	</script>

	<%-- 제목 남은 글자 수 초과시 --%>
	<script>
		$('#post_title').keyup(function() {
			var title=$('#post_title').val();

			if(title.length>40)
			{
				alert("40자 까지만 입력가능합니다.");
				title=title.substring(0,40)
				$('#post_title').val(title);
			}
			
			$('#title_feedback').text('40자 제한 / 현재 : '+title.length+"자");
		});	
	</script>

	<%-- 내용 남은 글자 수 초과시--%>
	<script type="text/javascript">
		var summer=$('#summernote').next(); 
				
		summer.keyup(function() {
			var content=summer.children('.note-editing-area').children('.panel-body').html();
			var realContent = content.replace(/(<([^>]+)>)/ig,"").replace(/&nbsp;/g," ").length;
			
			if(realContent>2000)
			{
				alert("2000자 까지만 입력가능합니다.\n"+(realContent-2000)+"글자를 줄여주세요.");
				$('#wr').text('글 등록 불가');
				$('#wr').attr('disabled','disabled');
			} 
			else
			{
				$('#wr').text('글 등록');
				$('#wr').removeAttr('disabled');
			}
			
			$('#content_feedback').text('2000자 제한 / 현재 : '+realContent+"자");
		});		
	</script>

	<script>
		$(document).ready(function() {
			var objDragAndDrop = $(".dragAndDropDiv");

			$(document).on("dragenter",".dragAndDropDiv",function(e) {
				e.stopPropagation();
				e.preventDefault();
				$(this).css('border','2px solid #0B85A1');
			});

			$(document).on("dragover", ".dragAndDropDiv",function(e) {
				e.stopPropagation();
				e.preventDefault();
			});

			$(document).on("drop",".dragAndDropDiv",function(e) {
				$(this).css('border','2px dotted #0B85A1');
				e.preventDefault();

				var files = e.originalEvent.dataTransfer.files;
				handleFileUpload(files,objDragAndDrop);
			});

			$(document).on('dragenter', function(e) {
				e.stopPropagation();
				e.preventDefault();
			});

			$(document).on('dragover',function(e) {
				e.stopPropagation();
				e.preventDefault();
				objDragAndDrop.css('border','2px dotted #0B85A1');
			});

			$(document).on('drop', function(e) {
				e.stopPropagation();
				e.preventDefault();
			});

			var fileArray = new Array();
			function handleFileUpload(files, obj)
			{
				alength = files.length;
				for (var i = 0; i < files.length; i++)
				{
					var fd = new FormData();
					fd.append('file', files[i]);

					var status = new createStatusbar(obj); //Using this we can set progress.
					status.setFileNameSize(files[i].name,files[i].size);

					var fileData = new Object();
					fileData.form = fd;
					fileData.stat = status;

					fileArray.push(fileData);
				}

			}

			var j = 1;
			var alength = 0;
			$('#beditComplete').click(function() {

				var board = new Object();
				board.bcode = $('input[name=bcode]').val();
				board.title = $('input[name=title]').val();
				board.content = $('textArea[name=content]').val();
				board.distinguish = $('select[name=distinguish]').val();
				board.writerid = $('input[name=writerid]').val();

				if (board.title == '')
				{
					alert("제목이 비어있습니다.");
					return;
				}

				$.ajax({
					type : "GET",
					url : "afteredit.do",
					data : board,
					success : function()
					{
						if (fileArray.length == 0) window.location.href = "boardOne.do?bcode=${board.bcode}";

						for (var i = 0; i < fileArray.length; i++)
							sendFileToServer(fileArray[i].form,fileArray[i].stat,j,fileArray.length);
					},
					error : function(request,status,error)
					{
						alert("code:"+ request.status+ "\nmessage:"+ request.responseText+ "\nerror:"+ error);
					}
				});


			});

			var rowCount = 0;
			var num=0;
			function createStatusbar(obj) {
				rowCount++;
				var row = "odd";

				if (rowCount % 2 == 0)	row = "even";

				this.statusbar = $("<div class='statusbar " + row +"' id='"+num+"'></div>");
				this.filename = $("<div class='filename'></div>").appendTo(this.statusbar);
				this.size = $("<div class='filesize'></div>").appendTo(this.statusbar);
				this.progressBar = $("<div class='progressBar'><div></div></div>").appendTo(this.statusbar);
				this.abort = $("<div class='abort'>중지</div>").appendTo(this.statusbar);
				this.fcancle = $("<div class='fcancle' id='fc"+(num++)+"'>취소</div>").appendTo(this.statusbar);

				obj.after(this.statusbar);

				this.setFileNameSize = function(name, size)
				{
					var sizeStr = "";
					var sizeKB = size / 1024;
					if (parseInt(sizeKB) > 1024)
					{
						var sizeMB = sizeKB / 1024;
						sizeStr = sizeMB.toFixed(2) + " MB";
					}
					else
					{
						sizeStr = sizeKB.toFixed(2) + " KB";
					}

					this.filename.html(name);
					this.size.html(sizeStr);
				}

				this.setProgress = function(progress)
				{
					var progressBarWidth = progress* this.progressBar.width() / 100;
					this.progressBar.find('div').animate({width : progressBarWidth}, 10).html(progress + "% ");

					if (parseInt(progress) >= 100) {this.abort.hide();}
				}

				this.setAbort = function(jqxhr)
				{
					var sb = this.statusbar;
					this.abort.click(function()
					{
						jqxhr.abort();
						sb.hide();
					});
				}

				this.fcancle.click(function(){
					fileArray.splice($(this).parent().attr('id'),1);
					alength--;
					$(this).parent().remove();
				});
			}



			function sendFileToServer(formData, status)
			{
				var uploadURL = "fileUp.do?bcode="+ $('input[name=bcode]').val(); //Upload URL
				var extraData = {}; //Extra Data.
				var jqXHR = $.ajax({
					xhr : function() {
						var xhrobj = $.ajaxSettings.xhr();

						if (xhrobj.upload) {
							xhrobj.upload.addEventListener('progress',function(event) {
								var percent = 0;
								var position = event.loaded|| event.position;
								var total = event.total;
								if (event.lengthComputable)
								{
										percent = Math.ceil(position/ total* 100);
								}//Set progress
								status.setProgress(percent);}, false);
						}
						return xhrobj;
				},
				url : uploadURL,
				type : "POST",
				contentType : false,
				processData : false,
				cache : false,
				data : formData,
				success : function(data) {
						status.setProgress(100);

						if (alength == j++) window.location.href = "boardOne.do?bcode=${board.bcode}";
				}
			});
			status.setAbort(jqXHR);
		}

	});

		function fileDelete(atcode)
		{
			$.ajax({
				type : "GET",
				url : "fileDelete.do?atcode="+atcode,
				success : function()
				{
					$('#fileList'+atcode).remove();
				},
				error : function(request,status,error)
				{
					alert("code:"+ request.status+ "\nmessage:"+ request.responseText+ "\nerror:"+ error);
				}
			});
		}
	</script>

	

	<script type="text/javascript">
		$(function(){
			$('#boardDelete').click(function(){
				alert("게시글이 삭제되었습니다.");
				window.location.href="boardDelete.do?bcode=${board.bcode}";
			});
		});
	</script>

</body>
</html>
