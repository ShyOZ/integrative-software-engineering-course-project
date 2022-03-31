package iob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class DuetApplication {
	
	public static final String API_PREFIX = "/iob";
	public static final String INSTANCES_HEADER = "/instances";
	public static final String USERS_HEADER = "/users";
	public static final String DOMAIN = "/2022b.Yaeli.Bar.Gimelshtei";
	public static final String ADMIN_HEADER = "/admin";
	public static final String ACTIVITIES_HEADER = "/activities";

	public static void main(String[] args) {
		SpringApplication.run(DuetApplication.class, args);
	}

}
