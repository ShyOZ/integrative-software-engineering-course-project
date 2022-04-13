package iob.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


/*
 * USER Table
 * ------------
 * User ID      | Role | User Name | Avatar | Domain
 * VARCHAR(255) | ENUM | String    | String | String
 * 
 * */ 

@Entity
@Table(name = "USERS_TABLE")
public class UserEntity {
	private String userId;
	private UserRole role;
	private String username;
	private String avatar;
	private String domain;
	
	public UserEntity() {
		
	}	
	
	public UserEntity(String userId, UserRole role, String username, String avatar, String domain) {
		this.userId = userId;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
		this.domain = domain;
	}

	@Id
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="User_Role")
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Column(name="User_Name")
	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	@Column(name="Avatar")
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Column(name="Domain")
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
}
