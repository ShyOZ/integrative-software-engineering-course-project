package iob;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.htmlunit.LocalHostWebClient;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import iob.logic.activities.ActivityBoundary;
import iob.logic.users.UserId;
import iob.logic.utility.ConfigProperties;
import iob.utility.TestProperties;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SanityTest {
	private @LocalServerPort int port;
	private String url;
	private RestTemplate restTemplate;
	private @Autowired TestProperties testProperties;
	
	private UserId adminId;

	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + this.port;
		this.restTemplate = new RestTemplate();
		adminId = testProperties.getAdminUser().getUserId();
	}

	@BeforeEach
	void setUp() throws Exception {
		this.restTemplate.delete(this.url + "/iob/admin/activities?userDomain={domain}&userEmail={email}",
				adminId.getDomain(), adminId.getEmail());
		this.restTemplate.delete(this.url + "/iob/admin/instances?userDomain={domain}&userEmail={email}",
				adminId.getDomain(), adminId.getEmail());
		this.restTemplate.delete(this.url + "/iob/admin/users?userDomain={domain}&userEmail={email}",
				adminId.getDomain(), adminId.getEmail());
	}

	@AfterEach
	void tearDown() throws Exception {
		this.restTemplate.delete(this.url + "/iob/admin/activities?userDomain={domain}&userEmail={email}",
				adminId.getDomain(), adminId.getEmail());
		this.restTemplate.delete(this.url + "/iob/admin/instances?userDomain={domain}&userEmail={email}",
				adminId.getDomain(), adminId.getEmail());
		this.restTemplate.delete(this.url + "/iob/admin/users?userDomain={domain}&userEmail={email}",
				adminId.getDomain(), adminId.getEmail());
	}

	@Test
	void test() {
		ActivityBoundary boundary = new ActivityBoundary(); // testProperties.defaultActivityBoundary();

		Object rv = this.restTemplate.postForObject(this.url + "/iob/activities", boundary, Object.class);
		System.err.println(rv);

		assertThat(rv).isNotNull();
		assert true;
	}
}
