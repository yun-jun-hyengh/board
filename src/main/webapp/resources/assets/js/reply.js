/**
 * 
 * Javascript 모듈화 
 * 
 * 함수들을 하나의 모듈처럼 묶음으로 구성하는 것을 의미한다 
 * 화면 내에서 Javascript를 처리하다 보면 이벤트 처리, DOM, Ajax 처리 등 
 * 복잡하게 섞여서 유지보수가 힘들다. 따라서 Javascript 코드를 여러 부품으로 분리하여 조립하는 형식으로 설계
 */
 
 console.log("ReplyModule....");
 let replyService = (function(){
 	// 추가하기 
 	function add(reply, callback){
 		console.log("add reply......");
 		
 		$.ajax({
 			url: "/replies/new",
 			type: "post",
 			data: JSON.stringify(reply),
 			contentType: "application/json; charset=utf-8",
 			success: function(result){
 				if(callback){
 					callback(result);
 				}
 			}
 		});
 	}
 	
 	// 목록보기 
 	// param은 게시글 번호와 요청한 페이지를 받아올 수 있는 객체이다 
 	function getList(param, callback){
 		let bno = param.bno;
 		let page = param.page || 1; // let variable = a | b; --> a가 값이 없으면 b로 사용된다.
 		
 		// getJSON()은 빠르고 편하게 요청 후 JSON 데이터를 방아오기 위해 사용한다 
 		// 전송방식은 GET방식이다.
 		// .json을 붙힌 이유는 xml이 default로 설정되어 있기 때문에 json 형식으로 전달받기 위함 !!
 		$.getJSON("/replies/" + bno + "/" + page + ".json", function(result){
 			if(callback){
 				callback(result);
 			}
 		}).fail(function(xhr, status, err){
 			if(error){
 				error(err);
 			}
 		});
 	}
 	
 	// 댓글 삭제 
 	function remove(rno, callback){
 		$.ajax({
 			url: "/replies/" + rno,
 			type: "delete",
 			success: function(result){
 				if(callback){callback(result);}
 			}
 		});
 	}
 	
 	// 댓글 수정 
 	function modify(reply, callback){
 		$.ajax({
 			url: "/replies/" + reply.rno,
 			type: "put",
 			data: JSON.stringify(reply),
 			contentType: "application/json; charset=utf-8",
 			success: function(result){
 				if(callback) {callback(result);}
 			}
 		});
 	}
 	
 	// 댓글 1개 조회 
 	function getReply(rno, callback){
 		$.get("/replies/" + rno, function(reply){
 			if(callback) {callback(reply);}
 		});
 	}
 	
 	// 댓글 작성시간 
 	// 작성 시간을 기준으로 24시간 이후는 년-월-일
 	// 24시간 이전은 시:분:초 로 만든다 
 	function displayTime(timeValue){
 		let today = new Date();
 		let date = new Date(timeValue);
 		let gap = today.getTime() - date.getTime();
 		
 		if(gap < 1000 * 60 * 60 * 24){
 			let hh = date.getHours();
 			let mm = date.getMinutes();
 			let ss = date.getSeconds();
 			
 			return [hh < 10 ? "0" + hh : hh, mm < 10 ? "0" + mm : mm, ss < 10 ? "0" + ss : ss].join(':');
 		} else {
 			let yy = date.getFullYear();
 			let mm = date.getMonth() + 1;
 			let dd = date.getDate();
 			
 			return [yy, mm < 10 ? "0" + mm : mm, dd < 10 ? "0" + dd : dd].join('-');
 		}
 	}
 	
 	return {add: add, getList: getList, remove: remove, modify: modify, getReply: getReply, displayTime: displayTime}
 })();