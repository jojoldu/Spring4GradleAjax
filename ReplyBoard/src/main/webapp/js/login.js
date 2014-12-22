/**
 * 로그인 관련 자바스크립트
 */
$(function() {
	
	$("#signUpBtn").click(signUp);
	$("#loginBtn").click(login);
	
	$("#loginId").keypress(function(event) {
		if(event.keyCode == 13) {
			$("#loginPassword").focus();
		}
	});
	$("#loginPassword").keypress(function(event) {
		if(event.keyCode == 13) {
			login();
		}
	});
});

//로그인
function login(){
	var loginVO = {
			id:$("#loginId").val(),
			password:$("#loginPassword").val()
	}
	
	$.ajax({
	    headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		type:"POST",
		url:"user/login",
		data:JSON.stringify(loginVO),
		dataType:"json",
		success:function(data){
			if(data===true){
				$("#loginId").val("");
				$("#loginPassword").val("");
				location.href="reply/list";
			}else{
				alert("아이디와 패스워드를 확인해주세요.");
			}

		}
	} );
}

//회원가입 입령항목 체크
function validateObj(){
	var id=$("#id").val();
	var password=$("#password").val();
	var checkPassword=$("#checkPassword").val();
	var email=$("#email").val();
	
	if(password !== checkPassword){
		alert("비밀번호를 잘못입력하였습니다.");
		return;
	}
	
	var obj={
			id:id,
			password:password,
			email:email
	}
	return obj;
}

//가입폼 초기화
function resetForm(){
	$("#id").val("");
	$("#password").val("");
	$("#checkPassword").val("");
	$("#email").val("");
}

//회원가입
function signUp(){
	var insertVO=validateObj();
	
	$.ajax({
	    headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		type:"POST",
		url:"user/add",
		data:JSON.stringify(insertVO),
		dataType:"json",
		success:function(data){
			if(data===true){
				alert("가입이 성공되었습니다.로그인해주세요.");
				resetForm();
			}else{
				alert("가입이 실패하였습니다.");
			}

		}
	} );
}

