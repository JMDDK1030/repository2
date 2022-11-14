package ticket;

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

import file.FileVO;




//사장
@WebServlet("/ticket/*")  
public class TicketController extends HttpServlet {
	
	private static String ARTICLE_IMAGE_REPO = "C:\\board\\article_image";

	TicketService boardService;
	FileVO articleVO;
	TicketVO tickeVO;
	RoomVO roomVO;

		
	//BoardController서블릿 클래스를 톰캣 메모리에 로드 하는 시점에 
	//호출되는 init메소드 내부에 BoardService클래스의 객체 생성후 저장 하자 
	@Override
	public void init() throws ServletException {

		boardService = new TicketService();
		articleVO = new FileVO();
		tickeVO = new TicketVO();
		roomVO = new RoomVO();
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
			List<TicketVO> menuList = new ArrayList<TicketVO>();
			List<FileVO> fileList = new ArrayList<FileVO>();
			List<RoomVO> roomList = new ArrayList<RoomVO>();
			List<RoomEXVO> roomexList = new ArrayList<RoomEXVO>();

			
			if(action == null  ) { //요청명이 null일때
				
				//boardservice 부장객체이 listArticles()메소드를 호출하여
				//db에 저장된 모든 글목록을 조회하여 담은 배열을 반환
				fileList = boardService.fileList();
				//articlesList배열에 저장
				req.setAttribute("menuList", menuList);
				//재요청할 뷰 주소저장
				nextPage = "/MovieList/ticketList.jsp";

			} else if (action.equals("/ticketlist.to")) {			
				
				//boardservice 부장객체이 listArticles()메소드를 호출하여
				//db에 저장된 모든 글목록을 조회하여 담은 배열을 반환
				fileList = boardService.fileList();
				//articlesList배열에 저장
				req.setAttribute("fileList", fileList);
				
				roomList = boardService.roomlistMenu();
				//articlesList배열에 저장
				req.setAttribute("roomList", roomList);
											
				//재요청할 뷰 주소저장
				nextPage = "/MovieList/ticketList.jsp";
				
			//좌석선택페이지 이동
			} else if (action.equals("/tixketinfo.to")) {			
				
				roomexList = boardService.roomexList();
				//articlesList배열에 저장
				req.setAttribute("roomexList", roomexList);				
				

				nextPage = "/MovieList/tixketInfo.jsp";
		
			} else if (action.equals("/myticket.to")) {			
				
				//boardservice 부장객체이 listArticles()메소드를 호출하여
				//db에 저장된 모든 글목록을 조회하여 담은 배열을 반환
				menuList = boardService.mylistMenu();
				//articlesList배열에 저장
				req.setAttribute("menuList", menuList);
				//재요청할 뷰 주소저장
				nextPage = "/MovieList/myticketList.jsp";
				
			//요청명이  /addArticle.do 이면 (DB에 새글 추가 요청 했다면)
			}else if(action.equals("/ticketre.to")) {				
				
				//추가한 새글 번호를 반환받아 저장할 변수
				//반환 받는 이유는? 글번호 폴더를 생성하기 위함입니다.
				int articleNO = 0;
			
				Map<String, String>	articleMap = upload(req,resp);
				
				//HashMap에 저장된 글정보(업로드한 파일명, 입력한 글제목, 입력한 글내용)을 다시 꺼내옵니다.
				String movieNO =articleMap.get("movieNO");
				String roomNO =articleMap.get("roomNO");			
				String id = articleMap.get("id");
				String title = articleMap.get("title");
	
				
				//DB에 추가하기 위해  사용자가 입력한 글정보+업로드할 파일명을 ArticleVO객체의 각변수에 저장
				tickeVO.setMovieNO(movieNO);
				tickeVO.setRoomNO(roomNO);
				tickeVO.setId(id);//추가할 새글 작성자 ID를 hong으로 저장
				tickeVO.setTitle(title);//추가하기 위해 입력한 글제목 저장

				
				//웹브라우저로 응답할 데이터종류(MIME-TYPE)설정 , 한글처리 설정
				resp.setContentType("text/html;charset=utf-8");
				
				//클라이언트의 웹브라우저로 응답할 출력 스트림 통로 객체 생성
				PrintWriter pw = resp.getWriter();
				
				pw.print("<script>");
				pw.print(" alert('예매 성공하였습니다.');");
				pw.print(" location.href='" + req.getContextPath() + "/board/qboard.qo';");
				pw.print("</script>");			
			
				articleNO = boardService.addArticle(tickeVO);
		//DB에 새글을 추가하고 컨트롤러에서 /board02/listArticles.jsp로 이동하여
				//전체글을 DB에서 검색하여 보여주기 위해 다음과 같은 요청할 주소를 지정합니다.
				nextPage = "/MovieList/ticketList.jsp";

					
			}else if(action.equals("/removeteckit.to")) {	
				
				//설명
				//삭제 요청을 하면 글번호에 대한 글과 그자식글을 삭제 하기전에 먼저 삭제할 글번호와 자식 글번호를
				//DB목록으로 가져옵니다. 그리고 글을 삭제한후 글번호로 이루어진 이미지 저장 폴더까지 모두 삭제 시킵니다.
		
				//삭제할 글번호를 request에서 얻기
				int articleNO = Integer.parseInt(req.getParameter("marticleNO"));
				
				//삭제한 글번호에 해당하는 글을 삭제후 삭제된 부모글과 자식글의 글번호를 조회해서 목록으로 가져옵니다.
				List<Integer> articleNOList = boardService.removeArticle(articleNO);
				
				//웹브라우저로 응답할 데이터종류(MIME-TYPE)설정 , 한글처리 설정
				resp.setContentType("text/html;charset=utf-8");
				
				//클라이언트의 웹브라우저로 응답할 출력 스트림 통로 객체 생성
				PrintWriter pw = resp.getWriter();
				
				pw.print("<script>");
				pw.print(" alert('예매를 삭제 했습니다.');");
				pw.print(" location.href='" + req.getContextPath() + "/board/qboard.qo';");
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


