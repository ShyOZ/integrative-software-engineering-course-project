package iob.logic.instances;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import iob.data.UserGender;
import iob.logic.users.UserId;


public class Profile implements Instance {
	private UserId id;
	private String firstName;
	private String lastName;
	private String email;
	private Date birthDate;
	private UserGender gender;
	private UserGender interestedIn;
	private String phoneNum;
	private String address;
	private MusicalTaste musicalTaste;
	private HashMap<Profile, Chat> chats;

	public Profile() {
		chats = new HashMap<Profile, Chat>();
	}


	public String getFirstName() {
		return firstName;
	}



	public UserId getId() {
		return id;
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



	public MusicalTaste getMusicalTaste() {
		return musicalTaste;
	}



	public void setMusicalTaste(MusicalTaste musicalTaste) {
		this.musicalTaste = musicalTaste;
	}



	public HashMap<Profile, Chat> getChats() {
		return chats;
	}



	public void setChats(HashMap<Profile, Chat> chats) {
		this.chats = chats;
	}



	@Override
	public Map<String, Object> getInstanceAttributes() {
		Map<String, Object> attributes = new TreeMap<String, Object>();
		attributes.put("first name", this.firstName);
		attributes.put("last name", this.lastName);
		attributes.put("email", this.email);
		attributes.put("birthday", this.birthDate);
		attributes.put("gender", this.gender);
		attributes.put("intrested in", this.interestedIn);
		attributes.put("phone number", this.phoneNum);
		attributes.put("adress", this.address);
		attributes.put("musical taste", this.musicalTaste);
		attributes.put("chats", this.chats);
		return attributes;
	}



	@Override
	public String toString() {
		return "Profile [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", birthDate=" + birthDate + ", gender=" + gender + ", interestedIn="
				+ interestedIn + ", phoneNum=" + phoneNum + ", address=" + address + ", musicalTaste=" + musicalTaste
				+ ", chats=" + chats + "]";
	}

	

}
