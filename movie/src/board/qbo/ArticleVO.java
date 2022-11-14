package board.qbo;

import java.sql.Date;

public class ArticleVO {//조회한 글을 저장하는 VO역할의 클래스
	
	private int level;
	private int articleNO;
	private int parentNO;
	private String title;
	private String id;
	private Date writeDate;
	
	public ArticleVO() {
		
	}

	
	
	public ArticleVO(int level, int articleNO, int parentNO,String title,
			String id, Date writeDate) {
		this.level = level;
		this.articleNO = articleNO;
		this.parentNO = parentNO;
		this.title = title;
		this.id = id;
		this.writeDate = writeDate;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getArticleNO() {
		return articleNO;
	}

	public void setArticleNO(int articleNO) {
		this.articleNO = articleNO;
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

}
