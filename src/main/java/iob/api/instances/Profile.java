package iob.api.instances;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Profile implements Instance{
	private Name name;
	private LocalDate dob;
	private Gender gender;
	private Residence residence;
	private String bio;
	private MusicalTaste musicalTaste;
	private HashMap<Profile, Chat> chats;
	
	public Profile() {
		chats = new HashMap<Profile, Chat>();
	}
	
	public Profile(Name name, LocalDate dob, Gender gender, Residence residence, String bio, MusicalTaste musicalTaste) {
		this();
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.residence = residence;
		this.bio = bio;
		this.musicalTaste = musicalTaste;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Residence getResidence() {
		return residence;
	}

	public void setResidence(Residence residence) {
		this.residence = residence;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
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
		attributes.put("name", this.name);
		attributes.put("dob", this.dob);
		attributes.put("gender", this.gender);
		attributes.put("residence", this.residence);
		attributes.put("bio", this.bio);
		attributes.put("musicalTaste", this.musicalTaste);
		attributes.put("chats", this.chats);
		return attributes;
	}

	@Override
	public String toString() {
		return "Profile [name=" + name + ", dob=" + dob + ", gender=" + gender + ", residence=" + residence + ", bio="
				+ bio + ", musicalTaste=" + musicalTaste + ", chats=" + chats + "]";
	}
	
}
