package iob.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * USER Table
 * ------------
 * User ID      | First name   | Last name    | Email        | Password     | Birth date | Gender | 
 * VARCHAR(255) | VARCHAR(255) | VARCHAR(255) | VARCHAR(255) | VARCHAR(255) | DATE       | ENUM   | 
 * 
 * Interested in | Phone number | Address      |  Role | User Name | Avatar | Domain
 * ENUM          | VARCHAR(255) | VARCHAR(255) |  ENUM | String    | String | String
 * */ 

@Entity
@Table(name = "USERS_TABLE")
public class UserEntity {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Date birthDate;
	private UserGender gender;
	private UserGender interestedIn;
	private String phoneNum;
	private String address;
	private UserRole role;
	private String username;
	private String avatar;
	private String domain;
	
	public UserEntity() {
		
	}

	
	public UserEntity(String userId, String firstName, String lastName, String email, String password, Date birthDate,
			UserGender gender, UserGender interestedIn, String phoneNum, String address, UserRole role, String userName,
			String avatar, String domain) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.gender = gender;
		this.interestedIn = interestedIn;
		this.phoneNum = phoneNum;
		this.address = address;
		this.role = role;
		this.username = userName;
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

	@Column(name = "First_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name="Last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name="Email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="Password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="Birth_date")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="Gender")
	public UserGender getGender() {
		return gender;
	}

	public void setGender(UserGender gender) {
		this.gender = gender;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="Interested_in")
	public UserGender getInterestedIn() {
		return interestedIn;
	}

	public void setInterestedIn(UserGender interestedIn) {
		this.interestedIn = interestedIn;
	}

	@Column(name="Phone_number")
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Column(name="Address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
