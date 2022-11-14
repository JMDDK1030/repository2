package board.qbo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



//부장
//BoardDAO객체를 생성한후 각 기능별로 DB작업할 메소드를 호출하여 응답할 결과를 얻어
//BoardController서블릿에게 보고 하는 클래스 
public class BoardService {

	BoardDAO boardDAO;
	
	//기본생성자
	public BoardService() {
		boardDAO = new BoardDAO(); //BoardService생성자 호출시 BoardDAO객체를 생성합니다.
	}
	
	//각기능 별로 DB작업할 메소드를 추가하자!!!!!!!
	
	//1. 모든 글을 조회 하기 위해 명령 하는 메소드 
	public Map  listArticles(Map<String, Integer> pagingMap){
		
		Map articlesMap = new HashMap();
		
		//전달된 pagingMap매개변수(HashMap)을 이용해 글목록을 조회합니다.
		List<ArticleVO> articlesList  = boardDAO.selectAllArticles(pagingMap);
		
		//DB에 테이블에 저장된 전체 글 수를 조회 합니다.
		int totArticles = boardDAO.selectToArticles();
		
		articlesMap.put("articlesList", articlesList); //조회된 글목록(ArrayList배열의 데이터)를 HashMap에 저장
		articlesMap.put("totArticles", totArticles);//조회된 전체 글개수를 HashMap에 저장
		
		return articlesMap;
		
	}
	//2. BoardController서블릿에서 호출하는 메소드로서!!
	//   글쓰기 창에서 입력된 정보를 ArticleVO객체에 설정한후 매개변수로 전달 받아서
	//  다시~ BoardDAO객체의 insertNewArticle()메소드를 호출하면서 추가할 새글정보(ArticleVO객체)를
	//  매개변수로 전달하여 DB에 INSERT명령하는 메소드 
	public int addArticle(ArticleVO articleVO) {
		
		 return  boardDAO.insertNewArticle(articleVO);
	}

	//3. BoardController서블릿에서 호출하는 메소드로서!!
	//   글목록 창(listArticles.jsp)에서 글제목을 눌렀을때...
	//   조회 할 글번호를 매개변수로 받아서  조회할 글번호를 BoardDAO객체의 selectArticle메소드 호출시 매개변수로 전달 하여
	//   명령 하는 메소드 !
	public ArticleVO viewArticle(int articleNO) {
		
		//조회된 정보가 저장된 ArticleVO객체를 반환받아 저장시킬 변수
		ArticleVO article = null;
		
		article = boardDAO.selectArticle(articleNO);
		
		return article; //BoardController서블릿으로 리턴(반환)
	}

	//컨트롤러에서 modArticle메소드를 호출하면 다시~ BoardDAO의 updateArticle()메소드를 호출하면서 수정할 데이터를 매개변수로 전달하여
	//UPDATE시킴
	public void modArticle(ArticleVO articleVO) {
		
		boardDAO.updateArticle(articleVO);  
	}	
	
	//글번호를 매개변수로 받아서 받은 글번호의 글과 답변자식글 까지 모두 삭제한 후 부모글과 자식글의 글번호를 조회해서 반환시키는 메소드
	public List<Integer> removeArticle(int articleNO) {

		//삭제 글번호를 articleNOList에 담아 반환한다
		List<Integer> articleNOList = boardDAO.selectRemoveArticles(articleNO);
		
		//삭제 메소드 호출
		boardDAO.deleteArticle(articleNO);
		
		return articleNOList; //컨트롤러로 반환
	}

	//답글쓰기는 새글쓰기와 동일하게 BoardDAO의 insertNewArticle()메소드를 호출하여 명령한다.
	public int addReply(ArticleVO articleVO) {
		//새글 추가시 사용한 insertNewArticle메소드를 이용해 답글 또한 db에 추가 시킵니다.
		return  boardDAO.insertNewArticle(articleVO);
		 
	}	

}//BoardService 클래스 









