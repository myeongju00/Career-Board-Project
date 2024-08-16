function submitHandler(event){
	let jobCheck = document.querySelector('#jobCheck');
	let position = document.querySelector('#position');
	let title = document.querySelector('#title');
	let tag = document.querySelector('#tag');
	let content = document.querySelector('#content');
	let submitBtn = document.querySelector('#submitBtn');
	
	if(submitBtn.value === '질문하기'){
		if(!jobCheck.value){
			position.value=null;
		}
			
		if(!title.value.trim() || !content.value.trim()){
			alert('필수 입력양식을 입력해 주세요.');
			event.preventDefault();
		}
			
		if(!tag.value.startsWith('#') || !tag.value.trim()){
			alert('#을 구분자로 입력해주세요');
			event.preventDefault();
		}
	}
	
}

function searchHandler(event){
	let searchValue = document.querySelector('#searchValue');
	
	if(!searchValue.value.trim()){
		alert('검색어를 입력해 주세요');
		event.preventDefault();
	}
}

function init(){
	document.querySelector('#questionForm').addEventListener('submit', submitHandler);
	document.querySelector('#searchForm').addEventListener('submit', searchHandler);
}


window.addEventListener('load',init);