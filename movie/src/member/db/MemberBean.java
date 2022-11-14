package member.db;

import java.sql.Date;

public class MemberBean {
	
	private String id;
	private String pw;
	private String mail;
	private String birth;
	private String name;
	private String gender;
	private String phone;
	private Date regdate;
	private String address1;
	private String address2;
	private String address3;
	
	public MemberBean() { }
	
	public MemberBean(String id, String pw, String mail, String name, String gender, String birth, String phone, String address1, String address2,String address3) {
		super();
		this.id = id;
		this.pw = pw;
		this.mail = mail;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.phone = phone;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		
	}


	public MemberBean(String id, String pw, String mail,  String birth, String name, String gender, String phone,
			Date regdate, String address1, String address2,String address3) {
		super();
		this.id = id;
		this.pw = pw;
		this.mail = mail;
		this.birth = birth;
		this.name = name;
		this.gender = gender;
		this.phone = phone;
		this.regdate = regdate;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		
	}

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}


	
	

	
	
}
