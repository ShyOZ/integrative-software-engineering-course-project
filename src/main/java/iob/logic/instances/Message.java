package iob.logic.instances;

import java.util.Map;
import java.util.TreeMap;

public class Message implements Instance {
	private Profile sender;
	private Profile reciver;
	private String text;

	public Message() {

	}

	public Message(Profile sender, Profile reciver, String text) {
		this.sender = sender;
		this.reciver = reciver;
		this.text = text;
	}

	public Profile getSender() {
		return sender;
	}

	public void setSender(Profile sender) {
		this.sender = sender;
	}

	public Profile getReciver() {
		return reciver;
	}

	public void setReciver(Profile reciver) {
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
