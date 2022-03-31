package iob.api.instances;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Match implements Instance{
	
	private Date timestamp;
	private int[] test; // TODO need to delete
	
	public Match() {
		
	}
	
	public Match(Date timestamp, int[] test) {
		super();
		this.timestamp = timestamp;
		this.test = test;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int[] getTest() {
		return test;
	}

	public void setTest(int[] test) {
		this.test = test;
	}

	@Override
	public Map<String, Object> getInstanceAttributes() {
		Map<String, Object> attributes = new TreeMap<String, Object>();
		attributes.put("timestamp", this.timestamp);
		attributes.put("test_int", this.test);
		return attributes;
	}

	@Override
	public String toString() {
		return "Match [timestamp=" + timestamp + ", test=" + Arrays.toString(test) + "]";
	}

}
