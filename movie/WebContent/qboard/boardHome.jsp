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

	<c:set var="articlesList" value="${requestScope.articlesMap.articlesList}" /> 
	
	<c:set var="totArticles"  value="${articlesMap.totArticles}"  />
	
	<c:set var="section"  value="${articlesMap.section}"  />
	<c:set var="pageNum" value="${articlesMap.pageNum}"/>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>∙ QnA ∙</title>

<link href="<c:url value='/css/style.css' />" rel="stylesheet" />
<link href="<c:url value='/css/board.css' />" rel="stylesheet" />
 
 
</head>
<jsp:include page="../top.jsp"/>
<body>

<section>
 
<div class="top" style="text-align:left"><h1>∙QnA</h1></div>
<!-- 사이드 메뉴입니다. -->
<jsp:include page="boardSide.jsp"/>
 
<div class="leftcolumn">
       
<div class="board" >
	<table class="tab">
	<colgroup>
		<col width="7%"/>
		<col width="*"/>
		<col width="70%"/>
	</colgroup>	
		<tr>
			<th>글번호</th>
			<th>작성자</th>
			<th>질문</th>
			<th>작성일</th>		
		</tr>
<c:choose>
  <c:when test="${empty articlesList }" >
    <tr  height="10">
      <td colspan="4">
         <p align="center">
            <b><span style="font-size:9pt;">등록된 글이 없습니다.</span></b>
        </p>
      </td>  
    </tr>
  </c:when>
  <c:when test="${!empty articlesList}" >
    <c:forEach  var="article" items="${articlesList }" varStatus="articleNum" >
     <tr align="center">
<%-- 	 <td width="5%">${articleNum.count}</td> --%>
		<td width="5%">${article.articleNO}</td> 
	<td width="10%">${article.id }</td>
	<td align='left'  width="35%">
	  <span style="padding-right:30px"></span>
	   <c:choose>
	      <c:when test='${article.level > 1 }'>  
	         <c:forEach begin="1" end="${article.level }" step="1">
	              <span style="padding-left:20px"></span>    
	         </c:forEach>
	         <span style="font-size:12px;">[답변]</span>
                   <a class='cls1' href="${contextPath}/board/viewArticle.qo?articleNO=${article.articleNO}">${article.title}</a>
	          </c:when>
	          <c:otherwise>
	            <a class='cls1' href="${contextPath}/board/viewArticle.qo?articleNO=${article.articleNO}">${article.title }</a>
	          </c:otherwise>
	        </c:choose>
	  </td>
	  <td  width="10%"><fmt:formatDate value="${article.writeDate}" /></td> 
	</tr>
    </c:forEach>

     </c:when>
    </c:choose>		
	</table>

</div>


<div class="cls2">
	<%-- DB 테이블에 게시글이 존재하면? --%>
	<c:if test="${totArticles != null}">
		<c:choose>
			<%-- 글 개수가 100 초과인 경우 --%>
			<c:when test="${totArticles > 40}">
				
				<c:forEach var="page" begin="1" end="10" step="1">
					
					<c:if test="${section > 1 && page == 1 }">
						<a class="no-uline" 
						   href="${contextPath}/board/qboard.qo?section=${section-1}&pageNum=${(section-1)*10+1}">
							&nbsp;pre
						</a>
					</c:if>
					
						<a class="no-uline" 
						   href="${contextPath}/board/qboard.qo?section=${section}&pageNum=${page}">
							&nbsp;${(section-1)*10+page}
						</a>
				
				   <c:if test="${page == 10}">
						<a class="no-uline" 
						   href="${contextPath}/board/qboard.qo?section=${section+1}&pageNum=${(section-1)*10+1}">
							&nbsp;NEXT
						</a>
					</c:if>
				</c:forEach>
			</c:when>
			<%--등록된 글 개수가 100개인 경우 --%>			
			<c:when test="${totArticles == 40}">
				<c:forEach var="page" begin="1" end="10" step="1">
					<a class="no-uline" href="#">${page}</a>
				</c:forEach>
			</c:when>
			<%-- 등록된 글 개수가 100개 미만인 경우 --%>
			<c:when test="${totArticles < 40 }">
				<%-- 글수가 100개가 되지 않으므로 표시되는 페이지는 10개가 되지 않고 , 
				     전체글수를 10으로 나누어 구한 몫에 1을 더한 페이지까지 표시 --%>
				<c:forEach var="page" begin="1" end="${totArticles/4+1}" step="1">
					<c:choose>
						<%--페이지 번호와 컨트롤러에서 넘어온 pageNum이 같은경우 페이지번호를 빨간색으로 표시하여
							현재 사용자가 보고 있는 페이지임을 알립니다.
						 --%>
						<c:when test="${page==pageNum}">
							<a class="sel-page" 
							   href="${contextPath}/board/qboard.qo?section=${section}&pageNum=${page}">
								${page}
							</a>
						</c:when>
						<c:otherwise>
							<%--페이지 번호를 클릭하면 section값과 pageNum값을 컨트롤러로 전송합니다. --%>
							<a class="no-unline"
							   href="${contextPath}/board/qboard.qo?section=${section}&pageNum=${page}">
								${page}
							</a>
						</c:otherwise>
					</c:choose>
				
				</c:forEach>
			</c:when>
		</c:choose>
	</c:if>	
 <c:if test="${id!=null}">
 	<a class="uline" href="${contextPath}/board/articleForm.qo"><p align="right">글쓰기</p></a>
</c:if> 
</div>	
</div>

</section>
<jsp:include page="../footer.jsp"/>
</body>
<script src="<c:url value="/script/check.js" />"></script>
</html>