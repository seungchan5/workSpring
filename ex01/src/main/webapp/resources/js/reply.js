console.log('reply.js==========')

// get 방식 요청
function fetchGet(url, callback){
	try{
		// url 요청
		fetch(url)
			// 요청결과 json 문자열을 javascript 객체로 반환
			.then(response => response.json())
			// 콜백함수 실행
			.then(map => callback(map));
	} catch(e) {
		console.log('fetchGet', e)
	}
}

// post 방식 요청
function fetchPost(url, obj, callback){
	try{
		// url 요청
		fetch(url, {method : 'post', headers : {'Content-Type' : 'application/json'}, body : JSON.stringify(obj)})
			// 요청결과 json 문자열을 javascript 객체로 반환
			.then(response => response.json())
			// 콜백함수 실행
			.then(map => callback(map));
	} catch(e) {
		console.log('fetchPost', e)
	}
}

// 댓글 조회 및 출력
function getReplyList(page){
	
	/**
	 * falsey : false, 0, "", NaN, undefined, null
	 * 
	 * falsey한 값 이외의 값이 들어있으면 true 반환
	 * 
	 * page에 입력된 값이 없으면 1을 세팅
	 */
	if(!page){
		page = 1;
		
	}

	let bno = document.querySelector("#bno").value;
	console.log('bno : ' + bno)
	
	console.log('/reply/list/' + bno + '/' + page);
	console.log(`/reply/list/${bno}/${page}`);
	
	// url : 요청경로
	// callback : 응답결과를 받아 실행시킬 함수
	fetchGet(`/reply/list/${bno}/${page}`, replyView)
}

function replyView(map){
	let list = map.list;
	let pageDto = map.pageDto;
	
	console.log(list);
	console.log("pageDto======================", pageDto);
	
	
	if(list.length == 0){
		replyDiv.innerHTML = '등록된 댓글이 없음'
	} else {
		let replyDivStr = '';
		
		replyDivStr = ''
			+ '<table class="table text-break text-center">                '
			+ '  <thead>                            '
			+ '    <tr>                             '
			+ '      <th scope="col" class="col-1">#</th>         '
			+ '      <th scope="col" class="col-9">댓글</th>     '
			+ '      <th scope="col" class="col-2">작성자</th>      '
			+ '    </tr>                            '
			+ '  </thead>                           '
			+ '  <tbody>                            ';
		
		list.forEach(reply => {
			replyDivStr +=
			  '    <tr id="tr'+reply.rno+'" data-value="'+reply.reply+'">                             '
			+ '      <th scope="row">'+reply.rno+'</th>         '
			+ '      <td class="text-start">'+reply.reply;
			
			// replyer.value : 로그인한 아이디
			// reply.replyer : 답글 작성자
			if(replyer.value == reply.replyer){
				replyDivStr +=
					' <i class="fa-solid fa-pencil" onclick="replyEdit('+reply.rno+')"></i>' 
				+' <i class="fa-solid fa-trash-can" onclick="replyDelete('+reply.rno+')"></i> ' 
			}
			replyDivStr +=
			'</td>                  '
			+ '      <td>'+reply.replyer+ '<br>'+ reply.replyDate+'</td>                  '

			+ '    </tr>                            ';
		})
		
			replyDivStr +=
			 '  </tbody>                           '
			+ '</table>								';
		
		// 화면에 출력
		replyDiv.innerHTML = replyDivStr;
		
		// 페이지 블럭                                                                 `
		let pageBlock =                                                                
			 `<nav aria-label="...">                                                   `
		  	+`	<ul class="pagination justify-content-center">                         `;
		  	
		  	if(pageDto.prev){
				pageBlock +=
		  	`		<li class="page-item" onclick="getReplyList(${pageDto.startNo-1})">                                    `
		  	+`			<a class="page-link">Previous</a>                              `
		  	+`		</li>                                                              `;
		  	}
		  	
			for(i=pageDto.startNo; i<=pageDto.endNo;i++){
				let active = (pageDto.cri.pageNo==i)?"active":"";
				pageBlock +=`<li class="page-item ${active}" onclick="getReplyList(${i})"><a class="page-link" href="#">${i}</a></li>     `
			}
		  	//+`		<li class="page-item active" aria-current="page">                  `
		  	//+`			<a class="page-link" href="#">2</a>                            `
		  	//+`		</li>                                                              `
		  	//+`		<li class="page-item"><a class="page-link" href="#">3</a></li>     `
			if(pageDto.next){	
			pageBlock +=
			`		<li class="page-item" onclick="getReplyList(${pageDto.endNo+1})">                                             `
		  	+`			<a class="page-link" href="#">Next</a>                         `
		  	+`			</li>                                                          `;
			}
		  	pageBlock +=
		  	`	</ul>                                                                  `
		  	+`</nav> `;
		
		replyDiv.innerHTML+= pageBlock;
		                                                                               
		}
	
}

function getPage(page){
	document.querySelector('#pageNo').value=page;
	getReplyList();
}

// 답글 등록하기
function replyWrite(){
	// 답글 작성시 필요한 데이터 수집 - bno, reply, replyer
	let bno = document.querySelector('#bno').value;
	let reply = document.querySelector('#reply').value;
	let replyer = document.querySelector('#replyer').value;
	
	// 전달할 객체로 생성
	let obj = {bno : bno, reply : reply, replyer : replyer};
	
	console.log(obj);
	// url : /reply/insert
	// url : 요청경로
	// obj : JSON형식으로 전달할 데이터
	// callback : 콜백함수(응답결과를 받아서 처리할 함수)
	fetchPost('/reply/insert', obj, replyRes);
}

// 답글 등록, 수정, 삭제 결과를 처리하는 함수
function replyRes(map){
	console.log(map);
	// 성공 : 리스트 조회 및 출력
	if(map.result == "success"){
		getReplyList();
	} else{
		// 실패 : 메세지 출력
		alert(map.message);
	}
}

// 댓글 삭제
function replyDelete(rno){
	console.log('rno', rno);
	fetchGet('/reply/delete/' + rno, replyRes);
}

function replyEdit(rno){
	let tr = document.querySelector("#tr"+rno);
	let replyTxt = tr.dataset.value;

	tr.innerHTML = '<td colspan="3">'                                                                                  
	+'	<div class="input-group">                                                                                      '
	+'  		<span class="input-group-text">답글 수정</span>                                                        '
	+'  		<input type="text" aria-label="First name" class="form-control" id="reply'+rno+'" value="'+replyTxt+'">                            '
	+'  		<input type="button" onclick="replyEditAction('+rno+')" aria-label="Last name" class="form-control" value="수정하기"> '
	+'  	</div> '
	+ '</td>';
		
}

function replyEditAction(rno){
	// 파라메터 수집
	let reply = document.querySelector("#reply"+rno).value;
	
	
	// 전송할 데이터를 JSON 객체로 생성
	let obj = {rno : rno, reply : reply}
	
	// 서버에 요청
	fetchPost('/reply/editAction', obj, replyRes);
	
}