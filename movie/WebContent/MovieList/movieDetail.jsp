<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	request.setCharacterEncoding("UTF-8");
	String num = request.getParameter("fileNO");
	String URL = ""; //유튜브 예고편 링크
	switch(num){
	case "1":	URL = "rB3x60FMwm0"; break;
	case "2":	URL = "F8uPLJzR57w"; break;
	case "3":	URL = "-5Dc8EMVYBo"; break;
	case "4":	URL = "qhKCYXSHpWY"; break;
	case "5":	URL = "eFgBt6sRS_8"; break;
	case "6":	URL = "ujiLqutnym8"; break;
	case "7":	URL = "Y3pfj3yXY5M"; break;
	case "8":	URL = "WNahsXZgKaw"; break;
	case "9":	URL = "OAxu7YTMWNc"; break;
	case "10":	URL = "ywmOxCwBA4k"; break;
	case "11":  URL = "pCDq-RLfJu0"; break;
	case "12":  URL = "Xdf7ZW-AqXs"; break;
	case "13":  URL = "HLmm2ZR_VOI"; break;
	case "14":  URL = "tMKniHlecyc"; break;
	case "15":  URL = "8c8sBrMvqWY"; break;
	case "16":  URL = "uMeq3my5ZlE"; break;
	case "17":  URL = "HzOfDTKjwTo"; break;
	case "18":  URL = "pnkZFq4Y_sA"; break;
	case "19":  URL = "-Xyvn6Dxkz0"; break;
	case "20":  URL = "7g3fEWsy-ZI"; break;
	case "21":  URL = "Rdq1Uao67aQ"; break;
	case "22":  URL = "c8T2uJZVKBI"; break;
	case "23":  URL = "99DlyQZgfs8"; break;
	case "24":  URL = "-8KEWrFO-l0"; break;
	default: break;
	}	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현재상영작</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/movie.css">

</head>
<body>
	<jsp:include page="../top.jsp"/>
<script type="text/javascript">

const drawStar = (target) => {
    document.querySelector('.star span').style.width = '${target.value * 10}%';
  }
</script>
<section> 
	
<div class="top"></div>
    <div class="movie">  
      <div class="a">
      <div style="background-image:url('../img/<%=num%>.jpg');"class="fakeimg"></div>
  
      </div>
  <c:set var="file" value="${requestScope.file }"/>
 	
      <!-- 영화설명 -->
      <ul>
      	<li><h1>${file.m_name}</h1></li>
      	<li >감독 :${file.dir } </li>
      	<li style="margin-top:1rem;">배우 :${file.actor }</li>
      	<li style="margin-top:1rem;">장르 :${file.m_gebre} </li>
      	<li style="margin-top:1rem;">상영시간 :${file.m_rtime }  </li>
      	<li style="margin-top:1rem;">누적관객수 :${file.m_view } 명 </li>
      	<li style="margin-top:1rem;">영화소개 : ${file.m_info }</li>
      	<li style="margin-top:1rem;">평점 : <span>★★★★★</span></li>
      	<li><button class="button"type="button" onclick="location='ticketList.do?m_no=<%=num%>'">예매하기</button></li>
      <!-- 로그인 여부 확인을 위한 자바스크립트 유효성체크 *테스트로 작성했어요 수정하셔야합니다 :)-->
      </ul>     
    </div>
    <div class="review">
    <div class="L">
    <div class="video">
	    <div class="videowrapper" >
	    <iframe width="560" height="315" src="https://www.youtube.com/embed/<%=URL%>?controls=0"
	    frameborder="0" allowfullscreen></iframe>     
	    </div>
 	</div>
 	</div>
 	<div class="R">
 	<div class="comment">
 		<span class="star">
  		★★★★★
	  	<span>★★★★★</span>
	  	<input type="range" oninput="drawStar(this)" value="1" step="1" min="0" max="10">
	  	</span>
 		<div style="display:flex; justify-content:center;">
 		<input type="text" name="comment" placeholder="감상평을 등록해주세요.">
 		<button style="padding:1rem;"type="button">저장</button>
 		</div>
 		<div class="text">너무재미있어요!</div>
 		<div class="text">강추! ★★★★★</div>
 		<div class="text">배우들의 연기력이 뛰어났기 때문에 재미있게 볼 수 있었다.</div>
 		<div class="text">너무재미있어요!</div>
 		<div class="text">영상미 아주 훌륭합니다.강추!</div>
 		<div class="text">너무재미있어요! ★★★★★</div>
 	</div>
 	</div>
 	</div>
</section>
	<jsp:include page="../footer.jsp"/>
</body>
<script src="script/script.js"></script>
</html>