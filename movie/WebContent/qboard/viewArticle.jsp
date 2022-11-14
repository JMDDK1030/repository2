<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<html>
<head>
   <meta charset="UTF-8">
   <title>∙ 센텀시네마 QnA ∙</title>
   <link href="<c:url value='/css/style.css' />" rel="stylesheet" />
   <link href="<c:url value='/css/board.css' />" rel="stylesheet" />
   <style>
     div.board  {
  	margin-left: 50px;
	}
     #tr_btn_modify{
       display:none;
     }
   
   </style>
   <script  src="http://code.jquery.com/jquery-latest.min.js"></script> 
   <script type="text/javascript" >
     function backToList(obj){
	    obj.action="${contextPath}/board/qboard.qo";
	    obj.submit();
     }
 
	 function fn_enable(obj){
		 document.getElementById("i_title").disabled=false;
		 document.getElementById("tr_btn_modify").style.display="block";
		 document.getElementById("tr_btn").style.display="none";
	 }
	 
	 function fn_modify_article(obj){   // <form>태그의 name속성값을 매개변수obj로 받는다. -> frmArticle
		 obj.action="${contextPath}/board/modArticle.qo"; //수정 요청 하기 위해 <form> action속성에 주소 설정
		 obj.submit();//<form> 전송이벤트 발생시킴
	 }
	 
	 //삭제하기 버튼을 클릭하면 호출되는 함수이며
	 //url매개변수 : 삭제 요청할 주소 전달 받음
	 //articleNO매개변수 : 삭제시킬 글번호 전달받음
	 function fn_remove_article(url,articleNO){
		 //동적으로 새로운 form태그를 생성하여 변수에 저장
		 var form = document.createElement("form");
		 //form변수에 저장된 form태그에 method속성에 post값 action속성에 삭제요청할 주소를 설정
		 //예
		 //<form method= "post" action = "${contextPath}/board/removeArticle.do"></form>
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
		 
		 //자바스크립트를 이용해 동적으로 input을 생성한후 name속성과 value속성을 삭제할글번호를 설정합니다
		 //예
		 //<input type="hidden" name="articleNO" value=삭제할글번호>
	     var articleNOInput = document.createElement("input");
	     articleNOInput.setAttribute("type","hidden");
	     articleNOInput.setAttribute("name","articleNO");
	     articleNOInput.setAttribute("value", articleNO);
		 
	     //동적으로 생성한 input태그를 동적으로 생성한 form태그 내부에 보낸다
	     //<form method= "post" action = "${contextPath}/board/removeArticle.do">	     
	     //		<input type="hidden" name="articleNO" value=삭제할글번호>
	     //</form>
	     form.appendChild(articleNOInput);
	     //<form>태그를 body태그 내부에 추가
	     //<body>
	     //	<form method= "post" action = "${contextPath}/board/removeArticle.do">	     
	     //		<input type="hidden" name="articleNO" value=삭제할글번호>
	     //	</form>	     
	     //</body>
	     document.body.appendChild(form);
	     //<form>을 이용해서 삭제 요청
		 form.submit();
	 
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
	 //답슬쓰기 버튼을 클릭시 호출하는함수
	 //답글작성화면 요청주소와 주글 글번호를 매개변수로 전달받아 컨트롤러를 요청하는 함수
	 function fn_reply_form(url, parentNO) {
		var form = document.createElement("form");
			form.setAttribute("method", "post")
			form.setAttribute("action", url)			
			/*
				<form method="post" action=url></form>			
			*/
			var parentNOInput = document.createElement("input");
				parentNOInput.setAttribute("type", "hidden");
				parentNOInput.setAttribute("name", "parentNO");
				parentNOInput.setAttribute("value", parentNO);
				
				/*
					<input type="hidden" name="parentNO" value=작성할 답글의 주글번호>			
				*/
				form.appendChild(parentNOInput);
				/*
					<form method="post" action="/pro17/board/replyForm.do">
						<input type="hidden" name="parentNO" value=작성할 답글의 주글번호>
					</form>				
				*/
				
				document.body.appendChild(form);
				
				form.submit();
				
	}
	 
	 
 </script>
</head>
<body>
<jsp:include page="../top.jsp"/>
<section>
<div class="top" style="text-align:left"><h1>∙ 글 정보</h1></div>
<jsp:include page="boardSide.jsp"/>

  <form name="frmArticle" method="post"  action="${contextPath}"  enctype="multipart/form-data">
  <div class="board">
  <table  border="0"  align="center">
  <tr>
   <td width="150" align="center" bgcolor="#FF9933">
      글번호
   </td>
   <td >
    <input type="text"  value="${article.articleNO }"  disabled />
    <input type="hidden" name="articleNO" value="${article.articleNO}"  />
   </td>
  </tr>
  <tr>
   <td width="150" align="center" bgcolor="#FF9933">
      작성자 아이디
   </td>
   <td >
    <input type=text value="${article.id }" name="writer"  disabled />
   </td>
  </tr>
  <tr>
   <td width="150" align="center" bgcolor="#FF9933">
      제목 
   </td>
   <td>
    <input type=text value="${article.title }"  name="title"  id="i_title" disabled />
   </td>   
  </tr>
  <tr>
	   <td width=20% align=center bgcolor=#FF9933>
	      등록일자
	   </td>
	   <td>
	    <input type=text value="<fmt:formatDate value="${article.writeDate}" />" disabled />
	   </td>   
  </tr>
  <tr   id="tr_btn_modify"  >
	   <td colspan="2"   align="center" >
	        <input type=button value="수정반영하기"  onClick="fn_modify_article(frmArticle)"  >
       </td>
       <td> 	
         	<input type=button value="취소"  onClick="backToList(frmArticle)">
	   </td>   
  </tr>
    
  <tr  id="tr_btn">
   <td colspan=2 align=center>
	  <c:if test="${id == article.id}">  
	    <input type=button value="수정하기" onClick="fn_enable(this.form)">
	    
	    <%-- 삭제하기 버튼을 누르면 fn_remove_article함수 호출하면서 삭제할 주소와 삭제할 글번호를 매개변수로 전달함 --%>
	    <input type=button value="삭제하기" onClick="fn_remove_article('${contextPath}/board/removeArticle.qo', ${article.articleNO})">
	   </c:if> 
	    <input type=button value="리스트로 돌아가기"  onClick="backToList(this.form)">
	     <input type=button value="답글쓰기"  onClick="fn_reply_form('${contextPath}/board/replyForm.qo', ${article.articleNO})">
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