<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>

<h2>답글달기</h2>

<input type="text" name="bno" id="bno" value="83">
<input type="text" name="page" id="page" value="1">
 <input type="text" id="replyer"><br>

<div class="input-group mb-3">
  <input type="text" id="reply" class="form-control" placeholder="Recipient's username" aria-label="Recipient's username" aria-describedby="basic-addon2">
  <span class="input-group-text" id="btnWrite">댓글작성</span>
</div>
<div id="replyDiv"></div>

</body>

<script type="text/javascript">
	//버튼이 생성되고 나서 이벤트를 부여
	window.onload = function(){
		// 리스트 조회 및 출력
		getList();
		
		btnWrite.addEventListener('click', function(){
			// 1. 파라메터 수집
			let bno = document.querySelector('#bno').value;
			let reply = document.querySelector('#reply').value;
			let replyer = document.querySelector('#replyer').value;
			
			// 2. 전송할 데이터를 javascript 객체로 생성
			let replyObj = {
					bno : bno,
					reply : reply,
					replyer : replyer
			};
			
			// 서버에 요청
			fetchPost('/reply/insert',replyObj, replyWriteRes);
			
			/*
			// 3. 객체를 JSON 타입으로 변환
			let replyJson = JSON.stringify(replyObj);
			
			// 4. 서버에 요청
			fetch('/reply/insert',{method : 'post', headers : {'Content-Type' : 'application/json'}, body : replyJson})
				// 5. 응답처리
				.then(response => response.json())
				.then(map => replyWriteRes(map));
			*/
		})
		
	}
	
	// 서버에 리스트 요청
	function getList(){
		let bno = document.querySelector("#bno").value;
		let page = document.querySelector("#page").value;
		
		console.log(bno);
		
		fetchGet('/reply/list/'+bno + '/' + page, replyView);
		/*
		// url요청 결과를 받아 옵니다
		fetch('/reply/list/'+bno + '/' + page)
			// response.json() : 요청 결과를 json object형식으로 변환
			.then(response => response.json())
			// 반환받은 오브젝트를 이용하여 화면에 출력합니다
			.then(map => replyView(map));
		*/
	}
	
	function replyWriteRes(map){
		if(map.result=='success'){
			// 등록 성공
			getList();
		}else{
			// 등록 실패
			alert(map.message);
		}	
	}
	
	// 리스트를 화면에 출력
	function replyView(map){
		
		let list = map.list;
		let pageDto = map.pageDto;
		
		// 콘솔창에 리스트 출력
		console.log(list);
		console.log(pageDto);
		
		// div 초기화
		replyDiv.innerHTML = '';
		
		// 댓글 리스트로부터 댓글을 하나씩 읽어와서 div에 출력
		list.forEach((reply, index)=>{
			// 답글을 DIV에 출력
			replyDiv.innerHTML+= 
				 '<figure id="reply'+index+'" data-value="'+reply.reply+'" data-rno="'+reply.rno+'">'
				+  '<blockquote class="blockquote">'
				+   '<p>'+reply.reply 
				+ '<i class="fa-regular fa-pen-to-square" onclick="replyEdit('+ index +','+reply.rno+');"></i>'
				+ '<i class="fa-solid fa-delete-left" onclick="replyDelete('+ reply.rno +')"></i>'
				+' </p>'
				+  '</blockquote>'
				+  '<figcaption class="blockquote-footer">'
				+   '' + reply.replyer + '<cite title="Source Title"> ' + reply.replyDate + '</cite>'
				+  '</figcaption>'
				+'</figure>';
		
		});
		let pageBlock = '';
		// 페이지 블럭 생성
		pageBlock += 
			 '<nav aria-label="...">'
			+  '<ul class="pagination">'
			
			// prev 버튼
			if(pageDto.prev){
				pageBlock +=
			    '<li class="page-item" onclick="getPage('+ (pageDto.startNo-1) +')">'
			+      '<span class="page-link">Previous</span>'
			+    '</li>';
			}
			
			// 반복해서 페이지 번호를 출력
			for(i=pageDto.startNo; i<=pageDto.endNo;i++){
				let activeStr =  (pageDto.cri.pageNo == i) ? "active" : "";
			// 페이지 번호 생성
			pageBlock +=
				'<li class="page-item '+activeStr+'" onclick="getPage('+i+')"><a class="page-link" href="#">'+ i +'</a></li>'				
			}
			
			//+    '<li class="page-item active" aria-current="page">'
			//+      '<span class="page-link">2</span>'
			//+    '</li>'
			//+    '<li class="page-item"><a class="page-link" href="#">3</a></li>'
			
			// next 버튼
			if(pageDto.next){	
			pageBlock +=
			    '<li class="page-item" onclick="getPage('+ (pageDto.endNo + 1) +')">'
			+      '<a class="page-link" href="#">Next</a>'
			+    '</li>'
			}
			
			pageBlock +=
			  '</ul>'
			+ '</nav>';
		
			replyDiv.innerHTML+= pageBlock;	
		
	}
	
	function getPage(page){
		document.querySelector('#page').value=page;
		getList();
	}
	
	function replyDelete(rno){
		// url요청 결과를 받아 옵니다
		fetch('/reply/delete/' + rno)
			// response.json() : 요청 결과를 json object형식으로 변환
			.then(response => response.json())
			// 반환받은 오브젝트를 이용하여 화면에 출력합니다
			.then(map => replyWriteRes(map));
	}
	
	// 수정화면 보여주기
	function replyEdit(index, rno){
		let editbox = document.querySelector('#reply' + index);
		let replyTxt = editbox.dataset.value;
		editbox.innerHTML = ''
		+'<div class="input-group mb-3">'
		 + '<input type="text" id="editReply'+rno+'" value="'+replyTxt+'" class="form-control" aria-describedby="basic-addon2">'
		 + '<span class="input-group-text" id="btnWrite" onclick="replyEditAction('+rno+');">수정하기</span>'
		+'</div>';
	}
	
	function replyEditAction(rno){
		// 1. 파라메터 수집
		let reply = document.querySelector('#editReply'+rno).value; 
		
		// 2. 전송할 데이터를 javascript 객체로 생성
		let replyObj = {
				rno : rno,
				reply : reply
		};
		
		// 3. 객체를 JSON 타입으로 변환
		let replyJson = JSON.stringify(replyObj);
		
		// 4. 서버에 요청
		fetch('/reply/editAction',{method : 'post', headers : {'Content-Type' : 'application/json'}, body : replyJson})
			// 5. 응답처리
			.then(response => response.json())
			.then(map => replyWriteRes(map));
	}
	
	
	
	
</script>
<script src="https://kit.fontawesome.com/8c7c2f1357.js" crossorigin="anonymous"></script>
</html>