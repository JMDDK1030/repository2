package member.Action;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.db.MemberBean;



@WebServlet("/mem/*") 
public class MemberController extends HttpServlet{

	MemberBean memberBeam;
	MemberService MemberService;
	
	@Override
	public void init() throws ServletException {
		memberBeam = new MemberBean();
		MemberService = new MemberService();
	}
	
	public void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		
		request.setCharacterEncoding("utf-8");//한글처리
		
		//재요청할 뷰 주소 저장할 변수 
		String nextPage = null;
		
		//클라이언트가 요청한 URL을 받아 오는 부분
		String action = request.getPathInfo();
		
		System.out.println(action);
		
		//메인 페이지 이동
		if(action.equals("/main.mb")) {
			
			nextPage = "/movieMain.jsp";
		}//메인 페이지 이동 끝
		
		//회원가입 
		else if(action.equals("/InMember.mb")) {
			
			MemberService.insertmb(new MemberBean(
									request.getParameter("id"),
									request.getParameter("pw"),
									request.getParameter("mail"),
									request.getParameter("name"),
								    request.getParameter("gender"),
								    request.getParameter("birth"),
								    request.getParameter("phone"),
								    request.getParameter("address1"),
								    request.getParameter("address2"),
								    request.getParameter("address3")
								  ));
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입 성공');");
			out.println("location.href='../mem/MemberLogin.mb'");
			out.println("</script>");
			out.close();	

		 
		 }//회원가입 끝
		
		else if(action.equals("/usercheck.mb")){
			
			String id = request.getParameter("id");
		
			boolean result = MemberService.useridcheck(id);
			
			response.setContentType("text/html;charset=euc-kr");
			PrintWriter out = response.getWriter();

			if(result)	out.println("0"); 
			else		out.println("1");
			
			out.close();
		}
		
		
		//회원가입 페이지 이동
		else if(action.equals("/MemberJoin.mb")) {
			
			nextPage = "/member/userJoin.jsp";
		}//회원가입 페이지 이동 끝

		//로그인 페이지 이동
		else if(action.equals("/MemberLogin.mb")) {
			
			nextPage = "/member/userLogin.jsp";
		}//로그인 페이지 이동 끝
		
		//로그인
		else if(action.equals("/LogMember.mb")) {
			
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			int check = MemberService.LoginMember(id,pw);
			
			// check==0  "비밀번호틀림" 뒤로이동
			if(check==0){
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out=response.getWriter();
				out.println("<script>");
				out.println("alert('아이디 또는 비밀번호틀림');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				
			// check==-1 "아이디없음" 뒤로이동	
			}else if(check==-1){
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out=response.getWriter();
				out.println("<script>");
				out.println("alert('아이디없음');");
				out.println("history.back();");
				out.println("</script>");
				out.close();	
			}
			
			HttpSession session = request.getSession();
			
			session.setAttribute("id", id);
			
			nextPage = "/mem/main.mb";
		}//로그인 끝
		

		//로그아웃
		else if(action.equals("/MemberLogout.mb")) {
			
			HttpSession session = request.getSession();
			
			session.invalidate();
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('로그아웃');");
			out.println("location.href='../mem/main.mb'");
			out.println("</script>");
			out.close();	
		}//로그아웃 끝
		
		//회원 정보 페이지 이동
		else if(action.equals("/MemberPage.mb")) {
			
			HttpSession session = request.getSession();
			
			String id = session.getAttribute("id").toString();
			
			request.setAttribute("id", id);
		
			nextPage = "/member/userPage.jsp";
		}
		
		//회원 정보 출력
		else if(action.equals("/MemberInfo.mb")) {
			
			HttpSession session = request.getSession();
			
			String id = session.getAttribute("id").toString();
			
			memberBeam = MemberService.selMember(id);
			
			request.setAttribute("Info", memberBeam);
			
			nextPage = "/member/userModify.jsp";
		}
		
	
		
		//회원 정보 수정
		else if(action.equals("/modifyMember.mb")) {
			
			String pw =request.getParameter("pw");
			String mail =request.getParameter("mail");
			String name =request.getParameter("name");
			String phone =request.getParameter("phone");
			String address1 =request.getParameter("address1");
			String address2 =request.getParameter("address2");
			String address3 =request.getParameter("address3");
			
			memberBeam.setPw(pw);
			memberBeam.setMail(mail);
			memberBeam.setName(name);
			memberBeam.setPhone(phone);
			memberBeam.setAddress1(address1);
			memberBeam.setAddress2(address2);
			memberBeam.setAddress3(address3);
			
			MemberService.updatemb(memberBeam);
							
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('수정 완료');");
			out.println("location.href='../mem/MemberInfo.mb?id=" + request.getParameter("id") + "'");
			out.println("</script>");
			
			return;
		}
	
		//회원 탈퇴 페이지 이동
		
		else if(action.equals("/MemberDelete.mb")) {
			
			nextPage = "/member/userDelete.jsp";
		}
		
		
		//회원 탈퇴
		else if(action.equals("/delMember.mb")) {
			
			HttpSession session = request.getSession();
			
			String id = session.getAttribute("id").toString();
			
			String pw = request.getParameter("pw");
			
			MemberService.delmb(id,pw);
			
			int check = MemberService.LoginMember(id,pw);
			
			// check==0  "비밀번호틀림" 뒤로이동
			if(check==0){
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out=response.getWriter();
				out.println("<script>");
				out.println("alert('비밀번호틀림');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			else {
			session.invalidate();
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('탈퇴');");
			out.println("location.href='../mem/main.mb'");
			out.println("</script>");
			out.close();	
			}
		}
		
		//시네마 위치
		else if(action.equals("/Site.ss")) {
			
			nextPage = "/Site.jsp";
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//그런다음 RequestDispatcher방식으로 뷰 페이지로 포워딩
				RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
				dispatch.forward(request, response);
		
		
		
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAll(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAll(request, response);
	}
	

}
