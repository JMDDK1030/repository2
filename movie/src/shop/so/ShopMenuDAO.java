package shop.so;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class ShopMenuDAO {

	private DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	ResultSet rs;

	
	public ShopMenuDAO() {

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
	}//생성자 끝

	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close(); // 커넥션 풀로 Connection객체 사용 후 반납

			System.out.println("커넥션 공간에 Connection객체 사용 후 반납");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}// close메소드 끝	

	public List<ShopMenuVO> selectMenu() {

		   List menuList = new ArrayList();
		   
		   try {
				//1. 커넥션풀(DataSource객체)에서 커넥션(Connection객체) 얻기 
				//   DB연결
				conn = dataFactory.getConnection();
				
				//2. 계층형 구조로 전체 글을 조회 하는 오라클의 계층형 SQL문
				String query = "SELECT * from shopmenu";
							
				
				//3. PreparedStatement객체 얻기
				pstmt = conn.prepareStatement(query);
				
				//4. 조회
				rs = pstmt.executeQuery();
				
				//5. 조회한 결과를 ResultSet에서 꺼내어서 ArticleVO객체에 저장후
				//   ArticleVO객체들을 ArrayList배열에 최총 추가추가 합니다.
				while(rs.next()) {
					
					
					int mcno = rs.getInt("mcno");
					String mcontent = rs.getString("mcontent");
					String menuimg = rs.getString("menuimg");
					String menuname = rs.getString("menuname");
	
					//한 행 단위로 검색된 글의 정보들을 ShopMenuVO객체의 각변수에 저장
					ShopMenuVO menu = new ShopMenuVO();
					menu.setMcno(mcno);
					menu.setMcontent(mcontent);
					menu.setMenuimg(menuimg);
					menu.setMenuname(menuname);
					
					//ArrayList배열에 ArticleVO객체 추가
					menuList.add(menu);		
				}//while문
				    
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//자원해제
				close();
			}
				
			return menuList;
			
		}//selectMenu()
	


	
	
	


}
