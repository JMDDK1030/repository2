package file;

import java.sql.Date;

public class FileVO {
	
	private int level;
	private int fileNO;
	private int parentNO;
	private String title;
	private String content;
	private String imageFileName;
	private String id;
	private Date writeDate;
	private int m_mo;
	private String m_name;
	private String dir;
	private String actor;
	private String m_gebre;
	private Date m_sdate;
	private Date m_edate;
	private char m_grade;
	private int m_rtime;
	private int m_view;
	private String m_info;
	
	
	//기본생성자 : 아무런 일도 하지 않음
	public FileVO() {}

	public FileVO(int level, int fileNO, int parentNO, String title, String content, String imageFileName,
			String id, Date writeDate) {
		
		this.level = level;
		this.fileNO = fileNO;
		this.parentNO = parentNO;
		this.title = title;
		this.content = content;
		this.imageFileName = imageFileName;
		this.id = id;
		this.writeDate = writeDate;
	}

	//getter, setter메소드
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getFileNO() {
		return fileNO;
	}

	public void setFileNO(int fileNO) {
		this.fileNO = fileNO;
	}

	public int getParentNO() {
		return parentNO;
	}

	public void setParentNO(int parentNO) {
		this.parentNO = parentNO;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public int getM_mo() {
		return m_mo;
	}

	public void setM_mo(int m_mo) {
		this.m_mo = m_mo;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getM_gebre() {
		return m_gebre;
	}

	public void setM_gebre(String m_gebre) {
		this.m_gebre = m_gebre;
	}

	public Date getM_sdate() {
		return m_sdate;
	}

	public void setM_sdate(Date m_sdate) {
		this.m_sdate = m_sdate;
	}

	public Date getM_edate() {
		return m_edate;
	}

	public void setM_edate(Date m_edate) {
		this.m_edate = m_edate;
	}

	public char getM_grade() {
		return m_grade;
	}

	public void setM_grade(char m_grade) {
		this.m_grade = m_grade;
	}

	public int getM_rtime() {
		return m_rtime;
	}

	public void setM_rtime(int m_rtime) {
		this.m_rtime = m_rtime;
	}

	public int getM_view() {
		return m_view;
	}

	public void setM_view(int m_view) {
		this.m_view = m_view;
	}

	public String getM_info() {
		return m_info;
	}

	public void setM_info(String m_info) {
		this.m_info = m_info;
	}

}
