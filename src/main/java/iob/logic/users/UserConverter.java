package iob.logic.users;

import org.springframework.stereotype.Component;

import iob.data.UserEntity;
import iob.data.UserEntityId;

@Component
public class UserConverter {

	public UserEntity toEntity(UserBoundary boundary) {
		UserEntity entity = new UserEntity();
		entity.setUserId(toEntity(boundary.getUserId()));
		entity.setAvatar(boundary.getAvatar());
		entity.setUserName(boundary.getUsername());
		entity.setRole(boundary.getRole());
		return entity;
	}

	public UserBoundary toBoundary(UserEntity entity) {
		UserBoundary boundary = new UserBoundary();
		boundary.setUserId(toBoundary(entity.getUserId()));
		boundary.setAvatar(entity.getAvatar());
		boundary.setUsername(entity.getUserName());
		boundary.setRole(entity.getRole());
		return boundary;
	}

	public UserEntityId toEntity(UserId boundaryId) {
		return new UserEntityId(boundaryId.getEmail(), boundaryId.getDomain());
	}

	public UserEntityId toEntity(String email, String domain) {
		return new UserEntityId(email, domain);
	}

	public UserId toBoundary(UserEntityId entityId) {
		return new UserId(entityId.getEmail(), entityId.getDomain());
	}
}
