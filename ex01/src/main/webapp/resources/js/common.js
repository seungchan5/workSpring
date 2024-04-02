console.log("common.js");

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
