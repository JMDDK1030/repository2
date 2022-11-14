package board.qbo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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




//사장
@WebServlet("/board/*")  
public class BoardController extends HttpServlet {
	
	private static String ARTICLE_IMAGE_REPO = "C:\\board\\article_image";

	BoardService boardService;
	ArticleVO articleVO;
		
	//BoardController서블릿 클래스를 톰캣 메모리에 로드 하는 시점에 
	//호출되는 init메소드 내부에 BoardService클래스의 객체 생성후 저장 하자 
	@Override
	public void init() throws ServletException {

		boardService = new BoardService();
		articleVO = new ArticleVO();
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
			List<ArticleVO> articlesList = null;
			
			if(action == null ) { 	
				
				//section값과 pageNum값을 구합니다.
				String _section = req.getParameter("section");
				String _pageNum = req.getParameter("pageNum");
				
				//최초 요청시 section과 pageNum의 기본값을 1로 초기화 합니다.
				int section  = Integer.parseInt(((_section == null) ? "1" : _section));     
				int pageNum  = Integer.parseInt(((_pageNum == null) ? "1" : _pageNum));     
				
				//위 section변수,pageNum변수에 저장된 값을 HashMap에 담자
				Map<String,Integer> pagingMap = new HashMap<String,Integer>();
									pagingMap.put("section", section);
									pagingMap.put("pageNum", pageNum);
				
				//section값과 pageNum값으로 해당 섹션과 페이지에 해당되는 글목록을 조회 하기 위해 
				//BoardService부장의 listArticles메소드 호출시 위 생성한 HashMap배열을 매개변수로 전달 함.
				Map  articlesMap = boardService.listArticles(pagingMap);
					 articlesMap.put("section", section);
					 articlesMap.put("pageNum", pageNum);
					
				//request내장객체에 조회된 정보가 있는 HashMap배열을 저장 
				req.setAttribute("articlesMap", articlesMap);
				
				//재요청할 뷰 주소 저장
				nextPage = "/qboard/boardHome.jsp";

			} else if (action.equals("/qboard.qo")) {			
				
				//최초 요청시 또는 /listArticles.do로 요청시
				//section값과 pageNum값을 구합니다.
				String _section = req.getParameter("section");
				String _pageNum = req.getParameter("pageNum");
				
				//최초 요청시 section과 pageNum의 기본값을 1로 초기화 합니다.
				int section  = Integer.parseInt(((_section == null) ? "1" : _section));     
				int pageNum  = Integer.parseInt(((_pageNum == null) ? "1" : _pageNum));     
				
				//위 section변수,pageNum변수에 저장된 값을 HashMap에 담자
				Map<String,Integer> pagingMap = new HashMap<String,Integer>();
									pagingMap.put("section", section);
									pagingMap.put("pageNum", pageNum);
				
				//section값과 pageNum값으로 해당 섹션과 페이지에 해당되는 글목록을 조회 하기 위해 
				//BoardService부장의 listArticles메소드 호출시 위 생성한 HashMap배열을 매개변수로 전달 함.
				Map  articlesMap = boardService.listArticles(pagingMap);
					 articlesMap.put("section", section);
					 articlesMap.put("pageNum", pageNum);
					
				//request내장객체에 조회된 정보가 있는 HashMap배열을 저장 
				req.setAttribute("articlesMap", articlesMap);
				
				//재요청할 뷰 주소 저장
				nextPage = "/qboard/boardHome.jsp";
									
			
			//요청명이 /articleForm.do이면(글쓰기 화면을 요청했다면)	
			}else if(action.equals("/articleForm.qo")) {
				
				//새 글을 작성할수 있는 화면을 재요청하기 위해 주소 저장
				nextPage = "/qboard/articleForm.jsp";				
				
			}else if(action.equals("/qnaList.qo")) {
				
				//새 글을 작성할수 있는 화면을 재요청하기 위해 주소 저장
				nextPage = "/qboard/boardQnaList.jsp";					
				
				
			//요청명이  /addArticle.do 이면 (DB에 새글 추가 요청 했다면)
			}else if(action.equals("/addArticle.qo")) {
				
				
				//추가한 새글 번호를 반환받아 저장할 변수
				//반환 받는 이유는? 글번호 폴더를 생성하기 위함입니다.
				int articleNO = 0;

				
				Map<String, String>	articleMap = upload(req,resp);
				
				//HashMap에 저장된 글정보(업로드한 파일명, 입력한 글제목, 입력한 글내용)을 다시 꺼내옵니다.
				String id = articleMap.get("id");
				String title = articleMap.get("title");
	
				
				//DB에 추가하기 위해  사용자가 입력한 글정보+업로드할 파일명을 ArticleVO객체의 각변수에 저장
				articleVO.setParentNO(0);//추가할 새글의 부모 글번호를 0으로 저장
				articleVO.setId(id);//추가할 새글 작성자 ID를 hong으로 저장
				articleVO.setTitle(title);//추가하기 위해 입력한 글제목 저장

				
				//웹브라우저로 응답할 데이터종류(MIME-TYPE)설정 , 한글처리 설정
				resp.setContentType("text/html;charset=utf-8");
				
				//클라이언트의 웹브라우저로 응답할 출력 스트림 통로 객체 생성
				PrintWriter pw = resp.getWriter();
				
				pw.print("<script>");
				pw.print(" alert('새 글을 추가 했습니다.');");
				pw.print(" location.href='" + req.getContextPath() + "/board/qboard.qo';");
				pw.print("</script>");			
			
				articleNO = boardService.addArticle(articleVO);
		//DB에 새글을 추가하고 컨트롤러에서 /board02/listArticles.jsp로 이동하여
				//전체글을 DB에서 검색하여 보여주기 위해 다음과 같은 요청할 주소를 지정합니다.
				nextPage = "/board/qboard.qo";

			}else if(action.equals("/viewArticle.qo")) {
				
				//요청한 값 얻기(조회할 글 번호 얻기)
				String articleNO = req.getParameter("articleNO");
				
				//글번호를 이용하여 조회 한 글 하나의 정보를 ArticleVO객체에 저장후 반환 받기 
				articleVO = boardService.viewArticle(Integer.parseInt(articleNO));
				
				//조회된 글의 정보를 VIEW페이지(viewArticle.jsp)에 출력해서 보여주기 위해
				//request내장객체에 조회해서 반환받은 ArticleVO객체를 저장(바인딩) 시키자
				req.setAttribute("article", articleVO);
				
				//재요청할 VIEW주소 저장
				nextPage = "/qboard/viewArticle.jsp";									
				
			//수정 요청이 들어 왔을떄..	
			}else if(action.equals("/modArticle.qo")) {

				Map<String, String>	articleMap = upload(req,resp);
				
				//수정할 글번호를 얻는다.
				int articleNO = Integer.parseInt(articleMap.get("articleNO"));
				String id = articleMap.get("id");
				String title = articleMap.get("title");


				//ArticleVO객체에 위에 작성한 수정할 값들을 변수에 저장시키자
				articleVO.setArticleNO(articleNO);
				articleVO.setParentNO(0);
				articleVO.setId(id);
				articleVO.setTitle(title);
				
				//DB의 t_board테이블에 수정할 정보를 UPDATE하기 위해 
				//BoardService의 modArticle메소드 호출시  ArticleVO객체를 매개변수로 전달!
				boardService.modArticle(articleVO);
											
				//웹브라우저로 응답할 데이터종류(MIME-TYPE)설정 , 한글처리 설정
				resp.setContentType("text/html;charset=utf-8");
				
				//클라이언트의 웹브라우저로 응답할 출력 스트림 통로 객체 생성
				PrintWriter pw = resp.getWriter();
				
				
				pw.print("<script>");
				pw.print(" alert('글을 수정 했습니다.');");
				pw.print(" location.href='" + req.getContextPath() + "/board/viewArticle.qo?articleNO="+ articleNO +"';");
				pw.print("</script>");			
				
				return; 
					
			}else if(action.equals("/removeArticle.qo")) {	
				
				//설명
				//삭제 요청을 하면 글번호에 대한 글과 그자식글을 삭제 하기전에 먼저 삭제할 글번호와 자식 글번호를
				//DB목록으로 가져옵니다. 그리고 글을 삭제한후 글번호로 이루어진 이미지 저장 폴더까지 모두 삭제 시킵니다.
		
				//삭제할 글번호를 request에서 얻기
				int articleNO = Integer.parseInt(req.getParameter("articleNO"));
				
				//삭제한 글번호에 해당하는 글을 삭제후 삭제된 부모글과 자식글의 글번호를 조회해서 목록으로 가져옵니다.
				List<Integer> articleNOList = boardService.removeArticle(articleNO);
				
				//웹브라우저로 응답할 데이터종류(MIME-TYPE)설정 , 한글처리 설정
				resp.setContentType("text/html;charset=utf-8");
				
				//클라이언트의 웹브라우저로 응답할 출력 스트림 통로 객체 생성
				PrintWriter pw = resp.getWriter();
				
				pw.print("<script>");
				pw.print(" alert('글을 삭제 했습니다.');");
				pw.print(" location.href='" + req.getContextPath() + "/board/qboard.qo';");
				pw.print("</script>");			
				
				return;
				
			}else if(action.equals("/replyForm.qo")) { //답글요청
				//설명
				//답글창 요청시 미리 부모글번호를 parentNO속성으로 세션영역에 저장해 놓고
				//답글을 작성한 후에 등록을 요청을 하면 (/addreplt.do)세션에서 parentNO부모글번호를가져와
				//t_board테이블에 답글의 정보에 같이 추가 시킵니다.
				int parentNO = Integer.parseInt(req.getParameter("parentNO"));
				
				//session내장객체 메모리 생성
				//방법 request객체의 getsession()메소드를 호출하면 클라이언트정보와 연결된
				// session내장객체를 생성해서 얻을수 있습니다.
				session = req.getSession();
				session.setAttribute("parentNO", parentNO); //세션에 저장
				
				//답글 장성창 화면 (view)주소 지정
				nextPage = "/qboard/replyForm.jsp";		
			
			}else if(action.equals("/addReply.qo")) { //답글쓰기 요청시
				
				//답글 전송시 세션에 저장된 부모글 글번호를 꺼내옵니다.
				session = req.getSession(true); //세션영역을 얻는다
				int parentNO = (Integer)session.getAttribute("parentNO");
				
				session.removeAttribute("parentNO"); //세션에 저장된 부모글번호 삭제
				
				Map<String, String>	articleMap = upload(req,resp);

				//HashMap에 저장된 글정보(업로드한 파일명, 입력한 글제목, 입력한 글내용)을 다시 꺼내옵니다.
				String id = articleMap.get("id");

				String title = articleMap.get("title");			
				
				//DB에 추가하기 위해  사용자가 입력한 글정보+업로드할 파일명을 ArticleVO객체의 각변수에 저장
				articleVO.setParentNO(parentNO);//추가할 새글의 부모 글번호를 parentNO으로 저장
				articleVO.setId(id);//추가할 새글 작성자 ID를 hong으로 저장
				articleVO.setTitle(title);//추가하기 위해 입력한 답글제목 저장

				
				//db에 추가하기 위해 부장의 addreply메소드 호출
				//그리고 insert에 성공하면 글번호 폴더를 만들기 위해 글번호 리턴받습니다
				int articleNO = boardService.addReply(articleVO);
				
				//웹브라우저로 응답할 데이터종류(MIME-TYPE)설정 , 한글처리 설정
				resp.setContentType("text/html;charset=utf-8");
				
				//클라이언트의 웹브라우저로 응답할 출력 스트림 통로 객체 생성
				PrintWriter pw = resp.getWriter();
				
				pw.print("<script>");
				pw.print(" alert('답글을 추가 했습니다.');");
				pw.print(" location.href='" + req.getContextPath() + "/board/viewArticle.qo?articleNO="+ articleNO +"';");
				pw.print("</script>");			
				
				return; 			

				
			}else {
				
				nextPage = "/qboard/boardHome.jsp";
			}
			//뷰 또는 컨트롤러 재요청
			RequestDispatcher dispatch = req.getRequestDispatcher(nextPage);
							  dispatch.forward(req, resp);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}//doHandle메소드 끝	

	//파일 업로드 처리를 위한 메소드 
	private Map<String, String> upload(HttpServletRequest request, 
			                           HttpServletResponse response)
			                        		   throws ServletException,IOException{
		Map<String, String> articleMap = new HashMap<String, String>();
		
		String encoding="UTF-8";
		
		//글쓰기를 할때 첨부한 이미지를 저장할 폴더경로에 접근하기 위해 File객체를 생성합니다.
		File currentDirPath = new File(ARTICLE_IMAGE_REPO);
		
		//업로드할 파일 데이터를 임시로 저장시킬 저장소역할의 객체 메모리 생성
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//파일업로드시 사용할 임시메모리 최대 크기 1메가 바이트로 지정
		factory.setSizeThreshold(1024*1024*1);
		//임시메모리에 파일업로드시~ 지정한 1메가바이트크기를 넘길경우 업로드될 파일 경로를 지정함
		factory.setRepository(currentDirPath);
		
		//참고
		//DiskFileItemFactory클래스는 업로드 파일의 크기가 지정한 크기를 넘기 전까지는
		//업로드한 파일 데이터를 임시 메모리에 저장하고 지정한 크기를 넘길 경우 디렉터리에 파일로 저장한다.
		
		//파일업로드할 메모리를 생성자쪽으로 전달받아 저장한!! 파일업로드를 처리할 객체 생성
		ServletFileUpload  upload = new ServletFileUpload(factory);
		
		try {
			//업로드할 파일에 대한 요청 정보를 가지고 있는 request객체를 parseRequest()메소드 호출시 인자로 전달면
			//request객체 저장되어 있는 업로드할 파일의 정보를 파싱해서 DiskFileItem객체에 저장후
			//DiskFileItem객체를  ArrayList에 추가합니다. 그후 ArrayList를 반환 받습니다.
			List items = upload.parseRequest(request);
			
			for(int i=0; i<items.size(); i++) {
				
				//ArrayList가변 배열에서 DiskFileItem객체(업로드할 아이템하나의 정보를 말함)를 얻는다.
				FileItem fileItem = (FileItem)items.get(i);
				
				//DiskFileItem객체(업로드할 아이템하나의 정보)가 파일 아이템이 아닐경우 
				if(fileItem.isFormField()) {
					
					System.out.println(fileItem.getFieldName() + "=" + fileItem.getString(encoding));
					
					//articleForm.jsp페이지에서 입력한 글제목,내용만 따로 HashMap에 (key=value)형식으로 저장합니다.
					//HashMap에 저장된 데이터의 예 =>  {title=입력한글제목, content=입력한글내용}
					articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
					
				//DiskFileItem객체(업로드할 아이템하나의 정보)가 파일 아이템일 경우 업로드 진행!!!!	
				}else {
					
					System.out.println("파라미터명 : " + fileItem.getFieldName());
					System.out.println("파일명 : " + fileItem.getName());
					System.out.println("파일크기 : " + fileItem.getSize() + "bytes");
					
					//articleForm.jsp페이지에서 입력한 글제목, 글내용, 요청한 업로드할 파일등의 모든 정보를?
					//HashMap에 (key=value)형식으로 저장합니다.
					//HashMap에 저장된 데이터의 예 => {imageFileName=3.png , title=글제목, content=글내용}
					articleMap.put(fileItem.getFieldName(), fileItem.getName());
					
					//전체 : 업로드할 파일이 존재하는 경우 업로드할 파일의 파일이름으로 저장소에 업로드합니다.
					//파일크기가 0보다 크다면?(업로드할 파일이 있다면)
					if(fileItem.getSize() > 0) {
						//업로드할 파일명을 얻어 파일명의 뒤에서 부터 \\문자열이 들어 있는지 
						//인덱스 위치를 알려주는데.. 없으면 -1을 반환함
						int idx = fileItem.getName().lastIndexOf("\\");//뒤에서 부터 문자가 들어 있는 인덱스위치를 알려준다
						
						if(idx == -1) {
							idx = fileItem.getName().lastIndexOf("/"); // -1얻기 
						}
						
						//업로드할 파일명 얻기
						String fileName = fileItem.getName().substring(idx + 1);
						//업로드할 파일 경로 + 파일명의 주소(업로드할 경로)에 접근하기 위해 File객체 생성
						//-> 첨부한 파일을 먼저 temp폴더에 업로드 합니다.
						File uploadFile = new File(currentDirPath + "\\temp\\" + fileName);
						//실제 파일업로드 하기 		
						fileItem.write(uploadFile);		
								
					}//end if
				}//end if
			}//end for

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return articleMap; //doHandle메소드로 리턴
		
	}//upload메소드 닫는 기호 
	
	
	
	
	
}//BoardController서블릿 클래스 닫는 기호 











