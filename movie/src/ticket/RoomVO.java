package ticket;


public class RoomVO {//조회한 글을 저장하는 VO역할의 클래스
	private int roomNO;
	private String roomname;
	private String r_name;

	
	public RoomVO() {
		
	}

	public String getR_name() {
		return r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public RoomVO(int roomNO, String roomname) {
		this.roomNO = roomNO;
		this.roomname = roomname;
	}


	public int getRoomNO() {
		return roomNO;
	}


	public void setRoomNO(int roomNO) {
		this.roomNO = roomNO;
	}

	public String getRoomname() {
		return roomname;
	}


	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	
}

