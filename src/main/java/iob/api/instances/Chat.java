package iob.api.instances;

import java.util.Map;
import java.util.TreeMap;
import java.util.List;

public class Chat implements Instance {
	private List<Message> chat;
	
	public Chat() {

	}

	public Chat(List<Message> chat) {
		super();
		this.chat = chat;
	}


	public List<Message> getChat() {
		return chat;
	}


	public void setChat(List<Message> chat) {
		this.chat = chat;
	}



	@Override
	public String toString() {
		return "Chat [chat=" + chat + "]";
	}



	@Override
	public Map<String, Object> getInstanceAttributes() {
		Map<String, Object> attributes = new TreeMap<String, Object>();
		attributes.put("chat", this.chat);
		return attributes;
	}
	

}
