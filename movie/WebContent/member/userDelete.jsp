<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>∙ 회원탈퇴 ∙</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/user.css">
</head>

<body>
<jsp:include page="../top.jsp"/>
<section>
	<div class="join">
    <form name="frm" action="${contextPath}/mem/delMember.mb" method="post">
    <h2>∙ 회원 탈퇴 ∙</h2>
      <table>
    	<tr>
    	<td><input type="password" name="pw" maxlength="10" placeholder="비밀번호" required></td>
    	</tr>
      
      
    	<tr>
    	<td colspan="2">
    	<input type="submit" value="탈퇴">
    	<input type="button" value="취소" onclick="location.href='../mem/MemberPage.mb'">
    	<td>
    	</tr>
    </table>
    </form>
	</div>
</section>
<jsp:include page="../footer.jsp"/>
</body>
</html>