package com.dev.request;

public class UserRegister {

	private String userName;
	private String userEmail;
	private String password;

	public UserRegister() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserRegister(String userName, String userEmail, String password) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserRegister [userName=" + userName + ", userEmail=" + userEmail + ", password=" + password + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
