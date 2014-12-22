<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="/WEB-INF/jsp/common/commonHeader.jsp" flush="true" />
</head>
<body>
<table border="1">
  <tr align="center">
    <td width="100px">아이디</td>
    <td width="100px">메일</td>
  </tr>
<c:forEach var="user" items="${userList}">
  <tr align="center">
    <td width="100px">${user.id}</td>
    <td width="100px">${user.email}</td>
  </tr>
</c:forEach>
</body>
</html>