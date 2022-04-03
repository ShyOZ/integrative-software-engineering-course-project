package iob.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * USER Table
 * ------------
 * User ID      | First name   | Last name    | Email        | Password     | Birth date | Gender | 
 * VARCHAR(255) | VARCHAR(255) | VARCHAR(255) | VARCHAR(255) | VARCHAR(255) | DATE       | ENUM   | 
 * 
 * Interested in | Phone number | Address      |
 * ENUM          | VARCHAR(255) | VARCHAR(255) |
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
	
	public UserEntity() {
		
	}

	public UserEntity(String userId, String firstName, String lastName, String email, String password, Date birthDate,
			UserGender gender, UserGender interestedIn, String phoneNum, String address) {
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

	@Column(name="Birth_date")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name="Gender")
	public UserGender getGender() {
		return gender;
	}

	public void setGender(UserGender gender) {
		this.gender = gender;
	}

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

	@Id
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
