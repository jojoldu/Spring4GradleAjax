/**
 * 공통유틸 자바스크립트
 */


//ajax 페이징
function makeAjaxPaging(pagingInfo){
	
	/*
	1) 현재 index와 pageSize를 서버에 전송한다.
	2) 서버에서는 totalCount를 가져오고 이를 totalCount/pageSize하여 페이지 개수를 계산해낸다.
	3) limit (pageIndex-1)*10, pageIndex*pageSize 하여 list 조회
	4) 2),3)를 json으로 클라이언트에 전송
	5) 4)을 토대로 페이징 html코드 생성 & 페이지개수가 pageScope를 넘어가면 < > 생성
	6) 각 페이지버튼마다 onclick="getList(pageIndex)" 추가
	
	*/
	
	var totalPageCount = pagingInfo.totalPageCount;//전체 페이지개수
	var pageScope = pagingInfo.pageScope;//화면에 보여줄 페이지 범위
	var pageSize = pagingInfo.pageSize;//한 페이지당 보여줄 글개수
	var pageIndex = pagingInfo.pageIndex;//현재 페이지

	var pageId='page';
	var pagingHtml='<ul class="pagination pagination" style="float:right; margin:0px;">';
	if(totalPageCount>pageScope){//전체 페이지수가 페이지범위를 넘어설때는 <,> 버튼을 추가하여 페이징 ui를 만든다.
		
		if(pageIndex<=pageScope){//현재 페이지가 pageScope 이하 페이지라면 '<' 버튼 비활성화
			pagingHtml+='<li><a href="javascript:void(0);" class="disabled"><</a></li>';	
		}else{
			pagingHtml+='<li><a href="javascript:void(0);"><</a></li>';
		}

		for(var i=pageIndex;i<=pageScope;i++){
			if(){
				pagingHtml+='<li><a href="javascript:void(0);" onclick="getList('+i+')">'+i+'</a></li>';
			}else{
				pagingHtml+='<li><a href="javascript:void(0);" onclick="getList('+i+')">'+i+'</a></li>';				
			}
				
		}
		pagingHtml+='<li><a href="javascript:void(0);" onclick="getList()">></a></li>';
		
	}else{//전체페이지수가 페이지 범위를 넘지 않을 때
		pagingHtml+='<li><a href="javascript:void(0);" class="disabled"><</a></li>';	
		for(var i=1;i<=totalPageCount;i++){
			if(i==pageIndex){//현재 페이지일경우 활성화표시(active)
				pagingHtml+='<li><a href="javascript:void(0);" onclick="getList('+i+')" class="active" id="'+(pageId+i)+'">'+i+'</a></li>';
			}else{
				pagingHtml+='<li><a href="javascript:void(0);" onclick="getList('+i+')" id="'+(pageId+i)+'">'+i+'</a></li>';
			}
				
		}
		pagingHtml+='<li><a href="javascript:void(0);" class="disabled">></a></li>';
	}
	
}