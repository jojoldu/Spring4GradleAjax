/**
 * 공통유틸 자바스크립트
 */


//페이징정보 설정
function setPagingInfo(pageIndex){
	var pageIndex = pageIndex;
	var pageSize = $("#pageSize").val();
	var obj = {
			pageSize  : pageSize,
			pageIndex : pageIndex,
			firstRow  : (pageIndex-1)*pageSize,
	}
	return obj;
}

//페이징 정보 호출
function getPagingInfo(pageIndex){
	var pagingInfo = setPagingInfo(pageIndex);
	$.ajax({
		type:"POST",
		url:"getPagingInfo.json",
		data:pagingInfo,
		dataType:"json",
		success:function(data){
			makeAjaxPaging(data.pagingInfo);
		}
	} );	
}

//ajax 페이징
function makeAjaxPaging(pagingInfo){

	/*
	1) 현재 index와 pageSize를 서버에 전송한다.
	2) 서버에서는 totalCount를 가져오고 이를 totalCount/pageSize하여 페이지 개수를 계산해낸다.
	3) list 조회
	4) 2),3) 결과 클라이언트에 전송
	5) 4)을 토대로 페이징 html코드 생성 & 페이지개수가 pageScope를 넘어가면 < > 생성
	6) 각 페이지버튼마다 click="getList(pageIndex)" 추가

	 */

	var totalPageCount =parseInt(pagingInfo.totalPageCount);//전체 페이지개수
	var pageScope = parseInt(pagingInfo.pageScope);//화면에 보여줄 페이지 범위
	var pageSize = parseInt(pagingInfo.pageSize);//한 페이지당 보여줄 글개수
	var pageIndex = parseInt(pagingInfo.pageIndex);//현재 페이지
	var lastPageIndex=parseInt(pageIndex+pageScope-1);//현재화면에서 나와야할 마지막 페이지번호

	
	if(lastPageIndex>totalPageCount){//전체페이지수 보다 lastPageIndex가 크면
		lastPageIndex=totalPageCount;
	}

	var paginationHtml='<ul class="pagination pagination" style="float:right; margin:2px;">';
	
	paginationHtml+='<li><a href="javascript:void(0);" class="disabled" id="prev"><</a></li>';
	
	for(var i=1;i<=lastPageIndex;i++){
		paginationHtml+='<li><a href="javascript:void(0);" class="pageNo" id="'+i+'">'+i+'</a></li>';	
	}
	paginationHtml+='<li><a href="javascript:void(0);" class="disabled" id="next">></a></li></ul>';
	
	
	$('#pagination').html('');
	$('#pagination').append(paginationHtml);
	
	
	$("#"+pageIndex).addClass("active");//현재페이지 활성화
	
	$('.pageNo').each(function(){//각 페이지번호마다 클릭이벤트 할당
		$(this).click(getList(this.id));
	})
	
	if(totalPageCount > (pageIndex + pageScope - 1)){//현재 페이지범위보다 전체페이지개수가 더 클경우
		$("#next").removeClass("disabled");
		$("#next").click(makeListAndPaging(lastPageIndex+1));
	}
	if(pageIndex > pageScope){//현재페이지가 pageScope 보다 클경우
		$("#prev").removeClass("disabled");
		$("#prev").click(makeListAndPaging(pageIndex-pageScope));
	}
}

//페이징 생성 및 목록출력
function makeListAndPaging(pageIndex){
	getPagingInfo(pageIndex);
	getList(pageIndex);
}

//영문, 숫자만 입력되었는지 체크
function checkEng(str) {
	var flag=true;  
	var regResult = (/^[0-9a-z]*$/i).test(str); //영문, 숫자 이외엔 없는지
	if(!regResult || str===""){
		flag = false;       
	}
	return flag;
}


//댓글등록시 글자수 제한
function limitText(limitField, limitCount){
	if (limitField.value.length > limitCount) {
		limitField.value = limitField.value.substring(0, limitCount);
		alert("댓글은 10,000자 이하로만 입력이 가능합니다.");
	} 
}

