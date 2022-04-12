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
 * INSTANCE_TABLE
 * 
 * INSTANCE_ID  | TYPE         | NAME         | ACTIVE  | CREATED_TS | CREATED_BY_DOMAIN | CREATED_BY_EMAIL | LAT    | LNG    | ATTRIBUTES  
 * VARCHAR(255) | VARCHAR(255) | VARCHAR(255) | boolean |            | VARCHAR(255)      | VARCHAR(255)     | double | double | CLOB
 * */

@Entity
@Table(name = "INSTANCE_TABLE")
public class InstanceEntity {
	private String instanceId;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private String createdByDomain;
	private String createdByEmail;
	private double lat;
	private double lng;
	private String attributes;

	public InstanceEntity() {

	}

	@Id
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TS")
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getCreatedByDomain() {
		return createdByDomain;
	}

	public void setCreatedByDomain(String createdByDomain) {
		this.createdByDomain = createdByDomain;
	}

	public String getCreatedByEmail() {
		return createdByEmail;
	}

	public void setCreatedByEmail(String createdByEmail) {
		this.createdByEmail = createdByEmail;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@Lob
	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

}
