package iob.logic.users;

import java.util.UUID;

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
	
	public UserEntity toEntity(User boundary) {
		UserEntity entity = new UserEntity();
		entity.setUserId(toEntity(boundary.getUserId()));
		entity.setAvatar(boundary.getAvatar());
		entity.setUserName(boundary.getUsername());
		entity.setRole(toEntity(boundary.getRole()));
		return entity;
	}
	
	public UserEntity toEntity(NewUserBoundary user) {
		UserEntity entity = new UserEntity();
		entity.setUserId(UUID.randomUUID().toString());
		entity.setAvatar(user.getAvatar());
		entity.setUserName(user.getUsername());
		entity.setRole(UserRole.player);
		return entity;
	}
	
	
	public UserBoundary toBoundary (UserEntity entity) {
		UserBoundary boundary = new UserBoundary();
		boundary.setUserId(toBoundaryFromJsonString(entity.getUserId()));
		boundary.setAvatar(entity.getAvatar());
		boundary.setUsername(entity.getUserName());
		boundary.setRole(toBoundary(entity.getRole()));
		return boundary;
	}
	
	
	
	public String toEntity (UserId boundaryId) {
		try {
			return this.jackson
				.writeValueAsString(boundaryId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public UserRole toEntity(UserRoleLogic boundaryRole) {
		if (boundaryRole != null) {
			String strRole = boundaryRole.name().toLowerCase();
			UserRole rv = UserRole.valueOf(strRole);
			return rv;
		}else {
			return null;
		}
	}

	public UserRoleLogic toBoundary(UserRole role) {
		if (role != null) {
			return UserRoleLogic.valueOf(role.name().toUpperCase());
		}else {
			return null;
		}
	}
	
	public UserId toBoundaryFromJsonString (String json){
		try {
			return this.jackson.readValue(json, UserId.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
