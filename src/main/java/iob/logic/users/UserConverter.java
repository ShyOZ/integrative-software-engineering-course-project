package iob.logic.users;

import java.util.UUID;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import iob.data.UserEntity;
import iob.data.UserRole;


@Component
public class UserConverter {
	private ObjectMapper jackson;
	
	@PostConstruct
	public void init() {
		this.jackson = new ObjectMapper();
	}
	
	public UserEntity toEntity(User boundary) {
		UserEntity entity = new UserEntity();
		entity.setUserId(toEntity(boundary.getUserId().getDomain(), boundary.getUserId().getEmail()));
		entity.setAvatar(boundary.getAvatar());
		entity.setUserName(boundary.getUsername());
		entity.setRole(toEntity(boundary.getRole()));
		return entity;
	}
	
	public UserEntity toEntity(NewUserBoundary user) {
		UserEntity entity = new UserEntity();
		entity.setUserId(toEntity("2022b.Yaeli.Bar.Gimelshtei", UUID.randomUUID().toString()));
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
	
	public String toEntity(UserId id) {
		return toEntity(id.getDomain(), id.getEmail());
	}
	
	public String toEntity (String domain, String email) {
		return domain + "/" + email;
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
		String[] domainId = json.split("/");
		return new UserId(domainId[0], domainId[1]);
	}
}
