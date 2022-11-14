package ticket;


public class TicketVO {//조회한 글을 저장하는 VO역할의 클래스
	
	private int marticleNO;
	private String movieNO;
	private String roomNO;
	private String title;
	private String id;
	
	public TicketVO() {
		
	}

	public TicketVO(int marticleNO, String movieNO, String roomNO, String title, String id) {
		this.marticleNO = marticleNO;
		this.movieNO = movieNO;
		this.roomNO = roomNO;
		this.title = title;
		this.id = id;
	}

	
	public int getMarticleNO() {
		return marticleNO;
	}

	public void setMarticleNO(int marticleNO) {
		this.marticleNO = marticleNO;
	}

	public String getMovieNO() {
		return movieNO;
	}

	public void setMovieNO(String movieNO) {
		this.movieNO = movieNO;
	}

	public String getRoomNO() {
		return roomNO;
	}

	public void setRoomNO(String roomNO) {
		this.roomNO = roomNO;
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
		

}
