<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
 <head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.104.2">
    <title>Fixed top navbar example · Bootstrap v5.2</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/navbar-fixed/">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
	
	<!-- css -->
	<link href="/resources/css/style.css" rel="stylesheet">
	
	<!-- js -->
	<script src="/resources/js/reply.js"></script>
		
</head>
<body>

<%@include file="../common/header.jsp" %>
<script type="text/javascript">

window.addEventListener('load', function(){
	// 로그인한 아이디와 게시글의 작성자가 일치하면
	// 수정, 삭제 버튼에 이벤트를 적용
	if(${userId eq board.writer}){
		// 수정 페이지로 이동
		btnEdit.addEventListener('click', function(){
			viewForm.action='/board/edit';
			viewForm.submit();
		});
		
		// 삭제
		btnDelete.addEventListener('click', function(){
			viewForm.action='/board/delete';
			viewForm.submit();
		});
	}
	
	// 리스트
	btnList.addEventListener('click', function(){	
		viewForm.action='/board/list';
		viewForm.submit();
	});
	
	// 답글등록
	btnReplyWrite.addEventListener('click', function(){
		replyWrite();
	});
	
	// 댓글목록 조회 및 출력
	getReplyList();
	
	getFileList();

});

	function getFileList(){
		// /file/list/{bno}
		let bno = document.querySelector("#bno").value;
		fetch('/file/list/'+bno)
			.then(response => response.json())
			.then(map => viewFileList(map));
	}
	
	function viewFileList(map){
		console.log(map);
		let content= '';
		if(map.list.length > 0){
			content +=                                                                 
			'<div class="mb-3">                                             '
			+'  <label for="attachFile" class="form-label">첨부파일 목록</label> '
			+'  <div class="form-control" id="attachFile">                   '
		
			
			
			map.list.forEach(function(item, index){
				let savePath =  encodeURIComponent(item.savePath);
				content += "<a href = '/file/download?fileName="+savePath+"'>" + item.filename + '</a>' 
						+ '<br>';
			})
			
			content += '  </div>                                                       '
						+'</div>                                                         ';
			
		} else {
			content = "등록된 파일이 없습니다";
		}
		divFileupload.innerHTML = content;
	}
	


</script>

<main class="container">
  <div class="bg-light p-5 rounded">
    <h1>게시판 상세보기</h1>
    <p class="lead">부트스트랩을 이용한 게시판 만들기</p>
    <a class="btn btn-lg btn-primary" id='btnList' href="#" role="button">리스트 &raquo;</a>
  </div>
  <p></p>
  <!-- 상세보기 -->
  <div class="list-group w-auto">
  <form method="get" name="viewForm">
  <!-- 파라메터 -->
  <input type='text' name="pageNo" value="${param.pageNo }">
  <input type='text' name="searchField" value="${param.searchField }">
  <input type='text' name="searchWord" value="${param.searchWord }">
  <input type='text' name="bno" id="bno" value="${board.bno }">
  
  
  <input type="hidden" name="bno" id="bno" value="${board.bno }">
	<div class="mb-3">
	  <label for="title" class="form-label">제목</label>
	  <input type="text" readonly class="form-control" id="title" name="title" value='${board.title }'>
	</div>
	<div class="mb-3">
	  <label for="content" class="form-label">내용</label>
	  <textarea readonly class="form-control" id="content" name="content" rows="3" >${board.content }</textarea>
	</div>
	<div class="mb-3">
	  <label for="writer" class="form-label">작성자</label>
	  <input type="text" readonly class="form-control" id="writer" name="writer" value="${board.writer }">
	</div>
	
	<!-- 첨부파일 -->
	<div id="divFileupload">
	</div>
	
	<!-- 로그인한 사용자의 아이디와 게시글의 작성자가 일치하면 버튼을 노출 -->
	<c:if test="${userId eq board.writer }">
	<div class="d-grid gap-2 d-md-flex justify-content-md-center">
	<button type="button" id='btnEdit' class="btn btn-primary btn-lg">수정</button>
	<button type="button" id='btnDelete' class="btn btn-secondary btn-lg">삭제</button>
	</div>
	</c:if>
 </form>
</div>
<p></p>

	<!-- 댓글 작성자를 로그인한 사용자의 아이디를 입력 -->
  <input type="text" id="replyer" value="${userId }">
<div class="input-group">
  <span class="input-group-text">답글 작성</span>
  <input type="text" aria-label="First name" class="form-control" id="reply">
  <input type="button" id="btnReplyWrite"  aria-label="Last name" class="form-control" value="등록하기">
</div>
<!-- 댓글 리스트 -->
<div id='replyDiv'>

</div>

</main>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>