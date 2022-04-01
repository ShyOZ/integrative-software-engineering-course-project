package iob.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS_TABLE")
public class UserEntity {
	//TODO: OVERRIDE BASIC IMPLEMENTATION
	
	private String userId;
	
	@Id
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
