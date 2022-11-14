package file;

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
import javax.websocket.Session;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

//사장
@WebServlet("/Movie/*")
public class FileController extends HttpServlet {

	private static String ARTICLE_IMAGE_REPO = "C:\\file\\file_image";

	FileService fileService;
	FileVO fileVO;

	@Override
	public void init() throws ServletException {

		fileService = new FileService();
		fileVO = new FileVO();
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

		HttpSession session = null;

		String nextPage = "";

		req.setCharacterEncoding("UTF-8");

		String action = req.getPathInfo();

		System.out.println(action);
		
		try {
			List<FileVO> filesList = null;

			if (action == null) { 
				filesList = fileService.listFiles();
				req.setAttribute("filesList", filesList);
				nextPage = "/MovieList/movieList.jsp";

			} else if (action.equals("/listFiles.do")) {
				filesList = fileService.listFiles();
				req.setAttribute("filesList", filesList);
				nextPage = "/MovieList/movieList.jsp";

			} else if (action.equals("/fileForm.do")) {
				nextPage = "/MovieList/fileForm.jsp";

			} else if (action.equals("/addFile.do")) {
				int fileNO = 0;

				Map<String, String> fileMap = upload(req, resp);

				String title = fileMap.get("title");
				String content = fileMap.get("content");
				String imageFileName = fileMap.get("imageFileName");

				fileVO.setParentNO(0);
				fileVO.setId("hong");
				fileVO.setTitle(title);
				fileVO.setContent(content);
				fileVO.setImageFileName(imageFileName);

				fileNO = fileService.addFile(fileVO);

				if (imageFileName != null && imageFileName.length() != 0) {
					File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + imageFileName);
					File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + fileNO);
					
					destDir.mkdirs();

					FileUtils.moveFileToDirectory(srcFile, destDir, true);

					resp.setContentType("text/html;charset=utf-8");

					PrintWriter pw = resp.getWriter();

					pw.print("<script>");
					pw.print(" alert('새 글을 추가 했습니다.');");
					pw.print(" location.href='" + req.getContextPath() + "/Movie/listFiles.do';");
					pw.print("</script>");

					return;
				}
				nextPage = "/Movie/listFiles.do";

			} else if (action.equals("/viewFile.do")) {
				String fileNO = req.getParameter("fileNO");
				fileVO = fileService.viewFile(Integer.parseInt(fileNO));
				req.setAttribute("file", fileVO);
				nextPage = "/MovieList/movieDetail.jsp";
	
			} else if (action.equals("/modFile.do")) {
				Map<String, String> fileMap = upload(req, resp);

				int fileNO = Integer.parseInt(fileMap.get("fileNO"));
				String title = fileMap.get("title");
				String content = fileMap.get("content");
				String imageFileName = fileMap.get("imageFileName");

				fileVO.setFileNO(fileNO);
				fileVO.setParentNO(0);
				fileVO.setId("hong");
				fileVO.setTitle(title);
				fileVO.setContent(content);
				fileVO.setImageFileName(imageFileName);

				fileService.modFile(fileVO);

				if (imageFileName != null && imageFileName.length() != 0) {
					String orginalFileName = fileMap.get("originalFileName");

					File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + imageFileName);
					File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + fileNO);
					destDir.mkdirs();

					FileUtils.moveFileToDirectory(srcFile, destDir, true);

					File oldFile = new File(ARTICLE_IMAGE_REPO + "\\" + fileNO + "\\" + orginalFileName);
					oldFile.delete();

				}
				resp.setContentType("text/html;charset=utf-8");

				PrintWriter pw = resp.getWriter();

				pw.print("<script>");
				pw.print(" alert('글을 수정 했습니다.');");
				pw.print(" location.href='" + req.getContextPath() + "/Movie/viewFile.do?fileNO=" + fileNO + "';");
				pw.print("</script>");

				return;

			} else if (action.equals("/removeFile.do")) {
				
				int fileNO = Integer.parseInt(req.getParameter("fileNO"));

				List<Integer> fileNOList = fileService.removeFile(fileNO);

				for (int _fileNO : fileNOList) {
					File imgDir = new File(ARTICLE_IMAGE_REPO + "\\" + _fileNO);
					
					if (imgDir.exists()) {
						FileUtils.deleteDirectory(imgDir);
					}
				}

				resp.setContentType("text/html;charset=utf-8");

				PrintWriter pw = resp.getWriter();

				pw.print("<script>");
				pw.print(" alert('글을 삭제 했습니다.');");
				pw.print(" location.href='" + req.getContextPath() + "/Movie/listFiles.do';");
				pw.print("</script>");

				return;

			} else if (action.equals("/replyForm.do")) {

				int parentNO = Integer.parseInt(req.getParameter("parentNO"));

				session = req.getSession();
				session.setAttribute("parentNO", parentNO);

				nextPage = "/MovieList/replyForm.jsp";

			} else if (action.equals("/addReply.do")) {

				int parentNO = (Integer) session.getAttribute("parentNO");
				session.removeAttribute("parentNO");

				Map<String, String> fileMap = upload(req, resp);

				String title = fileMap.get("title");
				String content = fileMap.get("content");
				String imageFileName = fileMap.get("imageFileName");

				fileVO.setParentNO(parentNO);
				fileVO.setId("hong");
				fileVO.setTitle(title);
				fileVO.setContent(content);
				fileVO.setImageFileName(imageFileName);
				
				int fileNO = fileService.addReply(fileVO);

				if (imageFileName != null && imageFileName.length() != 0) {

					File srcFile = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + imageFileName);
					File destDir = new File(ARTICLE_IMAGE_REPO + "\\" + fileNO);
					destDir.mkdirs();

					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
				resp.setContentType("text/html;charset=utf-8");

				PrintWriter pw = resp.getWriter();

				pw.print("<script>");
				pw.print(" alert('답글을 추가 했습니다.');");
				pw.print(" location.href='" + req.getContextPath() + "/Movie/viewFile.do?fileNO=" + fileNO + "';");
				pw.print("</script>");

				return;
			}
			
			else if(action.equals("/ticketList.do")) {
				
				String num = req.getParameter("m_no");
				
				List list = fileService.selectName(num);
				
				req.setAttribute("list", list);
				req.setAttribute("num", num);
				nextPage = "/MovieList/ticketList.jsp";
			}
			
			
			else {
				nextPage = "/MovieList/movieList.jsp";
			}
			RequestDispatcher dispatch = req.getRequestDispatcher(nextPage);
			dispatch.forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> fileMap = new HashMap<String, String>();

		String encoding = "UTF-8";
		
		File currentDirPath = new File(ARTICLE_IMAGE_REPO);

		DiskFileItemFactory factory = new DiskFileItemFactory();

		factory.setSizeThreshold(1024 * 1024 * 1);

		factory.setRepository(currentDirPath);

		ServletFileUpload upload = new ServletFileUpload(factory);

		try {

			List items = upload.parseRequest(request);

			for (int i = 0; i < items.size(); i++) {

				FileItem fileItem = (FileItem) items.get(i);

				if (fileItem.isFormField()) {

					System.out.println(fileItem.getFieldName() + "=" + fileItem.getString(encoding));

					fileMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
				} else {
					System.out.println("파라미터명 : " + fileItem.getFieldName());
					System.out.println("파일명 : " + fileItem.getName());
					System.out.println("파일크기 : " + fileItem.getSize() + "bytes");

					fileMap.put(fileItem.getFieldName(), fileItem.getName());

					if (fileItem.getSize() > 0) {

						int idx = fileItem.getName().lastIndexOf("\\");

						if (idx == -1) {
							idx = fileItem.getName().lastIndexOf("/");
						}

						String fileName = fileItem.getName().substring(idx + 1);

						File uploadFile = new File(currentDirPath + "\\temp\\" + fileName);

						fileItem.write(uploadFile);

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileMap;

	}

}
