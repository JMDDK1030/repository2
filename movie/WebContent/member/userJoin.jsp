<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"  />   


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/user.css">
<link rel="stylesheet" href="../css/jquery-ui.css">
<script src="../script/jquery-1.12.4.js"></script>
<script src="../script/jquery-ui.js"></script>
<script>
	$(function() {//#은 id 값을 의미한다.
		$("#birthday").datepicker({
			changeMonth : true,
			changeYear : true
		});

	});
</script>

</head>
<body onload="document.frm.id.focus()">
	<jsp:include page="../top.jsp"/>

	<div class="top">
		<h1>회원 가입</h1>
	</div>
	<section>
		<form name="frm" action="${contextPath}/mem/InMember.mb" method="post">

			<div class="join">
				<table>
					<tr>
						<td><input style="width: 75%" type="text" name="id" placeholder="아이디" required> 
							<input style="width: 20%; float: right; cursor: pointer;" type="button"
								   onclick="fn_dbCheckId()" name="dbCheckId" value="check ID">
							<input type="hidden" name="idDuplication" value="idcheck"></td>
						<!-- 중복체크 확인을 위한 hidden 변수 -->
					</tr>
					<tr>
						<td><input type="password" name="pw" placeholder="비밀번호"
							required></td>
					</tr>
					<tr>
						<td><input type="password" name="pw2" placeholder="비밀번호 확인"
							required></td>
					</tr>
					<tr>
						<td><input type="email" name="mail" placeholder="e-mail"
							required></td>
					</tr>

					<tr>
						<td><input type="text" name="birth" id="birthday"
							placeholder="생년월일" required></td>
					</tr>
					<tr>
						<td><input type="text" name="name" placeholder="이름"
							required maxlength="10"></td>
					</tr>
					<tr>
						<td style="text-align: center">
						Male<input style="width: 10%" type="radio" name="gender" value="M" required> 
						Female<input style="width: 10%" type="radio" name="gender" value="F" required>
						</td>
					</tr>
					<tr>
						<td><input type="text" name="phone" placeholder="phone"
							required></td>
					</tr>
					<%
						java.util.Date date = new java.util.Date();
					SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
					String strdate = simpleDate.format(date);
					%>
					<tr>
						<td style="text-align: center"><input type="text"
							name="regdate" value="<%=strdate%>" readonly></td>
					</tr>
					<tr>
						<td>
					<input type="text" id="sample4_postcode" placeholder="우편번호" name="address1" required>
					<input type="text" id="sample4_roadAddress" placeholder="도로명주소" name="address2" required>
					<input type="text" id="sample4_jibunAddress" placeholder="부가주소" name="address3" required>
					<span id="guide" style="color:#999;display:none"></span>
					<input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br>
						</td>
					<tr>	
				</table>
			
			</div>
			<input class="button" type="submit"
				onclick="fn_checkAll();return false;" value="회원가입"> <input
				class="button" type="reset" value="다시입력">
		</form>
	</section>
	

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
                
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("sample4_jibunAddress").value = extraRoadAddr;
                } else {
                    document.getElementById("sample4_jibunAddress").value = '';
                }

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
        }).open();
    }
</script>
	<jsp:include page="../footer.jsp"/>
</body>
<script src="../script/check.js"></script>
</html>