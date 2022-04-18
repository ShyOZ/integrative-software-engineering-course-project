package iob.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/* 
ACTIVITIES_TABLE
---------------------------------------------------------------------
ACTIVITY_ID  | TYPE         | INSTANCE     | ACTIVITY_TS | Attributes
VARCHAR(255) | VARCHAR(255) | VARCHAR(255) | TIMESTAMP   | CLOB
<PK>
*/

@Entity
@Table(name = "ACTIVITIES_TABLE")
public class ActivityEntity {
	@Id
	private String activityId;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "INSTANCE")
	private String instance;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTIVITY_TS")
	private Date createdTimestamp;

	@Column(name = "INVOKED_BY")
	private String invokedBy;

	@Column(name = "ATTRIBUTES")
	private String attributes;

	public ActivityEntity() {

	}

	public ActivityEntity(String activityId, String type, String instance, Date createdTimestamp, String invokedBy,
			String attributes) {
		this.activityId = activityId;
		this.type = type;
		this.instance = instance;
		this.createdTimestamp = createdTimestamp;
		this.invokedBy = invokedBy;
		this.attributes = attributes;
	}

	public String getactivityId() {
		return activityId;
	}

	public void setactivityId(String activityId) {
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

}
