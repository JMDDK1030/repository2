<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />     
<c:set var="id" value="${sessionScope.id}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>∙ Mypage ∙</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/user.css">
</head>
<body>
<jsp:include page="../top.jsp"/>
<section> 
<div class="top"><h1>∙ 마이페이지 ∙</h1></div>
 
	<div class="page">
	<div class="pageWrap">
    <div class="pageBox1">마이페이지</div>
    <div class="pageBox">
   		<div class="pageBox2">
		    <div onclick="location.href='../mem/MemberInfo.mb?id=${id}'">회원정보 수정</div> 
		    <div onclick="location.href='../mem/MemberDelete.mb'">회원탈퇴</div>
   			<div onclick="location.href='userTicketing.jsp'">예매정보</div> 
   		</div>
     	<div class="pageBox3" >
      		<h3>센텀시네마</h3>
      		<p>Centum center</p>
      		<p>09:00 ~ 18:00</p>
    	</div>   
	</div>
	</div>
</div>
</section>
<jsp:include page="../footer.jsp"/>
</body>
</html>