package iob.logic.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import iob.logic.activities.ActivityBoundary;
import iob.logic.instances.InstanceBoundary;
import iob.logic.users.UserBoundary;

@Configuration
public class ConfigProperties {
	
	@Value("${spring.application.name}")
	public String applicationDomain;

	public String getApplicationDomain() {
		return applicationDomain;
	}

	@Bean
	@ConfigurationProperties(prefix = "configurable.user")
	public UserBoundary defaultUserBoundary() {
		return new UserBoundary();
	}

	@Bean
	@ConfigurationProperties(prefix = "configurable.activity")
	public ActivityBoundary defaultActivityBoundary() {
		return new ActivityBoundary();
	}

	@Bean
	@ConfigurationProperties(prefix = "configurable.instance")
	public InstanceBoundary defaultInstanceBoundary() {
		return new InstanceBoundary();
	}
	
	
}
