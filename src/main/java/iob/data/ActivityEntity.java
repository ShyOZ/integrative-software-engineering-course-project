package iob.data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/*
 * Activity Table
 * Activity ID | Type         | Performer    | Receiver     | Attributes
 * ActivityId  | VARCHAR(255) | UserEntityId | UserEntityId | Map
 * 
 * 
 * */

@Entity
@Table(name = "ACTIVITIES_TABLE")
public class ActivityEntity {
	private String activityId;
	private String type;
	private String performerId;
	private String receiverId;
//	private Map<String, Object> attributes;
//	private ActivityId activityId
	
	public ActivityEntity() {
		
	}

	public ActivityEntity(String activityId, String type, String performer, String receiver
			/*, Map<String, Object> attributes*/) {
		this.activityId = activityId;
		this.type = type;
		this.performerId = performer;
		this.receiverId = receiver;
//		this.attributes = attributes;
	}

	@Column(name="Type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="Performer")
	public String getPerformerId() {
		return performerId;
	}

	public void setPerformerId(String performer) {
		this.performerId = performer;
	}

	@Column(name="Receiver")
	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiver) {
		this.receiverId = receiver;
	}

//	@Column(name="Attributes")
//	public Map<String, Object> getAttributes() {
//		return attributes;
//	}
//
//	public void setAttributes(Map<String, Object> attributes) {
//		this.attributes = attributes;
//	}

	@Id
	public String getactivityId() {
		return activityId;
	}

	public void setactivityId(String activityId) {
		this.activityId = activityId;
	}

}
