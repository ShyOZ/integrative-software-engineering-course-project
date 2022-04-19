package iob;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import iob.logic.activities.ActivityBoundary;
import iob.logic.utility.ConfigProperties;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SanityTest {
	private int port;
	private String url;
	private RestTemplate restTemplate;

	private @Autowired ConfigProperties configProperties;

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + this.port;
		this.restTemplate = new RestTemplate();
	}

	@BeforeEach
	void setUp() throws Exception {
//		this.restTemplate.delete(this.url + "/iob/admin/users?userDomain={domain}&userEmail={email}",
//				configProperties.getApplicationDomain(), configProperties.getApplicationDomain() + "@s.afeka.ac.il");
//		this.restTemplate.delete(this.url + "/iob/admin/instances?userDomain={domain}&userEmail={email}",
//				configProperties.getApplicationDomain(), configProperties.getApplicationDomain() + "@s.afeka.ac.il");
		this.restTemplate.delete(this.url + "/iob/admin/activities?userDomain={domain}&userEmail={email}",
				configProperties.getApplicationDomain(), configProperties.getApplicationDomain() + "@s.afeka.ac.il");
	}

	@AfterEach
	void tearDown() throws Exception {
//		this.restTemplate.delete(this.url + "/iob/admin/users?userDomain={domain}&userEmail={email}",
//				configProperties.getApplicationDomain(), configProperties.getApplicationDomain() + "@s.afeka.ac.il");
//		this.restTemplate.delete(this.url + "/iob/admin/instances?userDomain={domain}&userEmail={email}",
//				configProperties.getApplicationDomain(), configProperties.getApplicationDomain() + "@s.afeka.ac.il");
		this.restTemplate.delete(this.url + "/iob/admin/activities?userDomain={domain}&userEmail={email}",
				configProperties.getApplicationDomain(), configProperties.getApplicationDomain() + "@s.afeka.ac.il");
	}

	@Test
	void test() {
		ActivityBoundary boundary = configProperties.defaultActivityBoundary();

		Object rv = this.restTemplate.postForObject(this.url + "/iob/activities", boundary, Object.class);
		System.err.println(rv);

		assertThat(rv).isNotNull();
		assert true;
	}

}
