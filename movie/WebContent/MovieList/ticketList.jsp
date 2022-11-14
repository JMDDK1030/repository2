<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>


<meta charset="UTF-8">
<title>영화 예매</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/ticket.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />



</head>
<body>

<jsp:include page="../top.jsp"/>


	<section>
		<div class="top">
			<h1>영화 예매</h1>
		</div>
		<div class="ticket">
		<c:set var="ticket" value="${list}"/>
		<c:set var="room" value="${roomList}"/>
		
	 		<div class="ticketBox">
				<div class="ticketHead"><h3>영화</h3></div>
				<c:choose>
					<c:when test="${!empty list}"> 
						<c:forEach var="list" items="${list}">							
							<div class="ticketList"								 
								 onClick="location='${contextPath}/Movie/ticketlist.do?title=${list.m_mo}'" > 
								 <a>${list.m_name}</a>
							</div>
						</c:forEach>
					</c:when>
				</c:choose> 
			</div>		

			
	 		<div class="ticketBox">
				<div class="ticketHead"><h3>관선택</h3></div>
				<c:choose>
					<c:when test="${!empty roomList}"> 
						<c:forEach begin="0" end="3" step="1" var="j">
							<div class="ticketList"
								 onClick="location='${contextPath}/ticket/tixketinfo.to?title=${param.title}&room=${room[j].roomname}'"> 
								 <a>${room[j].roomname}</a> 
							</div>
						</c:forEach>
					</c:when>
				</c:choose> 
			</div>


		</div>
	</section>


<jsp:include page="../footer.jsp"/>
	
</body>
</html>