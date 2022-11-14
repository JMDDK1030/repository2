package shop.so;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem; 
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import board.qbo.ArticleVO;
import board.qbo.BoardService;


@WebServlet("/shop/*") 
public class ShopController extends HttpServlet  {


	ShopService shopService;
	ShopMenuVO shopVo;
		
	//BoardController서블릿 클래스를 톰캣 메모리에 로드 하는 시점에 
	//호출되는 init메소드 내부에 BoardService클래스의 객체 생성후 저장 하자 
	@Override
	public void init() throws ServletException {

		shopService = new ShopService();
		shopVo = new ShopMenuVO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandle(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandle(req, resp);
	}
	
	protected void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		

		
		//답글에 대한 부모글번호를 저장하기 위해 sesssion내장 객체를 저장시킬 변수 선언
		HttpSession session = null;		
		
		//재요청할 페이지 주소를 저장할 변수
		String nextPage = "";
		
		req.setCharacterEncoding("UTF-8");
		
		//요청명을 가져옵니다.
		String action = req.getPathInfo(); //    /articleForm.qo
										   //    /addArticle.qo
											//   /viewArticle.qo
											//   /modArticle.qo
										
		System.out.println(action);

		try {	
			List<ShopMenuVO> menuList = new ArrayList<ShopMenuVO>();
			
			if(action == null  ) { //요청명이 null일때
				
				//boardservice 부장객체이 listArticles()메소드를 호출하여
				//db에 저장된 모든 글목록을 조회하여 담은 배열을 반환
				menuList = shopService.listMenu();
				//articlesList배열에 저장
				req.setAttribute("menuList", menuList);
				//재요청할 뷰 주소저장
				nextPage = "/shopmenu/shopHome.jsp";
			}else if(action.equals("/menuList.so")) {		
				//boardservice 부장객체이 listArticles()메소드를 호출하여
				//db에 저장된 모든 글목록을 조회하여 담은 배열을 반환
				menuList = shopService.listMenu();
				//articlesList배열에 저장
				req.setAttribute("menuList", menuList);
				//재요청할 뷰 주소저장
				nextPage = "/shopmenu/shopHome.jsp";			
				
			}else {
				nextPage = "/shopmenu/shopHome.jsp";
			}
			
			RequestDispatcher dispatch = req.getRequestDispatcher(nextPage);
							  dispatch.forward(req, resp);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}				
				
				
	}//doHandle메소드 끝	
	
	
}//BoardController서블릿 클래스 닫는 기호 











