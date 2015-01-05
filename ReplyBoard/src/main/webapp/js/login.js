/**
 * 로그인 관련 자바스크립트
 */
var validId=false;
var inputId="";

$(function() {
	//중복검사 이벤트
	$("#checkIdBtn").click(checkId);
	$("#id").keypress(function(event) {
		if(event.keyCode == 13) {
			checkId();
		}
	});
	
	//회원가입 이벤트
	$("#signUpBtn").click(signUp);
	$("#email").keypress(function(event) {
		if(event.keyCode == 13) {
			signUp();
		}
	});

	//로그인 이벤트
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

//ID중복검사
function checkId(){
	var id=$("#id").val();
	var flag=true;
	
	if(!checkEng(id)){//영문, 숫자만 있는지 체크
		alert("ID에 특수문자 및 공백은 포함될 수 없습니다.");
		flag=false;
		return false;
	}

	if(flag){
		var userVO = {
				id:id
		}
		
		checkIdFromDB(userVO);
	}
}

function checkIdFromDB(userVO){
	
	$.ajax({
	    headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		type:"POST",
		url:"user/checkId.json",
		data:JSON.stringify(userVO),
		dataType:"json",
		success:function(data){
			if(data==true){
				alert("사용가능한 ID입니다.");
				inputId=$("#id").val();
				validId=true;
			}else{
				alert("사용이 불가능한 ID입니다.");
				validId=false;
			}
		}
	} );
}


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
		url:"user/login.json",
		data:JSON.stringify(loginVO),
		dataType:"json",
		success:function(data){
			if(data===true){
				$("#loginId").val("");
				$("#loginPassword").val("");
				location.href="reply/goTolist";
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
	
	if(validId==false){
		alert("ID중복검사를 해주세요");
		return false;
	}
	
	if(id !== inputId){// 미중복 ID 입력후 중복검사 없이 ID수정하여 중복ID로 회원가입시  
		alert("ID변경시에는 다시 중복검사를 해야합니다.");
		return false;
	}
	
	if(password === ""){
		alert("비밀번호에 공란이 올 수 없습니다.");
		return false;
	}
	
	if(password !== checkPassword){
		alert("새 비밀번호를 확인해주세요.");
		return false;
	}
	
	if(email === ""){
		alert("email을 입력해주세요.");
		return false;
	}
	
	var reg_email=/^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$/;	// 인자 email_address를 정규식 format 으로 검색
	
	if (email.search(reg_email) == -1){
	   alert("email형식에 맞춰 입력해주세요.");
	   return false;
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
	if(insertVO==false){
		return;
	}
	
	$.ajax({
	    headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		type:"POST",
		url:"user/add.json",
		data:JSON.stringify(insertVO),
		dataType:"json",
		success:function(data){
			if(data===true){
				alert("가입이 성공되었습니다.로그인해주세요.");
				resetForm();
				$(".close").trigger("click");
			}else{
				alert("가입이 실패하였습니다.");
			}

		}
	} );
}

