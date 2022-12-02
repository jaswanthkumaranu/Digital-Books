package com.rc.model;

public class UserVo {

	private Long userId;
	private String userName;
	private String emailId;
	private String password;
	private Long phoneNumber;
	private Long roleIdFk;
	private Long isActive;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getRoleIdFk() {
		return roleIdFk;
	}

	public void setRoleIdFk(Long roleIdFk) {
		this.roleIdFk = roleIdFk;
	}

	public Long getIsActive() {
		return isActive;
	}

	public void setIsActive(Long isActive) {
		this.isActive = isActive;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserVo() {
		super();
	}

	public UserVo(String userName) {
		super();
		this.userName = userName;
	}

	public UserVo(String userName, String emailId, String password, Long phoneNumber, Long roleIdFk, Long isActive) {
		super();
		this.userName = userName;
		this.emailId = emailId;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.roleIdFk = roleIdFk;
		this.isActive = isActive;
	}

	public UserVo(Long userId, String userName, String emailId, String password, Long phoneNumber, Long roleIdFk,
			Long isActive) {
		this(userName, emailId, password, phoneNumber, roleIdFk, isActive);
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + "]";
	}

}
