
function fn_checkAll() {
    var f = document.frm;
    if(f.id.value == "") {
        alert("아이디가 입력되지 않았습니다.");
		f.id.focus();
		return false;
	}
	
	if(f.idDuplication.value == "idcheck"){
		alert("아이디 중복체크를 해주세요");
		return false;
	}

    if(f.pw.value == "") {
        alert("비밀번호가 입력되지 않았습니다.");
		f.pw.focus();
		return false;
	}
	if(f.pw.value.length < 4 ){
		alert("비밀번호는 4자이상으로 입력해주세요.");
		f.pw.focus();
		return false;
	}	
	if(f.pw2.value != f.u_pw.value){
		alert("비밀번호를 확인해주세요.");
		f.pw2.focus();
		return false;
	}
	if(f.mail.value == "") {
        alert("메일이 입력되지 않았습니다.");
		f.mail.focus();
		return false;
	}
	if(f.birth.value == ""){
		alert("생년월일을 입력해주세요.");
		return false;
	}
	if(f.name.value == "") {
        alert("이름이 입력되지 않았습니다.");
		f.name.focus();
		return false;
	}
	if(f.gender.value == ""){
		alert("성별을 선택하세요.");
		return false;
	}
	var phone = /^[0-9]{8,13}$/; //전화번호 숫자만
	if(!phone.test(f.phone.value)){
		alert("전화번호를 확인해주세요.");
		f.phone.focus();
		return false;
	}
	
	if(f.address1.value == "" || f.address2.value == "" || f.address3.value == ""){
		alert("주소를 입력해주세요");
		f.address1.focus();
		return false;
	}
	
	f.submit();
}

	
	function fn_dbCheckId(){ // id 중복체크

		window.open("../member/userCheckDuplication.jsp",
					"chkForm","width=500,height=300,resizable = no, scrollbars = no");
	}




// 게시판 유효성 체크
function fn_submit(){
	var f = document.frm;
	if(f.q_title.value.trim().length == ""){
		alert("제목을 입력해주세요.");
		return false;
	}
	if(f.q_con.value.trim().length == "" || f.q_con.value.trim().length < 10){
		alert("내용을 10자 이상으로 작성해주세요.");
		return false;
	}
	f.submit();
}

function fn_boardDelete(q_no){ //게시물 삭제 컨펌
	if(confirm("정말 삭제하시겠습니까?")){
		location="boardPro.jsp?GUBUN=D&q_no="+q_no;		
	}
}