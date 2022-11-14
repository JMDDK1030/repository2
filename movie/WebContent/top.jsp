<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"  />     
<c:set var="id" value="${sessionScope.id}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Insert title here</title>
<style type="text/css">
		.uline{text-decoration: none; color: black;}
</style>
<script type="text/javascript">
	function screenSite(){
		window.open("${contextPath}/mem/Site.ss","checkForm","width=800,height=500,resizable = no, scrollbars = no");
	}
</script>
</head>
<body>

<header>
  <h1><a href="${contextPath}/mem/main.mb" >센텀시네마</a></h1>
  <p>∙ C U L T U R E P L E X ∙</p>
</header>
<c:if test="${id!=null}">
<p align="right">${id}님 환영합니다</p>
</c:if>
<%-- 메뉴 부분 --%>
<nav role="navigation">
  <ul id="main-menu">
    <li><a href="${contextPath}/Movie/listFiles.do">영화</a></li>
    <li><a href="${contextPath}/Movie/ticketList.do">예매</a></li>
    <li><a href="${contextPath}/shop/menuList.so">스토어</a></li>
    <li><a href="${contextPath}/board/qboard.qo">고객센터</a></li>
   <li><a class="uline" href="javascript:void(0)" onclick="screenSite()">시네마 위치</a></li>
   		 <c:if test="${id==null}">
   		 <li><a class="uline" href="${contextPath}/mem/MemberJoin.mb">회원가입</a></li>
		 <li><a class="uline" href="${contextPath}/mem/MemberLogin.mb">로그인</a></li>
		 </c:if>
		 <c:if test="${id!=null}">
		 	<li><a class="uline" href="${contextPath}/mem/MemberPage.mb">마이페이지</a></li>
		  	<li><a class="uline" href="${contextPath}/mem/MemberLogout.mb">로그아웃</a></li>
	     </c:if>
  </ul>
</nav>

<jsp:include page="qchat.jsp"/>
</body>
</html>