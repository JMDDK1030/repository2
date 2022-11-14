package board.qbo;

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


// MVC 중에 Model역할 을 하는 DB관련 작업하는 클래스 
public class BoardDAO {
	
	private DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//생성자의 역할 -> new MemberDAO()객체 생성시 생성자가 호출되는데.. DataSource커넥션풀 객체를 얻는 역할을 함.
	public BoardDAO() {
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
	   
		public int selectToArticles() { //글 개수 조회 메소드 
			
			try {
				conn = dataFactory.getConnection();
				String query = "select count(articleNo) from q_board"; //전체 글번호의 갯수 조회 
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				if(rs.next())
					return (rs.getInt(1));	//조회된 글번호 갯수 리턴
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//자원해제
				ResourceClose();
			}
			
			return 0;
		}	   
	   
	
		public List selectAllArticles(Map  pagingMap) {//글 목록 조회 메소드 
		
		List articlesList = new ArrayList();
		
		int section = (Integer)pagingMap.get("section");
		int pageNum = (Integer)pagingMap.get("pageNum");
			
		try {
			//1. 커넥션풀(DataSource객체)에서 커넥션(Connection객체) 얻기 
			//   DB연결
			conn = dataFactory.getConnection();
			
			//2. 계층형 구조로 전체 글을 조회 하는 오라클의 계층형 SQL문
			String query = 
					
				"SELECT * FROM ( "
				+               "SELECT ROWNUM as recNUM, " 
				+                        "LVL,"                            
				+                        "articleNO, "
				+                        "parentNO, "
				+                         "title, "
				+                         "id, "
				+                         "writedate "
				+                       "FROM ( "
				+                              "SELECT LEVEL as LVL, "  
				+                                    "articleNO, "
				+                                     "parentNO, "
				+                                     "title, "
				+                                     "id, "
				+                                     "writedate "
				+                                "FROM q_board "
				+                                "START WITH parentNO=0 "
				+                                "CONNECT BY PRIOR articleNO=parentNO "
				+                                "ORDER SIBLINGS BY articleNO DESC "
				+                ")) "
				+"where "
				+"recNum between (?-1)*40+(?-1)*4+1 and (?-1)*40+?*4";
				// section과 pageNum값으로 레코드 번호의 범위를 조건으로 정합니다.
				//(이들 값이 각각 1로 전송되었으므면 between 1 and 10 이 됩니다.)
			
			
			//3. PreparedStatement객체 얻기
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, section);
			pstmt.setInt(2, pageNum);
			pstmt.setInt(3, section);
			pstmt.setInt(4, pageNum);
			
			//4. 조회
			rs = pstmt.executeQuery();
			
			//5. 조회한 결과를 ResultSet에서 꺼내어서 ArticleVO객체에 저장후
			//   ArticleVO객체들을 ArrayList배열에 최총 추가추가 합니다.
			while(rs.next()) {
				int level = rs.getInt("lvl"); //각 글의 깊이(계층)를 level변수에 저장합니다.
				int articleNO = rs.getInt("articleNO"); //글번호는 숫자이므로 getInt()로 가져옵니다.
				int parentNO = rs.getInt("parentNO"); //부모 글번호 
				String title = rs.getString("title"); //글제목
				String id = rs.getString("id"); //글쓴이의 ID
				Date writeDate = rs.getDate("writeDate");//글쓴날짜
				
				//한 행 단위로 검색된 글의 정보들을 ArticleVO객체의 각변수에 저장
				ArticleVO article = new ArticleVO();
				article.setLevel(level);
				article.setArticleNO(articleNO);
				article.setParentNO(parentNO);
				article.setTitle(title);
				article.setId(id);
				article.setWriteDate(writeDate);
				
				//ArrayList배열에 ArticleVO객체 추가
				articlesList.add(article);		
			}//while문
			    
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//자원해제
			ResourceClose();
		}
		
		return articlesList;//ArrayList배열을 BoardService로 리턴
	}
	
	
	//DB에 새글을 추가하기전에 DB에 저장된 가장 최신 글번호를 검색해서 제공하는 메소드
	private int getNewArticleNo() {
		try {
			conn = dataFactory.getConnection();
			//저장된 글번호중 가장 큰 글번호 조회 하는 SQL문
			String query = "SELECT max(articleNO) from q_board";
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
	public int insertNewArticle(ArticleVO article) {
		
		//DB에 INSERT시킬 새로운글번호 구해오기 위해 메소드 호출!
		int articleNO = getNewArticleNo();
		
		try {
			conn = dataFactory.getConnection();
		
			int parentNO =  article.getParentNO();
			String title = article.getTitle();
			String id = article.getId();
			
			String query = "INSERT INTO q_board(articleNO, parentNO, title, id, writedate) "
					     + " VALUES(?,?,?,?,sysdate)";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, articleNO);
			pstmt.setInt(2, parentNO);
			pstmt.setString(3, title);
			pstmt.setString(4, id);
			
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원해제
			ResourceClose();
		}
		
		return articleNO; //추가한 새 글의 글번호를 반환
		
	}

	//매개변수로 전달 받은 글 번호를 이용해 글 정보를 조회 하는 메소드 
	public ArticleVO selectArticle(int articleNO) {
		
		//조회된 글 하나의 정보를 저장할 ArticleVO객체 생성
		ArticleVO article = new ArticleVO();
		
		try {
			  conn = dataFactory.getConnection();
			  
			  String query = "select articleNO,parentNO,title,id,writeDate"
					       + " from q_board where articleNO=?";
			  
			  pstmt = conn.prepareStatement(query);
			  pstmt.setInt(1, articleNO);
			  ResultSet rs = pstmt.executeQuery();
			  rs.next();
			  article.setArticleNO(rs.getInt("articleNO"));
			  article.setParentNO(rs.getInt("parentNO"));
			  article.setTitle(rs.getString("title"));
			  article.setId(rs.getString("id"));
			  article.setWriteDate(rs.getDate("writeDate"));
			  
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		return article; //BoardService로 리턴 
	}

	//매개변수로 전달된 수정할 데이터에 대해 
	//이미지 파일을 수정하는 경우와 이미지 파일을 수정하지 않는 경우를 구분해 
	//동적으로 UPDATE문을 생성하여 수정 데이터를 DB에 반영합니다.
	public void updateArticle(ArticleVO article) {
		
		int articleNO = article.getArticleNO();
		String title = article.getTitle();	
		try {			
			conn = dataFactory.getConnection();
			
			String query = "update q_board set title=? "
					+ "where articleNO=?";

			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setInt(2, articleNO);
		
		pstmt.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//자원해제
			ResourceClose();
		}
			
	}
	
	
	public List<Integer> selectRemoveArticles(int articleNO) {
	
		List<Integer> articleNOList = new ArrayList<Integer>();
		
		try {
				conn = dataFactory.getConnection();
						
				String query = "SELECT articleNO FROM q_board "
							   +"START WITH articleNO=? "
							   +"CONNECT BY PRIOR articleNO = parentno";
			
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
				String query = "DELETE FROM q_board "
							 + "WHERE articleNO  in ("
		                     + "        SELECT articleNO FROM q_board "
		                     + "        START WITH articleNO=? "          
		                     + "        CONNECT BY PRIOR articleNO = parentno)";
				
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

	

}//BoardDAO클래스 닫는 기호 











