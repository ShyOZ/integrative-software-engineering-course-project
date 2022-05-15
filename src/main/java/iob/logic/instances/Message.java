package iob.logic.instances;

import java.util.Map;
import java.util.TreeMap;

import iob.logic.users.User;

public class Message implements Instance {
	private User sender;
	private User reciver;
	private String text;
	private boolean isRead;

	public Message() {

	}

	public Message(User sender, User reciver, String text, boolean isRead) {
		this.sender = sender;
		this.reciver = reciver;
		this.text = text;
		this.isRead = isRead;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReciver() {
		return reciver;
	}

	public void setReciver(User reciver) {
		this.reciver = reciver;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Message [sender=" + sender + ", reciver=" + reciver + ", text=" + text + "]";
	}

	@Override
	public Map<String, Object> getInstanceAttributes() {
		Map<String, Object> attributes = new TreeMap<String, Object>();
		attributes.put("sender", this.sender);
		attributes.put("reciever", this.reciver);
		attributes.put("text", this.text);
		return attributes;
	}
}
