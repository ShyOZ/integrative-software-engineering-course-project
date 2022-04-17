package iob.logic.users;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import iob.data.UserEntity;


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
		entity.setRole(boundary.getRole());
		return entity;
	}
	
	public UserBoundary toBoundary (UserEntity entity) {
		UserBoundary boundary = new UserBoundary();
		boundary.setUserId(toBoundaryFromJsonString(entity.getUserId()));
		boundary.setAvatar(entity.getAvatar());
		boundary.setUsername(entity.getUserName());
		boundary.setRole(entity.getRole());
		return boundary;
	}
	
	public String toEntity(UserId id) {
		return toEntity(id.getDomain(), id.getEmail());
	}
	
	public String toEntity (String domain, String email) {
		return domain + "/" + email;
	}
	
	public UserId toBoundaryFromJsonString (String json){
		String[] domainId = json.split("/");
		return new UserId(domainId[1], domainId[0]);
	}
}
