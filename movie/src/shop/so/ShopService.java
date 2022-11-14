package shop.so;

import java.util.List;



//부장
//BoardDAO객체를 생성후 각기능별로 DB작업할 메소드를 호출하여 응답결화를 얻어 
//BoardController서블릿에게 보고하는 서블릿
public class ShopService {
	
	ShopMenuDAO shopDAO;

	//생성자
	public ShopService() {
		shopDAO = new ShopMenuDAO(); //BoardService생성자 호출시 BoardDAO객체를 생성
	}

	
	public List<ShopMenuVO> listMenu(){
		
		//전체글 검색해서 가져옴
		//가져온 검색결과를 Arraylist배열을 BoadrControll에 보고
		return shopDAO.selectMenu();
	}

	
	
	
}
