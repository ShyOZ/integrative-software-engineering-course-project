package iob.logic.users;

import java.util.Date;
import iob.data.UserGender;
import iob.data.UserRole;
import iob.logic.instances.UserId;

public class User {
	private UserId userId;
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
	
	
	public User() {
		
	}

	public User(UserId userId, String firstName, String lastName, String email, String password, Date birthDate,
			UserGender gender, UserGender interestedIn, String phoneNum, String address, UserRole role, String username,
			String avatar, String domain) {
		super();
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
		this.username = username;
		this.avatar = avatar;
		this.domain = domain;
	}

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public UserGender getGender() {
		return gender;
	}

	public void setGender(UserGender gender) {
		this.gender = gender;
	}

	public UserGender getInterestedIn() {
		return interestedIn;
	}

	public void setInterestedIn(UserGender interestedIn) {
		this.interestedIn = interestedIn;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", birthDate=" + birthDate + ", gender=" + gender + ", interestedIn="
				+ interestedIn + ", phoneNum=" + phoneNum + ", address=" + address + ", role=" + role + ", username="
				+ username + ", avatar=" + avatar + ", domain=" + domain + "]";
	}
}
