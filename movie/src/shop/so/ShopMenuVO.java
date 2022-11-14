package shop.so;


public class ShopMenuVO {
	private int mcno;
	private String menuname;
	private String mcontent;
	private String menuimg;
	
	
	public ShopMenuVO() {

	}
	
	
	public ShopMenuVO(int mcno, String menuname, String mcontent, String menuimg) {
		this.mcno = mcno;
		this.menuname = menuname;
		this.mcontent = mcontent;
		this.menuimg = menuimg;
	}
	public int getMcno() {
		return mcno;
	}
	public void setMcno(int mcno) {
		this.mcno = mcno;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getMcontent() {
		return mcontent;
	}
	public void setMcontent(String mcontent) {
		this.mcontent = mcontent;
	}
	public String getMenuimg() {
		return menuimg;
	}
	public void setMenuimg(String menuimg) {
		this.menuimg = menuimg;
	}

	
}
