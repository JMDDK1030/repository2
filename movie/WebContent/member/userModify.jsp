<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />     


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>userModify</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/user.css">
</head>
<body>
<jsp:include page="../top.jsp"/>
  <section>
    <form action="${contextPath}/mem/modifyMember.mb" method="post">

  	<c:set var="member" value="${requestScope.Info}"/>

    <div class="top"><h1>${member.id}님 회원정보 수정</h1></div>
    
    <div class="join">
				<table>
					<tr>
						<td><input style="width: 75%" type="text" name="id" value="${member.id}" disabled> 
							<input
							style="width: 20%; float: right; cursor: pointer;" type="button"
							onclick="fn_dbCheckId()" name="dbCheckId" value="check ID">
							<input type="hidden" name="idDuplication" value="0"></td>
						<!-- 중복체크 확인을 위한 hidden 변수 -->
					</tr>
					<tr>
						<td><input type="password" name="pw" placeholder="비밀번호" value=""
							required></td>
					</tr>
					<tr>
						<td><input type="email" name="mail" placeholder="e-mail" value="${member.mail}"
							required></td>
					</tr>  	
				
		
					<tr>
						<td><input type="text" name="birth" id="birthday" value="${member.birth}"
							placeholder="생년월일" required disabled></td>
					</tr>
		
					 <tr>
						<td><input type="text" name="name" placeholder="이름" value="${member.name}"
							required maxlength="10"></td>
					 </tr>
				
					<tr>
						<td style="text-align: center">
						<c:if test="${member.gender eq 'M'}">
						Male<input style="width: 10%"
							type="radio" name="gender" value="M" checked> 
						</c:if>
						<c:if test="${member.gender eq 'F'} ">
						Female<input
							style="width: 10%" type="radio" name="gender" value="F" checked>
						</c:if>
						</td>
					</tr>
					<tr>
						<td><input type="text" name="phone" placeholder="phone" value="${member.phone}"
							required></td>
					</tr>
					<%
					java.util.Date date = new java.util.Date();
					SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
					String strdate = simpleDate.format(date);
					%>
					<tr>
						<td style="text-align: center"><input type="text"
							name="regdate" value="<%=strdate%>" disabled></td>
					</tr>
					<tr>
							<td>
							<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
							<input type="text" class="memtxt" id="sample4_postcode"
							name="address1" class="mini" placeholder="우편번호" value="${member.address1}">
							<input type="text" class="memtxt" id="sample4_roadAddress"
							name="address2" placeholder="도로명주소" value="${member.address2}"> 
							<input type="text" class="memtxt" id="sample4_jibunAddress" name="address3"
							placeholder="나머지주소" value="${member.address3}"> 
							<input type="button" class="button" onclick="sample4_execDaumPostcode()" 
							value="우편번호 찾기"><br>
						</td>
					<tr>	
				</table>
      </div>
	      <input class="button" type="submit" value="수정">
	      <input class="button" type="reset" value="되돌리기">
	      <input class="button" type="button" value="취소" onclick="location.href='../mem/MemberPage.mb'">
 
    </form>
  </section>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
	<script>
		//본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
		function sample4_execDaumPostcode() {
			new daum.Postcode(
					{
						oncomplete : function(data) {
							// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

							// 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
							// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
							var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
							var extraRoadAddr = ''; // 도로명 조합형 주소 변수

							// 법정동명이 있을 경우 추가한다. (법정리는 제외)
							// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
							if (data.bname !== ''
									&& /[동|로|가]$/g.test(data.bname)) {
								extraRoadAddr += data.bname;
							}
							// 건물명이 있고, 공동주택일 경우 추가한다.
							if (data.buildingName !== ''
									&& data.apartment === 'Y') {
								extraRoadAddr += (extraRoadAddr !== '' ? ', '
										+ data.buildingName : data.buildingName);
							}
							// 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
							if (extraRoadAddr !== '') {
								extraRoadAddr = ' (' + extraRoadAddr + ')';
							}
							// 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
							if (fullRoadAddr !== '') {
								fullRoadAddr += extraRoadAddr;
							}

							// 우편번호와 주소 정보를 해당 필드에 넣는다.
							document.getElementById('sample4_postcode').value = data.zonecode; //5자리 새우편번호 사용
							document.getElementById('sample4_roadAddress').value = fullRoadAddr;
							document.getElementById('sample4_jibunAddress').value = data.jibunAddress;

							// 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
							if (data.autoRoadAddress) {
								//예상되는 도로명 주소에 조합형 주소를 추가한다.
								var expRoadAddr = data.autoRoadAddress
										+ extraRoadAddr;
								document.getElementById('guide').innerHTML = '(예상 도로명 주소 : '
										+ expRoadAddr + ')';

							} else if (data.autoJibunAddress) {
								var expJibunAddr = data.autoJibunAddress;
								document.getElementById('guide').innerHTML = '(예상 지번 주소 : '
										+ expJibunAddr + ')';

							} else {
								document.getElementById('guide').innerHTML = '';
							}
						}
					}).open();
		}
	</script>
  
 <jsp:include page="../footer.jsp"/>

</body>
</html>