package iob.logic.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

	@Value("${spring.application.name}")
	public String applicationDomain;
	
	@Value("${activity.like}")
	public String likedUser;
	
	@Value("${activity.like.userId}")
	public String userId;
	
	@Value("${user.property.domain}")
	public String userDomain;
	
	@Value("${user.property.email}")
	public String userEmail;
	
	@Value("${like.match}")
	public String match;

	public String getApplicationDomain() {
		return applicationDomain;
	}

	public String getUserId() {
		return userId;
	}

	public String getlikedUser() {
		return likedUser;
	}
	
	public String getUserDomain() {
		return userDomain;
	}
	
	public String getUserEmail() {
		return userEmail;
	}

	public String getMatch() {
		return match;
	}
}
