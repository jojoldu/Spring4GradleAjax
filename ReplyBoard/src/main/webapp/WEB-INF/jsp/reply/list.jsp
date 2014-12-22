<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<jsp:include page="/WEB-INF/jsp/common/commonHeader.jsp" flush="true" />

<!-- ajax 파일 업로드를 위한 plugin -->
<script src="../plugins/jquery.form.js"></script>

<!-- 바탕화면 css -->
<link rel="stylesheet" href="../css/carousel.css">

<body>

    <div class="navbar-wrapper">
      <div class="container">

        <div class="navbar navbar-inverse navbar-static-top">
          <div class="container">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="#">Reply Board</a>
            </div>
          </div>
        </div>
      </div>
    </div> 
    <!-- Carousel
    ================================================== -->
    <div id="myCarousel" class="carousel slide">
      <!-- Indicators -->
      <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
      </ol>
      <div class="carousel-inner">
        <div class="item active">
          <div class="container">
            <div class="carousel-caption">
              <h1>이동욱</h1>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="container">
            <div class="carousel-caption">
              <h1>줌인터넷</h1>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="container">
            <div class="carousel-caption">
              <h1>서비스개발 파트1</h1>
            </div>
          </div>
        </div>
      </div>
      <a class="left carousel-control" href="#myCarousel" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
      <a class="right carousel-control" href="#myCarousel" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
    </div><!-- /.carousel -->
    
    <div class="row">
    	<div class="col-md-1" >
    		<select id="pageSize" class="form-control" style='float:right;'>
    			<option value='2'>2</option>
    			<option value='3'>3</option>
    			<option value='5'>5</option>
    			<option value='10'>10</option>
    			<option value='15'>15</option>
    			<option value='20'>20</option>
    			<option value='30'>30</option>    			
    		</select>
    	</div>
    	<div class="col-md-6 col-md-offset-2" id="form0">
			<textarea class="form-control" rows="3" autofocus placeholder="댓글을 입력해주세요." id="content" name="content"></textarea>	
	       	<br>
	       	<div class="row">
		       <div class="col-md-2 file">
			       	<input type="file" id="image" name="image">
		       </div>
	           <div class="col-md-2 col-md-offset-8">
		        	<button type="button" class="btn btn-success" onclick='addReply(this)'>글 등록하기</button>
		       </div>	        	
	       </div>
		</div>
	</div>
	<br>
	<br>
	<div class="row hide"><!-- 답글 form -->
        <form class="form replyForm" enctype="multipart/form-data" >
		    <textarea class="form-control" rows="3" autofocus placeholder="답글을 입력해주세요." id="childContent" name="content"></textarea>	
	      	<br>
	       	<div class="row">
		        <div class="col-md-2 file">
			       	<input type="file" id="childImage" name="image">
			    </div>
		        <div class="col-md-4 col-md-offset-8">
			        <button type="button" class="btn btn-primary btn-sm" onclick='addReply(this)'>등록</button>
			        <button type="button" class="btn btn-danger btn-sm" onclick='cancleReply(this)'>취소</button>
			    </div>	        	
	        </div>
	   </form>	
	</div>
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<table class="table table-striped table-hover" id="replyList" style='margin:0px;'>
				<col width="10%">
				<col width="*">
				<col width="10%">
				<col width="10%">
				<col width="3%">				
				<col width="3%">
				<col width="3%">				
				<!-- 
				<thead>
					<tr>
						<th align="center" id="header-content">내용</th>
						<th align="center">번호</th>						
						<th align="center">등록일</th>
						<th align="center">등록자</th>
						<th align="center">댓글달기</th>						
						<th align="center">수정/삭제</th>
					</tr>
				</thead>
 -->
	 			<tbody>
	 			</tbody>
			</table>
			<div id="paging">
			</div>
		</div>
	</div>
	

	

</body>
</html>