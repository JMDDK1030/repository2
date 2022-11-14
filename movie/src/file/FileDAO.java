package file;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import file.FileVO;

public class FileDAO {

	private DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public FileDAO() {
		try {
			Context initCtx = new InitialContext();

			Context ctx = (Context) initCtx.lookup("java:comp/env");

			dataFactory = (DataSource) ctx.lookup("dbcp_myoracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ResourceClose() {
		try {
			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List selectAllFiles() {

		List filesList = new ArrayList();

		try {
			conn = dataFactory.getConnection();

			String query = "SELECT  FILENO, IMAGEFILENAME from moviefile";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int fileNO = rs.getInt("FILENO");
				String imageFileName = rs.getString("IMAGEFILENAME");

				FileVO file = new FileVO();
				file.setFileNO(fileNO);
				file.setImageFileName(imageFileName);

				filesList.add(file);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ResourceClose();
		}

		return filesList;

	}// selectAllFiles()

	private int getNewFileNo() {
		try {
			conn = dataFactory.getConnection();

			String query = "SELECT max(fileNO) from moviefile";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return (rs.getInt(1) + 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ResourceClose();
		}
		return 0;

	}// getNewFileNo()

	public int insertNewFile(FileVO file) {

		int fileNO = getNewFileNo();

		try {
			conn = dataFactory.getConnection();

			int parentNO = file.getParentNO();
			String title = file.getTitle();
			String content = file.getContent();
			String id = file.getId();
			String imageFilename = file.getImageFileName();

			String query = "INSERT INTO moviefile(fileNO, parentNO, title, content, imageFileName, id)"
					+ "VALUES(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, fileNO);
			pstmt.setInt(2, parentNO);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setString(5, imageFilename);
			pstmt.setString(6, id);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ResourceClose();
		}

		return fileNO;

	}// insertNewFile()

	public FileVO selectFile(int fileNO) {
		FileVO file = new FileVO();
	
		try {
			conn = dataFactory.getConnection();

			String query = "select * from movie where m_no=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, fileNO);
			rs = pstmt.executeQuery();
		
			rs.next();
			file.setM_name(rs.getString("m_name"));
			file.setDir(rs.getString("m_dir"));
			file.setActor(rs.getString("m_actor"));
			file.setM_gebre(rs.getString("m_genre"));
			file.setM_rtime(rs.getInt("m_rtime"));
			file.setM_view(rs.getInt("m_view"));
			file.setM_info(rs.getString("m_info"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ResourceClose();
		}
		return  file;
	}// selectFile()

	public void updateFile(FileVO file) {

		int fileNO = file.getFileNO();
		String title = file.getTitle();
		String content = file.getContent();
		String imageFileName = file.getImageFileName();
		try {
			conn = dataFactory.getConnection();

			String query = "update moviefile  set title=?,content=?";

			if (imageFileName != null && imageFileName.length() != 0) {
				query += ",imageFileName=?";
			}
			query += " where fileNO=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);

			if (imageFileName != null && imageFileName.length() != 0) {
				pstmt.setString(3, imageFileName);
				pstmt.setInt(4, fileNO);
			} else {
				pstmt.setInt(3, fileNO);
			}
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ResourceClose();
		}

	}// updateFile()

	public List<Integer> selectRemoveFiles(int fileNO) {

		List<Integer> fileNOList = new ArrayList<Integer>();

		try {
			conn = dataFactory.getConnection();

			String query = "SELECT fileNO FROM moviefile " + "START WITH fileNO=? "
					+ "CONNECT BY PRIOR fileNO = parentno";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, fileNO);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				fileNOList.add(rs.getInt("fileNO"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ResourceClose();
		}
		return fileNOList;
	}// selectRemoveFiles()

	public void deleteFile(int fileNO) {

		try {
			conn = dataFactory.getConnection();
			String query = "DELETE FROM moviefile " 
					+ "WHERE fileNO  in (" 
					+ "        SELECT fileNO FROM moviefile "
					+ "        START WITH fileNO=? " 
					+ "        CONNECT BY PRIOR fileNO = parentno)";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, fileNO);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ResourceClose();
		}
	}// deleteFile()

	public List selectListFile(Map<String, Object> map) {
		List<FileVO> bbs = new Vector<FileVO>();

		try {
			conn = dataFactory.getConnection();

			String query = "select * from (" + "		SELECT Tb.*, rownum rNum FROM( "
					+ "			SELECT * FROM moviefile ";

			if (map.get("searchWord") != null) {

				query += " where " + map.get("searchField") + " " + " LIKE '%" + map.get("searchWord") + "%'";

			}

			query += " ORDER by num DESC " + "	) Tb " + " ) " + " WHERE rNum BETWEEN ? and ?";

			query += " order by num desc";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, map.get("start").toString());
			pstmt.setString(2, map.get("end").toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {

				FileVO vo = new FileVO();

				int fileno = rs.getInt(1);
				int parentno = rs.getInt(2);
				String title = rs.getString(3);
				String content = rs.getString(4);
				String imagefilename = rs.getString(5);
				Date writedate = rs.getDate(6);
				String id = rs.getString(7);

				vo.setFileNO(fileno);
				vo.setParentNO(parentno);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setImageFileName(imagefilename);
				vo.setWriteDate(writedate);
				vo.setId(id);

				bbs.add(vo);

			}

		} catch (Exception e) {

			System.out.println("selectList123메소드 내부에서 오류" + e);

		} finally {
			ResourceClose();
		}

		return bbs;
	}// selectListFile()

	
	
	public List selectName(String num) {
		
		
		List list = new ArrayList();
		try {
			conn = dataFactory.getConnection();

			String query = "select  m_no,m_name from movie";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
			FileVO file = new FileVO();
			file.setM_name(rs.getString("m_name"));
			file.setM_mo(rs.getInt("m_no"));
		
			list.add(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ResourceClose();
		}
		return  list;
		
	}

		
		
}// DAO클래스
