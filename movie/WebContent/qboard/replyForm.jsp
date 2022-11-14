<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/css/style.css' />" rel="stylesheet" />
<link href="<c:url value='/css/board.css' />" rel="stylesheet" />
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">



  function backToList(obj){
	 obj.action="${contextPath}/board/qboard.qo";
	 obj.submit();
  }
 
  function readURL(input) {
      if (input.files && input.files[0]) {
          var reader = new FileReader();
          reader.onload = function (e) {
              $('#preview').attr('src', e.target.result);
          }
          reader.readAsDataURL(input.files[0]);
      }
  }  
</script> 
<style>
div.board  {

  margin-left: 50px;
 
}
</style>
<title>답글쓰기 페이지</title>
</head>
<body>
<jsp:include page="../top.jsp"/>
<section>
<div class="top" style="text-align:left"><h1>∙ 답글쓰기</h1></div>
 <jsp:include page="boardSide.jsp"/>
  <form name="frmReply" method="post"  action="${contextPath}/board/addReply.qo"   enctype="multipart/form-data">
    <div class="board">
    <table align="center">
    <tr>
			<td align="right"> 글쓴이:&nbsp; </td>
			<td><input type="text" size="5" value="${id}" name="id" readonly /> </td>
		</tr>
		<tr>
			<td align="right">답변 :&nbsp;  </td>
			<td><input type="text" size="67"  maxlength="100" name="title" /></td>
		</tr>
		<tr>
			<td align="right"> </td>
			<td>
				<input type=submit value="답글반영하기" />
				<input type=button value="취소"onClick="backToList(this.form)" />
				
			</td>
		</tr>
    </table>
    </div>
  </form>
</section>
<jsp:include page="../footer.jsp"/>
</body>
<script src="<c:url value="/script/check.js" />"></script>
</html>