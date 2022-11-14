<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%--jstl 라이브러리 사용을 위한 선언 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Centum cinema</title>

</head>
<body>
<%--중앙 영역은 사용자로 부터 계속 바뀌기 부분 index.jsp의 정보를 읽어 --%>
<c:set var="center" value="${param.center}"/>

<%--처음 movieMain실행시 param 값 받아올수 없기에 조건설정 --%>
<c:if test="${center == null }">
	<c:set var="center" value="./center/index.jsp"/>
</c:if>
	<jsp:include page="top.jsp"/>

	<jsp:include page="${center}"/>

	<jsp:include page="footer.jsp"/>
</body>
</html>