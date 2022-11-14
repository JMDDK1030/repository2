<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />     

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/user.css">

</head>
<body onload="document.frm.id.focus()">
<jsp:include page="../top.jsp"/>
  <section>
  <div class="top"><h1>Login</h1></div>
    <form name="frm" action="${contextPath}/mem/LogMember.mb"  method="post">
    <div class="login">
    <table>
      <tr>
      <td><input type="text" name="id" maxlength="10" placeholder="아이디" value="" required></td>
      </tr>
      
      <tr>
      <td><input type="password" name="pw" maxlength="10" placeholder="비밀번호" value="" required></td>
      </tr>
      </table>       
    </div>
    <!-- 쿠키 박스 -->
<!--     <div style="display:flex; justify-content:center;"> -->
<!-- 	  <div style="width:350px; margin-top:-20px; text-align: left;"> -->
<!-- 		<input type="checkbox" name="idcheck" value="1" >ID -->
<!-- 		<input type="checkbox" name="pwcheck" value="1" >PW remember -->
<!-- 	  </div> -->
<!-- 	</div> -->
	
      <div class="button">
      <input type="submit" value="로그인"></div>
      <div class="button">
      <input type="button" value="회원가입" onclick="location='${contextPath}/mem/MemberJoin.mb'"></div>       
      
    </form>
  </section>
<jsp:include page="../footer.jsp"/>
</body>
<script src="../script/check.js"></script>

</html>