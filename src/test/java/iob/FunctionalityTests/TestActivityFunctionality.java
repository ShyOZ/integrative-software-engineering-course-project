package iob.FunctionalityTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.RestTemplate;

import iob.logic.activities.ActivityBoundary;
import iob.logic.activities.ActivityInstance;
import iob.logic.activities.ActivityInvoker;
import iob.logic.instances.InstanceBoundary;
import iob.logic.instances.InstanceId;
import iob.logic.users.UserBoundary;
import iob.logic.users.UserId;
import iob.utility.TestProperties;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestActivityFunctionality {

	private @LocalServerPort int port;
	private String url;
	private RestTemplate restTemplate;
	private @Autowired TestProperties testProperties;
	private UserBoundary admin, player, manager;
	private InstanceBoundary testInstance;

	@PostConstruct
	public void init() {
		restTemplate = new RestTemplate();
		url = "http://localhost:" + port;
	}

	@BeforeEach
	void setUp() {
		player = restTemplate.postForObject(url + "/iob/users", testProperties.getNewPlayer(), UserBoundary.class);
		manager = restTemplate.postForObject(url + "/iob/users", testProperties.getNewManager(), UserBoundary.class);
		admin = restTemplate.postForObject(url + "/iob/users", testProperties.getNewAdmin(), UserBoundary.class);
		testInstance = restTemplate.postForObject(url + "/iob/instances", testProperties.getInstance(),
				InstanceBoundary.class);
	}

	@AfterEach
	void tearDown() {
		restTemplate.delete(url + "/iob/admin/activities?userDomain={domain}&userEmail={email}",
				admin.getUserId().getDomain(), admin.getUserId().getEmail());

		restTemplate.delete(url + "/iob/admin/instances?userDomain={domain}&userEmail={email}",
				admin.getUserId().getDomain(), admin.getUserId().getEmail());

		restTemplate.delete(url + "/iob/admin/users?userDomain={domain}&userEmail={email}",
				admin.getUserId().getDomain(), admin.getUserId().getEmail());
	}

	@Test
	void ifUserIsNotAnAdmin_thenDeleteAllActivitiesThrowsUnauthorizedWithMessage() {

		ActivityBoundary testActivity = new ActivityBoundary(null, "test type",
				new ActivityInstance(testInstance.getInstanceId()), null, new ActivityInvoker(player.getUserId()),
				null);

		IntStream.range(0, 5)
				.mapToObj(
						i -> restTemplate.postForObject(url + "/iob/activities", testActivity, ActivityBoundary.class))
				.collect(Collectors.toList());

		assertThat(assertThrows(Unauthorized.class,
				() -> restTemplate.delete(url + "/iob/admin/activities?userDomain={domain}&userEmail={email}",
						player.getUserId().getDomain(), player.getUserId().getEmail()))
				.getMessage()).contains("user must be an admin to perform this action");

		assertThat(assertThrows(Unauthorized.class,
				() -> restTemplate.delete(url + "/iob/admin/activities?userDomain={domain}&userEmail={email}",
						manager.getUserId().getDomain(), manager.getUserId().getEmail()))
				.getMessage()).contains("user must be an admin to perform this action");
	}

	@Test
	void ifUserIsNotAnAdmin_thenGetAllActivitiesThrowsUnauthorizedWithMessage() {

		ActivityBoundary testActivity = new ActivityBoundary(null, "test type",
				new ActivityInstance(testInstance.getInstanceId()), null, new ActivityInvoker(player.getUserId()),
				null);

		IntStream.range(0, 5)
				.mapToObj(
						i -> restTemplate.postForObject(url + "/iob/activities", testActivity, ActivityBoundary.class))
				.collect(Collectors.toList());

		assertThat(assertThrows(Unauthorized.class,
				() -> restTemplate.getForObject(url + "/iob/admin/activities?userDomain={domain}&userEmail={email}",
						Object[].class, player.getUserId().getDomain(), player.getUserId().getEmail()))
				.getMessage()).contains("user must be an admin to perform this action");

		assertThat(assertThrows(Unauthorized.class,
				() -> restTemplate.getForObject(url + "/iob/admin/activities?userDomain={domain}&userEmail={email}",
						Object[].class, manager.getUserId().getDomain(), manager.getUserId().getEmail()))
				.getMessage()).contains("user must be an admin to perform this action");
	}

	@Test
	void testGetAllActivitiesWithPagination() {

		IntStream.range(0, 20)
				.mapToObj(
						i -> new ActivityBoundary(null, "test type", new ActivityInstance(testInstance.getInstanceId()),
								null, new ActivityInvoker(player.getUserId()), Collections.singletonMap("key", 1)))
				.map(activity -> restTemplate.postForObject(url + "/iob/activities", activity, Object.class))
				.collect(Collectors.toList());

		assertThat(restTemplate.getForObject(url + "/iob/admin/activities?userDomain={domain}&userEmail={email}",
				Object[].class, admin.getUserId().getDomain(), admin.getUserId().getEmail())).hasSize(10);

		assertThat(restTemplate.getForObject(
				url + "/iob/admin/activities?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				Object[].class, admin.getUserId().getDomain(), admin.getUserId().getEmail(), 30, 0)).hasSize(20);

		assertThat(restTemplate.getForObject(
				url + "/iob/admin/activities?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				Object[].class, admin.getUserId().getDomain(), admin.getUserId().getEmail(), 6, 0)).hasSize(6);

		assertThat(restTemplate.getForObject(
				url + "/iob/admin/activities?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				Object[].class, admin.getUserId().getDomain(), admin.getUserId().getEmail(), 13, 1)).hasSize(7);
	}

	@Test
	void isActivityIsMissingInstance_thenInvokeActivityThrowsBadRequestWithInstanceIsMissingMessage() {
		ActivityBoundary testActivity = new ActivityBoundary(null, "test type", null, null,
				new ActivityInvoker(player.getUserId()), null);

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/activities", testActivity, ActivityBoundary.class))
				.getMessage()).contains("instance is missing");

		testActivity.setInstance(new ActivityInstance(null));

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/activities", testActivity, ActivityBoundary.class))
				.getMessage()).contains("instance.instanceId is missing");

		testActivity.getInstance().setInstanceId(new InstanceId(null, "test id"));

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/activities", testActivity, ActivityBoundary.class))
				.getMessage()).contains("instance.instanceId.domain is missing");

		testActivity.getInstance().setInstanceId(new InstanceId("test domain", null));

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/activities", testActivity, ActivityBoundary.class))
				.getMessage()).contains("instance.instanceId.id is missing");
	}

	@Test
	void isActivityIsMissingInvokedBy_thenInvokeActivityThrowsBadRequestWithInvokedByIsMissingMessage() {
		ActivityBoundary testActivity = new ActivityBoundary(null, "test type",
				new ActivityInstance(testInstance.getInstanceId()), null, null, null);

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/activities", testActivity, ActivityBoundary.class))
				.getMessage()).contains("invokedBy is missing");

		testActivity.setInvokedBy(new ActivityInvoker(null));

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/activities", testActivity, ActivityBoundary.class))
				.getMessage()).contains("invokedBy.userId is missing");

		testActivity.setInvokedBy(new ActivityInvoker(new UserId("test email", null)));

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/activities", testActivity, ActivityBoundary.class))
				.getMessage()).contains("invokedBy.userId.domain is missing");

		testActivity.setInvokedBy(new ActivityInvoker(new UserId(null, "test domain")));

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/activities", testActivity, ActivityBoundary.class))
				.getMessage()).contains("invokedBy.userId.email is missing");

	}

}
