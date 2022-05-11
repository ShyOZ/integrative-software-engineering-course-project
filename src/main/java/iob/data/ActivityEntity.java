package iob.data;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import iob.logic.activities.ActivityId;

/* 
ACTIVITIES_TABLE
---------------------------------------------------------------------
ACTIVITY_ID  | TYPE         | INSTANCE     | ACTIVITY_TS | Attributes
VARCHAR(255) | VARCHAR(255) | VARCHAR(255) | TIMESTAMP   | CLOB
<PK>
*/

@Document(collection = "activities")
public class ActivityEntity {
	
	private @Id ActivityId activityId;
	private String type;
	private String instance;
	private @Indexed Date createdTimestamp;
	private String invokedBy;
	private String attributes;
	private @Indexed int version;

	public ActivityEntity() {

	}

	public ActivityEntity(ActivityId activityId, String type, String instance, Date createdTimestamp, String invokedBy,
			String attributes) {
		this.activityId = activityId;
		this.type = type;
		this.instance = instance;
		this.createdTimestamp = createdTimestamp;
		this.invokedBy = invokedBy;
		this.attributes = attributes;
	}

	public ActivityId getactivityId() {
		return activityId;
	}

	public void setactivityId(ActivityId activityId) {
		this.activityId = activityId;
	}

	public String getType() {
		return type;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(String invokedBy) {
		this.invokedBy = invokedBy;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
