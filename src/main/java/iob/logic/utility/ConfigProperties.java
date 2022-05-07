package iob.logic.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

	@Value("${spring.application.name}")
	public String applicationDomain;

	public String getApplicationDomain() {
		return applicationDomain;
	}
}
