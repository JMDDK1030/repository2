package member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

//DB의 테이블과 연결하여 DB작업하는 클래스 
public class MemberDAO {
		
		DataSource dataFactory;
		Connection con;//데이터베이스와 연결을 담당 하는 객체 저장 변수
		//자바코드로 작성한 동적쿼리문을 연결된 DBMS의 테이블에 전달하여 
		//실행할 역할의 객체를 저장할 변수 
		//참고. Statement를 더욱 업그레이드 하여 나온 객체
		PreparedStatement pstmt;
		ResultSet rs;//DB로부터 SELECT쿼리문의 결과를 임시로 저장할 
					 //ResultSet객체 메모리를 담을 변수 
		String sql;
		
		public MemberDAO() {
			
			try {
				//커넥션풀 얻기
				//1. 자바의 네이밍 서비스(JNDI)에서 이름과 실제 객체를 연결 해주는 역할을 하는
				//   InitialContext()객체를 생성 하여 저장
				//   이객체는 네이밍 서비스를 이용하기 위한 시작점입니다.
				//   이객체의 lookup()메서드에 이름을 건네 원하는 객체를 찾아올수 있습니다.
				Context initCtx = new InitialContext();
				//2. "java:comp/env"경로를 얻은 InitialContext객체를 얻습니다.
				//여기서 "java:comp/env"경로는 현재 웹프로젝트의 루트 디렉터리 경로라 생각하면 됩니다.
				//즉~ 현재 웹프로젝트 내부에서 사용할수 있는 모든 자원은 "java:comp/env"아래에 위치합니다.
				Context ctx = (Context)initCtx.lookup("java:comp/env");
				//3. "java:comp/env"아래에 위치한 "dbcp_myoracle"자원을 얻어옵니다.
				//   이자원이 바로 앞서 설정한 DataSource(커넥션풀)입니다.
				//여기서 "dbcp_myoralce"은 context.xml파일에 추가한 
				//<RecourceLink>에 있는 name속성의 값입니다.
				dataFactory = (DataSource)ctx.lookup("dbcp_myoracle");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		
		//연결 해제 (자원반납)
		//설명
		//DB관련 작업을 모두 마쳤다면 자원을 절약하기 위해 연결을 해제해 주는 것이 좋습니다.
		//close메서드는 DB관련 작업 객체의 자원을 해재 하는 메소드 입니다.
		public void close() {
			
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close(); //커넥션 풀로 Connection객체 사용후 반납
				
				System.out.println("커넥션풀 공간에 Connection객체 사용후 반납");
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}//close메소드 끝


		//회원가입 
		public void insertMember(MemberBean memberBean) {
			
			try {
			
				con = dataFactory.getConnection();
				sql = "insert into member values(?,?,?,?,?,?,?,?,?,?,sysdate)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, memberBean.getId());
				pstmt.setString(2, memberBean.getPw());
				pstmt.setString(3, memberBean.getName());
				pstmt.setString(4, memberBean.getMail());
				pstmt.setString(5, memberBean.getGender());
				pstmt.setString(6, memberBean.getBirth());
				pstmt.setString(7, memberBean.getPhone());
				pstmt.setString(8, memberBean.getAddress1());
				pstmt.setString(9, memberBean.getAddress2());
				pstmt.setString(10, memberBean.getAddress3());
				
				pstmt.executeUpdate();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				close();
			}
			
			
		}

		//회원 정보 출력
		public MemberBean selectMember(String id) {
			
			MemberBean mb = null;
			
			try {
				
				con = dataFactory.getConnection();
				sql = "select * from member where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					mb = new MemberBean(rs.getString("id"),
										rs.getString("pass"),
										rs.getString("mail"),
										rs.getString("name"),
										rs.getString("gender"),
										rs.getString("birth"),
										rs.getString("phone"),
										rs.getString("address1"),
									    rs.getString("address2"),
										rs.getString("address3")
										);	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				close();
			}
		
			return mb;
		}

		//로그인
		public int userCheck(String id, String pw) {
			
				
				int check=-1;//1 -> 아이디, 비밀번호 맞음
							//0 -> 아이디 맞음, 비밀번호 틀림
							//-1 -> 아이디 틀림
				try {
					
					con=dataFactory.getConnection();
					sql="select pass from member where id=?";
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, id);
					rs=pstmt.executeQuery();
					// rs 첫번째행 이동하여.. id에 해당하는 데이터가 pass가 있으면.
					if(rs.next()){
						//로그인시.. 입려한 pass와  DB에 저장되어 있는 pass가 같으면
						if(pw.equals(rs.getString("pass"))){
							check=1;//아이디 맞음,비밀번호 맞음 판별값 저장
						//비밀번호가 틀리면
						}else{
							check=0;//아이디 맞음, 비밀번호틀림 판별값 저장
						}
					//id에 해당하는 데이터가 pass가 없으면(아이디가 없다는 뜻과 같음)	
					}else{
						check=-1; //아이디 없음 판별값 저장
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					close();
				}
				return check;

		}

		//회원 정보 수정
		public void modifyMember(MemberBean memberBean) {
			
			try {
				
				con = dataFactory.getConnection();
				sql = "update member set pass=?,mail=?,name=?,phone=?,address1=?,address2=?,address3=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, memberBean.getPw());
				pstmt.setString(2, memberBean.getMail());
				pstmt.setString(3, memberBean.getName());
				pstmt.setString(4, memberBean.getPhone());
				pstmt.setString(5, memberBean.getAddress1());
				pstmt.setString(6, memberBean.getAddress2());
				pstmt.setString(7, memberBean.getAddress3());
				pstmt.setString(8, memberBean.getId());
				pstmt.executeUpdate();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				close();
			}
			
			
		}

		//회원탈퇴
		public void deleteMember(String id ,String pw) {
			
			try {
				con = dataFactory.getConnection();
				sql = "delete from member where id=? and pass=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, pw);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				close();
			}
			
			
		}


		public boolean idCheck(String id) {
			
			boolean x = false;
			
			try {
				con=dataFactory.getConnection();
				sql="select id from member where id=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs=pstmt.executeQuery();
				
				if (rs.next()) {
					x = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				close();
			}
			return x;
		}
		
	
	
	
	
}
