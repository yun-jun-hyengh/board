<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Board</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="/resources/assets/css/main.css" />
		<style>
			body {transform: scale(0.8); margin-top: -50px;}
			.uploadResult {
				width: 100%;
			}
			
			.uploadResult ul {
				display: flex;
				justify-content: center;
			}
			
			.uploadResult ul li {
				list-style: none;
			}
		</style>
	</head>
	<body class="is-preload">
		<!-- Main -->
		<div id="main">
			<div class="wrapper">
				<div class="inner">

					<!-- Elements -->
					<header class="major">
						<h1>Board</h1>
						<p>게시글 등록</p>
					</header>
					<!-- Table -->
					<h3><a href="/board/list" class="button small">목록 보기</a></h3>
					<div class="content">
						<div class="form">
							<form method="post" action="/board/register" id="registForm" name="registForm" enctype="multipart/form-data">
								<div class="fields">
									<div class="field">
										<h4>제목</h4>
										<input name="title" placeholder="Title" type="text" />
									</div>
									<div class="field">
										<h4>내용</h4>
										<textarea name="content" rows="6" placeholder="Content" style="resize:none"></textarea>
									</div>
									<div class="field">
										<h4>작성자</h4>
										<input name="writer" placeholder="Writer" type="text" />
									</div>
									<div class="field">
										<h4>첨부파일</h4>
										<input name="multipartFiles" type="file" multiple/>
									</div>
									<div class="field">
										<div class="uploadResult">
											<ul></ul>
										</div>
									</div>
								</div>
								<ul class="actions special">
									<li><input type="submit" class="button" value="등록" /></li>
								</ul>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div> 
	</body>
	<!-- Scripts -->
	<script src="/resources/assets/js/jquery.min.js"></script>
	<script src="/resources/assets/js/jquery.dropotron.min.js"></script>
	<script src="/resources/assets/js/browser.min.js"></script>
	<script src="/resources/assets/js/breakpoints.min.js"></script>
	<script src="/resources/assets/js/util.js"></script>
	<script src="/resources/assets/js/main.js"></script>
	<script>
		$(document).ready(function(e){
			var $uploadResult = $(".uploadResult ul");
			var regex = new RegExp("(.*/)\.(exe|sh|zip|alz)$");
			var maxSize = 1024 * 1024 * 20; //20MB
			
			function showUploadResult(files){
				var str = "";
				$(files).each(function(i, file){
					if(!file.fileType){
						str += "<li data-filename='" + file.fileName + "' data-uuid='" + file.uuid + "' data-uploadpath='" + file.uploadPath + "' data-filetype='" + file.fileType + "'>";
						str += "<div>";
						str += "<img src='/resources/images/attach.png' width='100'>";
						str += "</div>";
						str += "<span>" + file.fileName + "</span>"
						str += "</li>";
						
					}else{
						var fileName = encodeURIComponent(file.uploadPath + "/t_" + file.uuid + "_" + file.fileName);
						console.log(file.fileType);
						str += "<li data-filename='" + file.fileName + "' data-uuid='" + file.uuid + "' data-uploadpath='" + file.uploadPath + "' data-filetype='" + file.fileType + "'>";
						str += "<div>";
						str += "<img src='/display?fileName=" + fileName + "' width='100'>";
						str += "</div>";
						str += "<span>" + file.fileName + "</span>"
						str += "</li>";
					}
				});
				$uploadResult.append(str);
			}
			
			$("input[type='file']").change(function(e){
				$(".uploadResult ul li").remove();
				
				var formData = new FormData();
				var $inputFile = $(this);
				var files = $inputFile[0].files;
				console.log(files);
				
				for(let i=0; i<files.length; i++){
					if(!checkExtension(files[i].name, files[i].size)){
						return false;
					}
					formData.append("multipartFiles", files[i]);
				}
				$.ajax({
					url: '/upload',
					processData: false,
					contentType: false,
					data: formData,
					type: "post",
					dataType: "json",
					success: function(result){
						console.log(result);
						showUploadResult(result);
					}
				});
			});
			
			$("input[type='submit']").on("click", function(e){
				e.preventDefault();
				var $form = $(document.registForm);
				var str = "";
				
				$(".uploadResult ul li").each(function(i, li){
					str += "<input type='hidden' name='files[" + i + "].uuid' value='" + $(li).data("uuid") +"'>";
					str += "<input type='hidden' name='files[" + i + "].uploadPath' value='" + $(li).data("uploadpath") +"'>";
					str += "<input type='hidden' name='files[" + i + "].fileName' value='" + $(li).data("filename") +"'>";
					str += "<input type='hidden' name='files[" + i + "].fileType' value='" + $(li).data("filetype") +"'>";
				});
				$form.append(str).submit();
			});
			
			
			function checkExtension(fileName, fileSize){
				if(regex.test(fileName)){
					alert("업로드할 수 없는 파일의 형식입니다.")
					return false;
				}	
				
				if(fileSize >= maxSize) {
					alert("파일 사이즈 초과");
					return false;
				}
				
				return true;
			}
		});
	</script>
</html>