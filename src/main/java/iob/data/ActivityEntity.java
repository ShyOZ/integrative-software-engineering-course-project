package iob.data;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/*
 * Activity Table
 * Activity ID | Type         | Performer    | Receiver     | Attributes
 * String      | VARCHAR(255) | UserEntityId | UserEntityId | Map
 * 
 * 
 * */

@Entity
@Table(name = "ACTIVITIES_TABLE")
public class ActivityEntity {
	private String activityId;
	private String type;
	private String instance;
	private Date createdTimestamp;
	private String invokedBy;
	private String attributes;
	
	public ActivityEntity() {
		
	}

	public ActivityEntity(String activityId, String type, String instance, Date createdTimestamp,
			String invokedBy, String attributes) {
		this.activityId = activityId;
		this.type = type;
		this.instance = instance;
		this.createdTimestamp = createdTimestamp;
		this.invokedBy = invokedBy;
		this.attributes = attributes;
	}

	@Column(name="Type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="Invoked_By")
	public String getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(String invokedBy) {
		this.invokedBy = invokedBy;
	}

	@Column(name="Attributes")
	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	@Id
	public String getactivityId() {
		return activityId;
	}

	public void setactivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Column(name="Instance")
	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	@Column(name="Created_Timestamp")
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

}
