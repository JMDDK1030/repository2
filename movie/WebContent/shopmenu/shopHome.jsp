<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}" />
<%
  	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
	<style>
		.cls1 {text-decoration:none;}
	   	.cls2{text-align:center; font-size:30px;}
	</style>
<link href="<c:url value='/css/style.css' />" rel="stylesheet" />
	
	<meta charset="UTF-8">
	<title>먹을것</title>
</head>
<body>
<jsp:include page="../top.jsp"/>
<section>
 
<div class="leftcolumn">

<div class="board" >
	<table>
<c:set var="i" value="0"/>
<c:set var="j" value="4" />
<c:choose>
  <c:when test="${empty menuList}" >
    <tr  height="10">
      <td colspan="4">
         <p align="center">
            <b><span style="font-size:9pt;">등록된 글이 없습니다.</span></b>
        </p>
      </td>  
    </tr>
  </c:when>
  
  <c:when test="${!empty menuList}" >
    <c:forEach  var="menu" items="${menuList }" varStatus="articleNum" >
 	<c:if test="${i % j == 0 }">
    <tr align="center">
	</c:if>
	
	<td width="10%">
	<img src="../img/${menu.menuimg}"><br>
	상품명:${menu.menuname } ${menu.mcontent }원
	</td>
	<c:if test="${i%j == j-1 }">
	</tr>
	</c:if>
	
	<c:set var="i" value="${i+1}"/>

    </c:forEach>
     </c:when>
    </c:choose>		
	</table>

</div>
</div>
</section>
<jsp:include page="../footer.jsp"/>
</body>
</html>