package iob.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACTIVITIES_TABLE")
public class ActivityEntity {
	//TODO: OVERRIDE BASIC IMPLEMENTATION
	private String activityId;

	@Id
	public String getactivityId() {
		return activityId;
	}

	public void setactivityId(String activityId) {
		this.activityId = activityId;
	}

}
