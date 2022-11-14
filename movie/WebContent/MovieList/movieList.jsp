<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="file.FileDAO"%>
<%@page import="file.FileVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="id" value="${sessionScope.id}"/>

<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<style>
.cls1 {
	text-decoration: none;
}
.cls2 {
	text-align: center;
	font-size: 30px;
}
</style>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/movieList.css">

<meta charset="UTF-8">
<title>영화</title>
</head>
<body>
	<jsp:include page="../top.jsp"/>
	<section>
		<div class="topList">
			<h1>영화 목록</h1>
		</div>
		<c:set var="filesList" value="${filesList}"></c:set>
		<c:choose>
			<c:when test="${!empty filesList}">
				<div class="movie">
					<c:forEach begin="0" end="3" step="1" var="i">
						<div class="fakeimg"
							onclick="location='${contextPath}/Movie/viewFile.do?fileNO=${filesList[i].fileNO}'"
							style="background-image:url('../img/${filesList[i].imageFileName}')">
						</div>
					</c:forEach>
				</div>

				<div class="movie">
					<c:forEach begin="4" end="7" step="1" var="i">
						<div class="fakeimg"
							onclick="location='${contextPath}/Movie/viewFile.do?fileNO=${filesList[i].fileNO}'"
							style="background-image:url('../img/${filesList[i].imageFileName}')">
						</div>
					</c:forEach>
				</div>

				<div class="movie">
					<c:forEach begin="8" end="11" step="1" var="i">
						<div class="fakeimg"
							onclick="location='${contextPath}/Movie/viewFile.do?fileNO=${filesList[i].fileNO}'"
							style="background-image:url('../img/${filesList[i].imageFileName}')">
						</div>
					</c:forEach>
				</div>

				<div class="movie">
					<c:forEach begin="12" end="15" step="1" var="i">
						<div class="fakeimg"
							onclick="location='${contextPath}/Movie/viewFile.do?fileNO=${filesList[i].fileNO}'"
							style="background-image:url('../img/${filesList[i].imageFileName}')">
						</div>
					</c:forEach>
				</div>

				<div class="movie">
					<c:forEach begin="16" end="19" step="1" var="i">
						<div class="fakeimg"
							onclick="location='${contextPath}/Movie/viewFile.do?fileNO=${filesList[i].fileNO}'"
							style="background-image:url('../img/${filesList[i].imageFileName}')">
						</div>
					</c:forEach>
				</div>
			</c:when>
		</c:choose>
 
		<c:if test="${id eq 'master'}">
			<a class="cls1" href="${contextPath}/Movie/fileForm.do"><p class="cls2">파일 업로드</p></a>		
		</c:if> 
	</section>
	<jsp:include page="../footer.jsp"/>
	</body>
</html>