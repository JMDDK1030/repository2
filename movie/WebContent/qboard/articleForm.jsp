	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  



<%
	request.setCharacterEncoding("UTF-8");
%>
<%-- 컨텍스트밍 얻기 --%>
	<c:set  var="contextPath" value="${pageContext.request.contextPath}"/>

	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>질문글쓰기</title>

<link href="<c:url value='/css/style.css' />" rel="stylesheet" />
<link href="<c:url value='/css/board.css' />" rel="stylesheet" />
 <style>
 
 div.board  {

  margin-left: 50px;

 
}
 
 </style>
 
 
 <script type="text/javascript">
   function backToList(obj){
    obj.action="${contextPath}/board/qboard.qo";
    obj.submit();
  }
 </script>

</head>

<body>
<jsp:include page="../top.jsp"/>

<section>

<h1 style="text-align:center">질문 하기</h1>
  <jsp:include page="boardSide.jsp"/>
  
  <form name="articleForm" 
        method="post"   
        action="${contextPath}/board/addArticle.qo"   
        enctype="multipart/form-data" >
   <div class="board"> 
    <table border=0 align="center">    
   	 <tr>
		<td align="right"> 글쓴이:&nbsp; </td>
		<td><input type="text" size="5" value="${id}" name="id" readonly /> </td>
	 </tr>     
     <tr>
	   <td align="right">질문: </td>
	   <td colspan="2"><input size="67"  maxlength="500" name="title" /></td>
	 </tr>
	 <tr>
	    <td align="right"> </td>
	    <td colspan="2">
	       <input type="submit" value="글쓰기" />
	       <input type=button value="목록보기"onClick="backToList(this.form)" />
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