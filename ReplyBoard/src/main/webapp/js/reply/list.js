/**
 * 댓글 관련 자바스크립트
 */

var parent;
var validPassword=false;
var activePageIndex;

$(function() {

	$("#pageSize").val('30');
	
	//댓글 목록조회
	makeListAndPaging(1);
	
	//댓글입력 
	$('#writeBtn').click(function(){
		addReply(this);
	});
	
	//페이징
	$("#pagination").on('click','.pageNo',function(){
		getList(this.id);
	});
	
	//페이징 사이즈 조절
	$("#pageSize").change(function(){
		makeListAndPaging(activePageIndex);
	});
	
	//비밀번호 확인
	$("#checkPasswordBtn").click(checkPassword);
	
	//회원정보수정
	$("#updateUserBtn").click(updateUserInfo);
	
	//로그아웃
	$("#logoutBtn").click(logout);
	
	//회원탈퇴 확인
	$("#deleteUserBtn").click(confirmDeleteUser);
	
	var $replyList=$('#replyList');
	
	//답글 열기 버튼
	$replyList.on('click', '.openAddFormBtn', function(){
    	openAddForm($(this).parents("tr").prop("id"));
    });
    
    //수정폼 열기버튼
	$replyList.on('click', '.openUpdateFormBtn', function(){
    	openUpdateForm($(this).parents("tr").prop("id"));
    });
    
    //글 삭제 버튼
	$replyList.on('click', '.deleteReplyBtn', function(){
    	confirmDelete($(this).parents("tr").prop("id"));
    });
    
    //답글 등록 버튼
	$replyList.on('click', '.addReplyBtn', function(){
    	addReply(this);
    });
    
    //답글폼 취소버튼
	$replyList.on('click', '.closeAddFormBtn', function(){
    	$(this).closest(".addForm").addClass("hide");
    	$(".openAddFormBtn").prop("disabled", false);
    });
    
    //글 수정완료 버튼
	$replyList.on('click', '.updateReplyBtn', function(){
    	updateReply(this);
    });
    
    //수정폼 취소버튼
	$replyList.on('click', '.closeUpdateFormBtn', function(){
    	$(this).closest(".updateForm").addClass("hide");
    	$(this).closest(".inputForm").find("input[type=text]").prop("readonly", true);
    	$(".openUpdateFormBtn").prop("disabled", false);
    });
    
});


//댓글 목록 조회
function getList(pageIndex){
	var pageIndex=pageIndex;
	activePageIndex = pageIndex;
	
	$('#'+pageIndex).addClass('active');//현재 선택한 페이지번호 활성화

	var pagingInfo = setPagingInfo(pageIndex);
	
	$.ajax({
		type:"POST",
		url:"get-list.json",
		data:pagingInfo,
		dataType:"json",
		success:function(data){
			if(data.result){
				$("#loginId").val(data.loginId);
				$("#email").val(data.loginEmail);
				drawTable(data.list, data.loginId);				
			}else{
				alert("목록을 조회하지 못하였습니다.");
			}
		}
	} );
}

//table 생성
function drawTable(list, loginId) {
	
	var $tbody = $("#replyList tbody");
	var row='';
    
	for (var i = 0; i < list.length; i++) {
        row+=drawRow(list[i]);
    }
	
	$tbody.html('');
	$tbody.append(row); 
	
	//로그인 유저
	var loginId = loginId;//json 전송받은 로그인 유저	
	
	//권한관련
	$(".authBtn").each(function(){
		var writer = $(this).parent().siblings(".writer").text();
		if(loginId !== writer){
			$(this).addClass("hide");
		}
	});
}

//table row 생성
function drawRow(rowData) {
	var id=rowData.no;
	var replyId = 'reply'+id;
	var replyForm = $(".replyForm").html(); //댓글 입력폼
	var updateForm = $(".updateForm").html(); //수정폼

	var depth = (100-(rowData.depth*3))+"%;";	//들여쓰기

    var row='<tr class="inputForm" id="'+replyId+'">';
	
    if(rowData.imageName!=null){
		row+='<td align="center"><img src="../resources/images/' + rowData.imageName + '"></td>'; 
	}else{
		row+='<td align="center"></td>';
	}
	
	row+='<td align="center"><div class="row" style="float:right; width:'+depth+'"><input type="text" class="form-control content" readonly value="'+ rowData.content +'"></div>';
	row+='<br><div class="row hide updateForm" style="float:right; width:'+depth+'">'+updateForm+'</div>';
	row+='<div class="row hide addForm" style="float:right; width:'+depth+'">'+replyForm+'</div></td>';
	row+='<td align="center">' + rowData.modifyDate.substr(0,16) + '</td>';
	row+='<td align="center" class="writer">' + rowData.writer + '</td>';
	row+='<td align="center"><button type="button" class="btn btn-info btn-sm openAddFormBtn" ><span class="glyphicon glyphicon-comment"></span></button></td>';
	row+='<td align="center"><button type="button" class="btn btn-primary btn-sm authBtn openUpdateFormBtn" ><span class="glyphicon glyphicon-edit "></span></button></td>';
	row+='<td align="center"><button type="button" class="btn btn-danger btn-sm authBtn deleteReplyBtn" ><span class="glyphicon glyphicon-trash"></span></button></td></tr>';
	
	return row;
}


//작성폼 초기화
function resetForm(){
	$("#content").val("");
	$("#image").val("");
}

//답글 작성
function addReply(e){
	var $e = $(e);
	var no;
	if($e.prop("id") === "writeBtn"){
		no = 0;
	}else{
		no = $e.closest(".inputForm").prop("id").substr(5);
	}
	var $parent = $e.closest(".addForm");
	var content =$parent.find(".content").val();
	var file =  $parent.find("input[type=file]")[0].files[0];
	
	var formData = new FormData();
	formData.append("parent",no);
	formData.append("content", content);
	formData.append("image", file);

	addReplyToAjax(formData);
}

//글&답글 등록
function addReplyToAjax(formData){
	var formData = formData;
	
	$.ajax({
		type:"POST",
		url:"add.json",
		data:formData,
		dataType:"json",
		processData : false,
	    contentType: false,		
		success:function(data){
			if(data.result){
				alert("댓글이 등록되었습니다!");
				resetForm();
				makeListAndPaging(activePageIndex);
			}else{
				alert("댓글 등록이 실패하였습니다.");
			}
		}
	} );
}

//답글작성폼 열기
function openAddForm(trId){
	
	$("#"+trId).find(".addForm").removeClass("hide");
	$(".openAddFormBtn").prop("disabled", true);//하나의 답글폼 open시 다른 답글폼 open 못하도록 방지
}

//글 수정폼 열기
function openUpdateForm(trId){
	$tr = $('#'+trId);
	$tr.find('.updateForm').removeClass('hide');
	$tr.find('.content').prop('readonly',false);
	$(".openUpdateFormBtn").prop("disabled", true);
}

//글 수정
function updateReply(e){
	var $parent = $(e).closest(".inputForm");
	var no = $parent.prop("id").substr(5);
	var content =$parent.find(".content").val();
	var file =  $parent.find("input[type=file]")[0].files[0];
	
	var formData = new FormData();
	formData.append("no",no);
	formData.append("content", content);
	formData.append("image", file);
	
	$.ajax({
		type:"POST",
		url:"update.json",
		data:formData,
		dataType:"json",
		processData : false,
	    contentType: false,		
		success:function(data){
			if(data.result){
				alert("댓글이 수정되었습니다!");
				resetForm();
				getList(activePageIndex);
			}else{
				alert("댓글 수정이 실패하였습니다.");
			}
		}
	} );
}

//댓글 삭제 확인
function confirmDelete(trId){
	var no=trId.substr(5);
	bootbox.confirm("삭제하시겠습니까?", function(confirmed) {
		if(confirmed) {
			deleteReply(no);
		}
	});	
}

//글 삭제
function deleteReply(no){
	var no=no;
	
	var user = {
			no:no
	}
	
	$.ajax({
		type:"POST",
		url:"delete.json",
		data:user,
		dataType:"json",
		success:function(data){
			alert("삭제되었습니다.");
			getList(activePageIndex);//active 된 pageIndex
		}
	} ).error(function(){
		alert("삭제가 실패하였습니다.");
	});
}

//기존 비밀번호 확인
function checkPassword(){
	var id=$("#loginId").val();
	var password = $("#password").val();
	var user={
			id:id,
			password:password
	}
	
	$.ajax({
		type:"POST",
		url:"/user/checkPassword.json",
		data:user,
		dataType:"json",
		success:function(data){
			if(data.result){
				alert("비밀번호가 확인되었습니다.");
				validPassword=true;
			}else{
				alert("비밀번호가 잘못되었습니다.");
			}
		}
	} );
	
}

//사용자정보 수정
function updateUserInfo(){
	if(!validPassword){
		alert("기존 비밀번호를 정확히 입력해주세요");
		return;
	}
	
	var id=$("#loginId").val();
	var password=$("#newPassword").val();
	var confirmPassword=$("#confirmPassword").val();
	var email=$("#email").val();
	
	if(password===""){
		alert("비밀번호는 공란이 허용되지 않습니다.");
		return;
	}
	if(password !== confirmPassword){
		alert("새로운 비밀번호를 다시 확인해주세요");
		return;
	}
	
	var user = {
			id			:id,
			password	:password,
			email		:email
	}
	
	$.ajax({
		type:"POST",
		url:"/user/update.json",
		data:user,
		dataType:"json",
		success:function(data){
			if(data.result){
				alert("회원정보가 수정되었습니다.");
				$(".close").trigger("click");
			}else{
				alert("회원정보수정이 실패 되었습니다.");
			}
			validPassword=false;
		}
	} );
}

//로그아웃
function logout(){
	
	$.ajax({
		type:"POST",
		url:"/user/logout.json",
		success:function(data){
			if(data.result){
				alert("로그아웃 되었습니다.");
				location.href="/";
			}else{
				alert("로그아웃이 실패하였습니다.");
			}
		}
	} );
}

//탈퇴확인
function confirmDeleteUser(){
	bootbox.confirm("탈퇴하시겠습니까?", function(confirmed) {
		if(confirmed) {
			deleteUser();
		}
	});	
}

//탈퇴
function deleteUser(){
	var id=$("#loginId").val();
	
	var user = {
			id:id
	}
	
	$.ajax({
		type:"POST",
		url:"/user/delete.json",
		data:user,
		dataType:"json",
		success:function(data){
			alert("탈퇴되었습니다.");
			location.href="/";
		}
	} ).error(function(){
		alert("탈퇴가 실패하였습니다.");
	});
}

