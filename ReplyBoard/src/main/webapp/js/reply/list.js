/**
 * 댓글 관련 자바스크립트
 */

var parent;
var validPassword=false;
$(function() {

	$("#pageSize").val('10');
	
	//댓글 목록조회
	makeListAndPaging(1);
	
	//댓글입력 
	$("#writeBtn").click(addReply);
	
	//페이징 사이즈 조절
	$("#pageSize").change(function(){
		makeListAndPaging(1);
	});
	
});


//댓글 목록 조회
function getList(pageIndex){
	var pageIndex=pageIndex;
	$('#page'+pageIndex).addClass('active');//현재 선택한 페이지번호 활성화
	
	$('#replyList tbody').html('');
	var listVO = setPagingInfo(pageIndex);
	$.ajax({
	    headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		type:"POST",
		url:"list.json",
		data:JSON.stringify(listVO),
		dataType:"json",
		success:function(data){
			drawTable(data.list, data.loginId);
		}
	} );
}

//table 생성
function drawTable(list, loginId) {
    for (var i = 0; i < list.length; i++) {
        drawRow(list[i]);
    }
    
	//로그인 유저
	var loginId = loginId;//json 전송받은 로그인 유저	
	
	//권한관련
	$(".authBtn").each(function(){
		var writer = $(this).prop("name");
		if(loginId !== writer){
			$(this).addClass("hide");
		}
	});
}

//table row 생성
function drawRow(rowData) {
	var id=rowData.no;
	var formId = 'form'+id;
	var textId = 'text'+id;
	var updateId = 'update'+id;
	var btnHtml = '';
	var replyForm = $(".replyForm").html();
	var updateForm = $(".updateForm").html();
	
	//들여쓰기
	var depth = (100-(rowData.depth*3))+"%;";
	
	//table row 생성
	var row = $('<tr>')
    $("#replyList tbody").append(row); 
	
	if(rowData.imageName!=null){
		row.append($('<td align="center"><img src="../resources/images/' + rowData.imageName + '"></td>')); 
	}else{
		row.append($('<td align="center"></td>')); 
	}
	row.append($('<td align="center"><div class="row" style="float:right; width:'+depth+'"><input type="text" class="form-control" readonly value="'+ rowData.content + '"id="'+textId+'"></div><br>'+'<div class="row hide" style="float:right; width:'+depth+'"id="'+updateId+'">'+updateForm+'</div><div class="row hide" style="float:right; width:'+depth+'"id="'+formId+'">'+replyForm+'</div></td>'));
    row.append($('<td align="center">' + rowData.modifyDate.substr(0,16) + '</td>'));
    row.append($('<td align="center">' + rowData.writer + '</td>'));
    row.append($('<td align="center"><button type="button" class="btn btn-info btn-sm" onclick="openAddForm('+id+')" ><span class="glyphicon glyphicon-comment"></span></button></td>'));
    row.append($('<td align="center"><button type="button" class="btn btn-primary btn-sm authBtn " onclick="openUpdateForm('+id+')" name="'+rowData.writer+'"><span class="glyphicon glyphicon-edit"></span></button></td>'));
    row.append($('<td align="center"><button type="button" class="btn btn-danger btn-sm authBtn " onclick="confirmDelete('+id+')" name="'+rowData.writer+'"><span class="glyphicon glyphicon-trash"></span></button></td></tr>'));

}


//작성폼 초기화
function resetForm(){
	$("#content").val("");
	$("#image").val("");
}

//글 작성
function addReply(e){
	var no = $(e).parent().parent().parent()[0].id.substr(4);
	
	var formData = new FormData();
	formData.append("parent",no);
	formData.append("content", $(e).parent().parent().siblings("textarea").val());
	formData.append("image", $(e).parent().parent().parent().children(".row").children(".file").children("input:file")[0].files[0]);
	
	$.ajax({
		type:"POST",
		url:"add",
		data:formData,
		dataType:"json",
		processData : false,
	    contentType: false,		
		success:function(data){
			if(data===true){
				alert("댓글이 등록되었습니다!");
				resetForm();
				getList(1);
			}else{
				alert("댓글 등록이 실패하였습니다.");
			}

		}
	} );
}

//답글작성폼 열기
function openAddForm(no){
	var formId ='#form'+no;
	$(formId).removeClass("hide");
}
//답글 작성폼 닫기
function cancleReply(e){
	$(e).parent().parent().parent().addClass("hide");
}

//글 수정폼 열기
function openUpdateForm(no){
	var textId ='#text'+no;
	var updateId = '#update'+no;
	$(textId).prop('readonly',false);
	$(updateId).removeClass("hide");
}

//글 수정
function updateReply(e){
	var no = $(e).parent().parent().parent()[0].id.substr(6);
	var pageIndex = $(".active").val();
	
	var formData = new FormData();
	formData.append("no",no);
	formData.append("content", $("#text"+no).val());
	formData.append("image", $(e).parent().parent().parent().children(".row").children(".file").children("input:file")[0].files[0]);
	
	$.ajax({
		type:"POST",
		url:"update",
		data:formData,
		dataType:"json",
		processData : false,
	    contentType: false,		
		success:function(data){
			if(data===true){
				alert("댓글이 수정되었습니다!");
				resetForm();
				getList(pageIndex);
			}else{
				alert("댓글 수정이 실패하였습니다.");
			}
		}
	} );
}

//글 수정폼 닫기
function closeUpdateForm(e){
	$(e).parent().parent().parent().siblings("text").prop("readonly",true);
	$(e).parent().parent().parent().addClass("hide");
}


//댓글 삭제 확인
function confirmDelete(no){
	var no=no;
	bootbox.confirm("삭제하시겠습니까?", function(confirmed) {
		if(confirmed) {
			deleteReply(no);
		}
	});	
}

//글 삭제
function deleteReply(no){
	var no=no;
	var pageIndex = $(".active").val();
	
	var deleteVO = {
			no:no
	}
	
	$.ajax({
		headers: { 
			'Accept': 'application/json',
			'Content-Type': 'application/json' 
		},
		type:"POST",
		url:"delete",
		data:JSON.stringify(deleteVO),
		dataType:"json",
		success:function(data){
			if(data===true){
				alert("삭제되었습니다.");
				getList(pageIndex);//active 된 pageIndex
			}else{
				alert("삭제가 실패하였습니다.");
			}

		}
	} );
}

//기존 비밀번호 확인
function checkPassword(){
	var password = $("#password").val();
	
	$.post("user/checkPassword",{password:password},function(data){
		if(data.resultCode){
			alert("비밀번호 확인");
			validPassword=true;
		}
	});
}

//사용자정보 수정
function updateUserInfo(){
	var password=$("#newPassword").val();
	
	var email=$("#email").val();
	
	var updateVO = {
			password	:password,
			email		:email
	}
	
	$.post("user/update",updateVO,function(data){
		
	});
}

//탈퇴확인


//탈퇴