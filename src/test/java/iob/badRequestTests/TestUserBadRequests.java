package iob.badRequestTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestTemplate;

import iob.logic.users.NewUserBoundary;
import iob.logic.users.UserBoundary;
import iob.utility.TestProperties;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TestUserBadRequests {

	private @LocalServerPort int port;
	private String url;
	private RestTemplate restTemplate;
	private @Autowired TestProperties testProperties;
	private UserBoundary admin;
	private NewUserBoundary newPlayer;

	@PostConstruct
	public void init() {
		admin = testProperties.getAdminUser();
		newPlayer = testProperties.getNewPlayer();
		this.restTemplate = new RestTemplate();
		this.url = "http://localhost:" + this.port;
		System.err.println(this.url);
	}

	@BeforeEach
	void setUp() {
		this.restTemplate.postForObject(this.url + "/iob/users", testProperties.getNewAdmin(), UserBoundary.class);
	}

	@AfterEach
	void tearDown() {
		this.restTemplate.delete(this.url + "/iob/admin/users?userDomain={domain}&userEmail={email}",
				admin.getUserId().getDomain(), admin.getUserId().getEmail());
	}

	@Test
	void testMissingEmail() {
		NewUserBoundary playerWithoutEmail = new NewUserBoundary(null, newPlayer.getRole(), newPlayer.getUsername(),
				newPlayer.getAvatar());

		assertThat(assertThrows(BadRequest.class,
				() -> this.restTemplate.postForObject(this.url + "/iob/users", playerWithoutEmail, UserBoundary.class))
				.getMessage()).contains("email is missing");
	}

	@Test
	void testMissingRole() {
		NewUserBoundary playerWithoutRole = new NewUserBoundary(newPlayer.getEmail(), null, newPlayer.getUsername(),
				newPlayer.getAvatar());

		assertThat(assertThrows(BadRequest.class,
				() -> this.restTemplate.postForObject(this.url + "/iob/users", playerWithoutRole, UserBoundary.class))
				.getMessage()).contains("role is missing");
	}

	@Test
	void testMissingUsername() {
		NewUserBoundary playerWithoutUsername = new NewUserBoundary(newPlayer.getEmail(), newPlayer.getRole(), null,
				newPlayer.getAvatar());

		assertThat(assertThrows(BadRequest.class, () -> this.restTemplate.postForObject(this.url + "/iob/users",
				playerWithoutUsername, UserBoundary.class)).getMessage()).contains("username is missing");
	}

	@Test
	void testMissingAvatar() {
		NewUserBoundary playerWithoutAvatar = new NewUserBoundary(newPlayer.getEmail(), newPlayer.getRole(),
				newPlayer.getUsername(), null);

		assertThat(assertThrows(BadRequest.class,
				() -> this.restTemplate.postForObject(this.url + "/iob/users", playerWithoutAvatar, UserBoundary.class))
				.getMessage()).contains("avatar is missing");
	}

}
