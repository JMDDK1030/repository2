package member.Action;

import member.db.MemberBean;
import member.db.MemberDAO;

//부장
//BoardDAO객체 생성한후 각 기능별로 DB작업할 메소드를 호출하여 응답할 결과를 얻어
//BoardController서블릿에게 보고 하는 클래스
public class MemberService {

	MemberDAO memberDAO;
	
	public MemberService() { memberDAO = new MemberDAO(); }

	//1.회원 가입 메소드

	public void insertmb(MemberBean memberBean) {
		
		memberDAO.insertMember(memberBean);
		
	}

	//2.회원 정보 출력 메소드
	
	public MemberBean selMember(String id) {
		
		return memberDAO.selectMember(id);
	}
	
	//3.회원 정보 수정 메소드

	public void updatemb(MemberBean memberBean) {
		
		memberDAO.modifyMember(memberBean);
		
	}


	//4.회원 탈퇴 메소드
	
	public void delmb(String id ,String pw) {
		
		memberDAO.deleteMember(id,pw);
		
	}

	
	//5.로그인 메소드
	
	public int LoginMember(String id, String pw) {
		
		return memberDAO.userCheck(id, pw);
			
	}

	//6.중복체크 
	public boolean useridcheck(String id) {
		
		return memberDAO.idCheck(id);
		
	}



	
	
	
	
}
