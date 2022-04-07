package iob.logic.users;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import iob.data.UserEntity;
import iob.data.UserRole;
import iob.logic.instances.UserId;

@Component
public class userConverter {
	private ObjectMapper jackson;
	
	@PostConstruct
	public void init() {
		this.jackson = new ObjectMapper();
	}
	
	public UserEntity toEntity(UserBoundary boundary) {
		UserEntity entity = new UserEntity();
		
		
		
		return entity;
	}

}
