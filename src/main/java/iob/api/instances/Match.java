package iob.api.instances;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Match implements Instance {

	private Date timestamp;

	public Match() {

	}

	public Match(Date timestamp) {
		super();
		this.timestamp = timestamp;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public Map<String, Object> getInstanceAttributes() {
		Map<String, Object> attributes = new TreeMap<String, Object>();
		attributes.put("timestamp", this.timestamp);
		return attributes;
	}

	@Override
	public String toString() {
		return "Match [timestamp=" + timestamp + "]";
	}

}
