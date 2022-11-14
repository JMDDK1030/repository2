package ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import file.FileVO;




//부장
//BoardDAO객체를 생성한후 각 기능별로 DB작업할 메소드를 호출하여 응답할 결과를 얻어
//BoardController서블릿에게 보고 하는 클래스 
public class TicketService {

	TicketDAO ticketDAO;
	
	//기본생성자
	public TicketService() {
		ticketDAO = new TicketDAO(); //BoardService생성자 호출시 BoardDAO객체를 생성합니다.
	}
	
	public List<FileVO> fileList(){
		
		//전체글 검색해서 가져옴
		//가져온 검색결과를 Arraylist배열을 BoadrControll에 보고
		return ticketDAO.selectMenu();
	}
	//2. BoardController서블릿에서 호출하는 메소드로서!!
	//   글쓰기 창에서 입력된 정보를 ArticleVO객체에 설정한후 매개변수로 전달 받아서
	//  다시~ BoardDAO객체의 insertNewArticle()메소드를 호출하면서 추가할 새글정보(ArticleVO객체)를
	//  매개변수로 전달하여 DB에 INSERT명령하는 메소드 
	public int addArticle(TicketVO articleVO) {
		
		 return  ticketDAO.insertNewArticle(articleVO);
	}

	
	//글번호를 매개변수로 받아서 받은 글번호의 글과 답변자식글 까지 모두 삭제한 후 부모글과 자식글의 글번호를 조회해서 반환시키는 메소드
	public List<Integer> removeArticle(int articleNO) {

		//삭제 글번호를 articleNOList에 담아 반환한다
		List<Integer> articleNOList = ticketDAO.selectRemoveArticles(articleNO);
		
		//삭제 메소드 호출
		ticketDAO.deleteArticle(articleNO);
		
		return articleNOList; //컨트롤러로 반환
	}

	//예약정보 불러오기
	public List<TicketVO> mylistMenu() {

		return ticketDAO.selectmyMenu();
	}

	public List<RoomVO> roomlistMenu() {

		return ticketDAO.selectroomMenu();
	}

	public List<RoomEXVO> roomexList() {

		return ticketDAO.selectroomEXMenu();
	}



}//BoardService 클래스 









