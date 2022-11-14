<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%--jstl 라이브러리 사용을 위한 선언 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>∙ 삼공시네마 ∙</title>
<link rel="stylesheet" href="${contextPath}/css/style.css">
<style>
@media screen and (max-width: 700px) {
    .rightcolumn {   
        display: none;
        padding: 0;
    }
}
</style>
</head>
<body>

<section>
	<div class="movie">
	<div class="movieBox" >
    <img style="cursor:pointer" src="${contextPath}/img/cen.jpg" height="250px">
    </div>
    </div>
  	<div class="leftcolumn">
	<div class="movie">
      <h1>공조2 : 인터내셔널, 2022</h1>
      <p>2022년 9월 7일 대개봉</p>
      <div class="videowrapper">
      <iframe width="560" height="315" src="https://www.youtube.com/embed/fzUKUfHeIYA"></iframe>
      </div>
    </div>
    <div class="movie">
      <h1>미니언즈2 Minions: The Rise of Gru, 2022</h1>
      <p>2022년 7월 대개봉</p>
      <div class="videowrapper">
      <iframe width="560" height="315" src="https://www.youtube.com/embed/99DlyQZgfs8"></iframe>
      </div>
    </div>
  </div>
<!-- 사이드 메뉴입니다. -->
<div class="rightcolumn">
    <div class="movie">
    <form name="frm" method="post" action="indexSearch.jsp">
     
    <div class="searchBox">
      	<input type="text" name="m_name" >
 		<button type="submit">검색</button>
 	</div>
    </form>
    </div>  
    <div class="movie">
    <div class="movieBox">
    	<img src="${contextPath}/img/side.jpg">
    </div>
    </div>
    <div class="movie">
    <div class="movieBox">
    	<img src="${contextPath}/img/side2.jpg">
    </div>
    </div>
</div>
</section>
<div class="movie">
	<div class="movieBox">
    <img style="cursor:pointer"  src="${contextPath}/img/Nite.jpg">
    </div>
    </div>

</body>
</html>