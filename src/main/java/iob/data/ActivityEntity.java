package iob.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
	private String activityId;
	private String type;
	private String instance;
	private Date createdTimestamp;
	private String invokedBy;
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

	@Id
	public String getactivityId() {
		return activityId;
	}

	public void setactivityId(String activityId) {
		this.activityId = activityId;
	}

	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	@Lob
	@Column(name = "INSTANCE")
	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTIVITY_TS")
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Lob
	@Column(name = "INVOKED_BY")
	public String getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(String invokedBy) {
		this.invokedBy = invokedBy;
	}

	@Lob
	@Column(name = "ATTRIBUTES")
	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

}
