package ticket;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import file.FileVO;



// MVC 중에 Model역할 을 하는 DB관련 작업하는 클래스 
public class TicketDAO {
	
	private DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//생성자의 역할 -> new MemberDAO()객체 생성시 생성자가 호출되는데.. DataSource커넥션풀 객체를 얻는 역할을 함.
	public TicketDAO() {
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
	
	   //자원해제 메소드 	
	   private void ResourceClose() {   
		   try {
				   if(pstmt != null) pstmt.close();
				   if(rs != null) rs.close();
				   if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	   }	

		//DB에 새글을 추가하기전에 DB에 저장된 가장 최신 글번호를 검색해서 제공하는 메소드
		private int getNewArticleNo() {
			try {
				conn = dataFactory.getConnection();
				//저장된 글번호중 가장 큰 글번호 조회 하는 SQL문
				String query = "SELECT max(marticleNO) from mticket";
				pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {//최신글번호가 검색되었다면?
					return (rs.getInt(1) + 1); //새로운글 추가시 가장큰 글번호에 + 1한 번호를 사용하기 위해 리턴
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//자원해제
				ResourceClose();
			}	
			return 0;
		}	   
	   
	   
	   
	   
	//DB에 새글을 추가 시키는 메소드
	public int insertNewArticle(TicketVO article) {
		
		//DB에 INSERT시킬 새로운글번호 구해오기 위해 메소드 호출!
		int marticleNO = getNewArticleNo();
		
		try {
			conn = dataFactory.getConnection();
		
			
			String movieNO = article.getMovieNO();
			String roomNO = article.getRoomNO();
			String title = article.getTitle();
			String id = article.getId();
			
			String query = "INSERT INTO mticket(marticleNO, movieNO, roomNO, title, id, writedate) "
					     + " VALUES(?,?,?,?,?,sysdate)";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, marticleNO);
			pstmt.setString(2, movieNO);
			pstmt.setString(3, roomNO);
			pstmt.setString(4, title);
			pstmt.setString(5, id);
			
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원해제
			ResourceClose();
		}
		
		return marticleNO; //추가한 새 글의 글번호를 반환
		
	}


		//매개변수로 전달 받은 글 번호를 이용해 글 정보를 조회 하는 메소드 
		public List<FileVO> selectMenu() {

			List filesList = new ArrayList();

			try {
				conn = dataFactory.getConnection();

				String query = "SELECT title from moviefile";

				pstmt = conn.prepareStatement(query);

				rs = pstmt.executeQuery();

				while (rs.next()) {

					String title = rs.getString("title");

					FileVO file = new FileVO();
					file.setTitle(title);
					

					filesList.add(file);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ResourceClose();
			}

			return filesList;
				
			}//selectMenu()
		
		//나의예약정보
		public List<TicketVO> selectmyMenu() {

			   List menuList = new ArrayList();
			   
			   try {
					//1. 커넥션풀(DataSource객체)에서 커넥션(Connection객체) 얻기 
					//   DB연결
					conn = dataFactory.getConnection();
					
					//2. 계층형 구조로 전체 글을 조회 하는 오라클의 계층형 SQL문
					String query = "SELECT marticleNO,movieNO,roomNO,title from mticket";
								
					
					//3. PreparedStatement객체 얻기
					pstmt = conn.prepareStatement(query);
					
					//4. 조회
					rs = pstmt.executeQuery();
					
					//5. 조회한 결과를 ResultSet에서 꺼내어서 ArticleVO객체에 저장후
					//   ArticleVO객체들을 ArrayList배열에 최총 추가추가 합니다.
					while(rs.next()) {
						
						
						int marticleNO = rs.getInt("marticleNO");
						String movieNO = rs.getString("movieNO");
						String roomNO = rs.getString("roomNO");
						String title = rs.getString("title");
		
						//한 행 단위로 검색된 글의 정보들을 ShopMenuVO객체의 각변수에 저장
						TicketVO tmenu = new TicketVO();
						tmenu.setMarticleNO(marticleNO);
						tmenu.setMovieNO(movieNO);
						tmenu.setRoomNO(roomNO);
						tmenu.setTitle(title);
						
						//ArrayList배열에 ArticleVO객체 추가
						menuList.add(tmenu);		
					}//while문
					    
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					//자원해제
					ResourceClose();
				}
					
				return menuList;
					
		}

	public List<Integer> selectRemoveArticles(int articleNO) {
	
		List<Integer> articleNOList = new ArrayList<Integer>();
		
		try {
				conn = dataFactory.getConnection();
						
				String query = "SELECT articleNO FROM mticket "
							   +"START WITH articleNO=? "
							   +"CONNECT BY PRIOR articleNO";
			
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, articleNO);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					articleNOList.add(rs.getInt("articleNO"));
				}

			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//자원해제
			ResourceClose();
		}
		return articleNOList;
	}


	public void deleteArticle(int articleNO) {
		
		try {
				conn = dataFactory.getConnection();//DB연결
				// 오라클의 계층형 SQL문을 이용해서 삭제 글과 관련된 자식 답변글까지 모두 삭제합니다.
				String query = "DELETE FROM mticket "
							 + "WHERE articleNO  in ("
		                     + "        SELECT articleNO FROM mticket "
		                     + "        START WITH articleNO=? "          
		                     + "        CONNECT BY PRIOR articleNO)";
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, articleNO);
				pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//자원해제
			ResourceClose();
		}
	}

	public List<RoomVO> selectroomMenu() {

		   List menuList = new ArrayList();
		   
		   try {
				//1. 커넥션풀(DataSource객체)에서 커넥션(Connection객체) 얻기 
				//   DB연결
				conn = dataFactory.getConnection();
				
				//2. 계층형 구조로 전체 글을 조회 하는 오라클의 계층형 SQL문
				String query = "SELECT roomNO,roomname from movieroom";
							
				
				//3. PreparedStatement객체 얻기
				pstmt = conn.prepareStatement(query);
				
				//4. 조회
				rs = pstmt.executeQuery();
				
				//5. 조회한 결과를 ResultSet에서 꺼내어서 ArticleVO객체에 저장후
				//   ArticleVO객체들을 ArrayList배열에 최총 추가추가 합니다.
				while(rs.next()) {
					
					
					int roomNO = rs.getInt("roomNO");
					String roomname = rs.getString("roomname");
	
					//한 행 단위로 검색된 글의 정보들을 ShopMenuVO객체의 각변수에 저장
					RoomVO rmenu = new RoomVO();
					rmenu.setRoomNO(roomNO);
					rmenu.setRoomname(roomname);

					
					//ArrayList배열에 ArticleVO객체 추가
					menuList.add(rmenu);		
				}//while문
				    
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//자원해제
				ResourceClose();
			}
				
			return menuList;
					
	}

	public List<RoomEXVO> selectroomEXMenu() {
		   List menuList = new ArrayList();
		   
		   try {
				//1. 커넥션풀(DataSource객체)에서 커넥션(Connection객체) 얻기 
				//   DB연결
				conn = dataFactory.getConnection();
				
				//2. 계층형 구조로 전체 글을 조회 하는 오라클의 계층형 SQL문
				String query = "SELECT r_no, room_no from ROOMEX";
							
				
				//3. PreparedStatement객체 얻기
				pstmt = conn.prepareStatement(query);
				
				//4. 조회
				rs = pstmt.executeQuery();
				
				//5. 조회한 결과를 ResultSet에서 꺼내어서 ArticleVO객체에 저장후
				//   ArticleVO객체들을 ArrayList배열에 최총 추가추가 합니다.
				while(rs.next()) {
					
					
					int r_no = rs.getInt("r_no");
					String room_no = rs.getString("room_no");
	
					//한 행 단위로 검색된 글의 정보들을 ShopMenuVO객체의 각변수에 저장
					RoomEXVO remenu = new RoomEXVO();
					remenu.setR_no(r_no);
					remenu.setRoom_no(room_no);

					
					//ArrayList배열에 ArticleVO객체 추가
					menuList.add(remenu);		
				}//while문
				    
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//자원해제
				ResourceClose();
			}
				
			return menuList;
	}


	

}//BoardDAO클래스 닫는 기호 











