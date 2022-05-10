package iob.FunctionalityTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

import iob.logic.instances.InstanceBoundary;
import iob.logic.users.UserBoundary;
import iob.logic.users.UserId;
import iob.logic.utility.Location;
import iob.utility.TestProperties;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestInstanceFunctionality {
	private @LocalServerPort int port;
	private String url;
	private RestTemplate restTemplate;
	private @Autowired TestProperties testProperties;
	private UserBoundary admin, player, manager;
	private InstanceBoundary testInstance;

	@PostConstruct
	public void init() {
		testInstance = testProperties.getInstance();
		restTemplate = new RestTemplate();
		url = "http://localhost:" + port;
	}

	@BeforeEach
	void setUp() {
		player = restTemplate.postForObject(url + "/iob/users", testProperties.getNewPlayer(), UserBoundary.class);
		manager = restTemplate.postForObject(url + "/iob/users", testProperties.getNewManager(), UserBoundary.class);
		admin = restTemplate.postForObject(url + "/iob/users", testProperties.getNewAdmin(), UserBoundary.class);
	}

	@AfterEach
	void tearDown() {
		restTemplate.delete(url + "/iob/admin/instances?userDomain={domain}&userEmail={email}",
				admin.getUserId().getDomain(), admin.getUserId().getEmail());

		restTemplate.delete(url + "/iob/admin/users?userDomain={domain}&userEmail={email}",
				admin.getUserId().getDomain(), admin.getUserId().getEmail());
	}

	@Test
	void testInstanceCreationUpdateAndRetrieval() {

		InstanceBoundary createdInstance = restTemplate.postForObject(url + "/iob/instances", testInstance,
				InstanceBoundary.class);

		assertThat(restTemplate.getForObject(
				url + "/iob/instances/{instanceDomain}/{instanceId}?userDomain={domain}&userEmail={email}",
				InstanceBoundary.class, createdInstance.getInstanceId().getDomain(),
				createdInstance.getInstanceId().getId(), manager.getUserId().getDomain(),
				manager.getUserId().getEmail())).isNotNull();

		createdInstance.setName("updated " + createdInstance.getName());
		createdInstance.setType("updated " + createdInstance.getType());
		createdInstance.setLocation(new Location(1., 1.));
		createdInstance.setInstanceAttributes(Collections.singletonMap("key", "property"));

		restTemplate.put(url + "/iob/instances/{instanceDomain}/{instanceId}?userDomain={domain}&userEmail={email}",
				createdInstance, createdInstance.getInstanceId().getDomain(), createdInstance.getInstanceId().getId(),
				manager.getUserId().getDomain(), manager.getUserId().getEmail());

		assertThat(restTemplate.getForObject(
				url + "/iob/instances/{instanceDomain}/{instanceId}?userDomain={domain}&userEmail={email}",
				InstanceBoundary.class, createdInstance.getInstanceId().getDomain(),
				createdInstance.getInstanceId().getId(), manager.getUserId().getDomain(),
				manager.getUserId().getEmail())).usingRecursiveComparison().isEqualTo(createdInstance);

		assertThat(restTemplate.getForObject(
				url + "/iob/instances/{instanceDomain}/{instanceId}?userDomain={domain}&userEmail={email}",
				InstanceBoundary.class, createdInstance.getInstanceId().getDomain(),
				createdInstance.getInstanceId().getId(), player.getUserId().getDomain(), player.getUserId().getEmail()))
				.usingRecursiveComparison().isEqualTo(createdInstance);

		createdInstance.setActive(false);

		restTemplate.put(url + "/iob/instances/{instanceDomain}/{instanceId}?userDomain={domain}&userEmail={email}",
				createdInstance, createdInstance.getInstanceId().getDomain(), createdInstance.getInstanceId().getId(),
				manager.getUserId().getDomain(), manager.getUserId().getEmail());

		assertThat(assertThrows(NotFound.class,
				() -> restTemplate.getForObject(
						url + "/iob/instances/{instanceDomain}/{instanceId}?userDomain={domain}&userEmail={email}",
						InstanceBoundary.class, createdInstance.getInstanceId().getDomain(),
						createdInstance.getInstanceId().getId(), player.getUserId().getDomain(),
						player.getUserId().getEmail()))
				.getMessage())
				.contains("no active instance with domain=" + createdInstance.getInstanceId().getDomain() + " and id="
						+ createdInstance.getInstanceId().getId());

		assertThat(assertThrows(Unauthorized.class,
				() -> restTemplate.getForObject(
						url + "/iob/instances/{instanceDomain}/{instanceId}?userDomain={domain}&userEmail={email}",
						InstanceBoundary.class, createdInstance.getInstanceId().getDomain(),
						createdInstance.getInstanceId().getId(), admin.getUserId().getDomain(),
						admin.getUserId().getEmail()))
				.getMessage()).contains("user must be either a manager or a player to perform this action");
	}

	@Test
	void ifUserIsNotManager_thenIstanceCreationThrowsBadRequestWithMwssage() {
		InstanceBoundary instanceCreatedByAdmin = new InstanceBoundary(null, "test type", "test name", true, null,
				Collections.singletonMap("userId", admin.getUserId()), null, null);
		InstanceBoundary instanceCreatedByPlayer = new InstanceBoundary(null, "test type", "test name", true, null,
				Collections.singletonMap("userId", player.getUserId()), null, null);

		assertThat(assertThrows(BadRequest.class, () -> restTemplate.postForObject(url + "/iob/instances",
				instanceCreatedByAdmin, InstanceBoundary.class)).getMessage())
				.contains("userId must belong to a manager");

		assertThat(assertThrows(BadRequest.class, () -> restTemplate.postForObject(url + "/iob/instances",
				instanceCreatedByPlayer, InstanceBoundary.class)).getMessage())
				.contains("userId must belong to a manager");

	}

	@Test
	void ifInstanceDoesNotExist_thenGetInstanceThrowsNotFoundWithCouldNotFindInstanceMessage() {
		IntStream.range(0, 5)
				.mapToObj(i -> restTemplate.postForObject(url + "/iob/instances", testInstance, InstanceBoundary.class))
				.collect(Collectors.toList());

		assertThat(assertThrows(NotFound.class,
				() -> restTemplate.getForObject(
						url + "/iob/instances/{instanceDomain}/{instanceId}?userDomain={domain}&userEmail={email}",
						InstanceBoundary.class, "1", "1", manager.getUserId().getDomain(),
						manager.getUserId().getEmail()))
				.getMessage()).contains("could not find instance by id");
	}

	@Test
	void ifInstanceIsMissingType_thenCreateInstanceThrowsBadRequestWithTypeIsMissingMessage() {
		InstanceBoundary instanceWithoutType = new InstanceBoundary(null, null, "", true, null,
				testInstance.getCreatedBy(), null, null);

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/instances", instanceWithoutType, InstanceBoundary.class))
				.getMessage()).contains("type is missing");
	}

	@Test
	void ifInstanceIsMissingName_thenCreateInstanceThrowsBadRequestWithNameIsMissingMessage() {
		InstanceBoundary instanceWithoutName = new InstanceBoundary(null, "", null, true, null,
				testInstance.getCreatedBy(), null, null);

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/instances", instanceWithoutName, InstanceBoundary.class))
				.getMessage()).contains("name is missing");
	}

	@Test
	void ifInstanceIsMissingCreatedBy_thenCreateInstanceThrowsBadRequestWithCreatedByIsMissingMessage() {
		InstanceBoundary instanceWithoutCreatedBy = new InstanceBoundary(null, "", "", true, null, null, null, null);

		assertThat(assertThrows(BadRequest.class, () -> restTemplate.postForObject(url + "/iob/instances",
				instanceWithoutCreatedBy, InstanceBoundary.class)).getMessage()).contains("CreatedBy is missing");
	}

	@Test
	void ifInstanceIsMissingCreatedByUserId_thenCreateInstanceThrowsBadRequestWithCreatedByUserIdIsMissingMessage() {
		InstanceBoundary instanceWithoutCreatedByUserId = new InstanceBoundary(null, "", "", true, null,
				new HashMap<String, UserId>(), null, null);

		assertThat(assertThrows(BadRequest.class, () -> restTemplate.postForObject(url + "/iob/instances",
				instanceWithoutCreatedByUserId, InstanceBoundary.class)).getMessage())
				.contains("creator->userId is missing");
	}

	@Test
	void ifInstanceIsMissingCreatedByUserIdEmail_thenCreateInstanceThrowsBadRequestWithCreatedByUserIdEmailIsMissingMessage() {
		InstanceBoundary instanceWithoutCreatedByUserIdEmail = new InstanceBoundary(null, testInstance.getType(),
				testInstance.getName(), true, null, null, null, null);

		Map<String, UserId> createdByWithoutUserIdEmail = new HashMap<String, UserId>();
		createdByWithoutUserIdEmail.put("userId", new UserId(null, ""));

		instanceWithoutCreatedByUserIdEmail.setCreatedBy(createdByWithoutUserIdEmail);

		assertThat(assertThrows(BadRequest.class, () -> restTemplate.postForObject(url + "/iob/instances",
				instanceWithoutCreatedByUserIdEmail, InstanceBoundary.class)).getMessage())
				.contains("creator->userId->email is missing");
	}

	@Test
	void ifInstanceIsMissingCreatedByUserIdDomain_thenCreateInstanceThrowsBadRequestWithCreatedByUserIdDomainIsMissingMessage() {
		InstanceBoundary instanceWithoutCreatedByUserIdDomain = new InstanceBoundary(null, "", "", true, null, null,
				null, null);

		Map<String, UserId> createdByWithoutUserIdDomain = new HashMap<String, UserId>();
		createdByWithoutUserIdDomain.put("userId", new UserId("", null));

		instanceWithoutCreatedByUserIdDomain.setCreatedBy(createdByWithoutUserIdDomain);

		assertThat(assertThrows(BadRequest.class, () -> restTemplate.postForObject(url + "/iob/instances",
				instanceWithoutCreatedByUserIdDomain, InstanceBoundary.class)).getMessage())
				.contains("creator->userId->domain is missing");
	}

	@Test
	void testGetAllInstancesWithPagination() {
		InstanceBoundary activeInstance = new InstanceBoundary(null, "", "", true, null, testInstance.getCreatedBy(),
				null, null);

		InstanceBoundary inactiveInstance = new InstanceBoundary(null, "", "", false, null, testInstance.getCreatedBy(),
				null, null);

		IntStream.range(0, 15)
				.mapToObj(
						i -> restTemplate.postForObject(url + "/iob/instances", activeInstance, InstanceBoundary.class))
				.collect(Collectors.toList());

		IntStream.range(0, 15).mapToObj(
				i -> restTemplate.postForObject(url + "/iob/instances", inactiveInstance, InstanceBoundary.class))
				.collect(Collectors.toList());

		assertThat(restTemplate.getForObject(
				url + "/iob/instances?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				InstanceBoundary[].class, player.getUserId().getDomain(), player.getUserId().getEmail(), 20, 0))
				.hasSize(15);

		assertThat(restTemplate.getForObject(
				url + "/iob/instances?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				InstanceBoundary[].class, manager.getUserId().getDomain(), manager.getUserId().getEmail(), 20, 0))
				.hasSize(20);

		assertThat(assertThrows(Unauthorized.class,
				() -> restTemplate.getForObject(
						url + "/iob/instances?userDomain={domain}&userEmail={email}&size={size}&page={page}",
						InstanceBoundary[].class, admin.getUserId().getDomain(), admin.getUserId().getEmail(), 20, 0))
				.getMessage()).contains("user must be either a manager or a player to perform this action");
	}

	@Test
	void ifUserIsPlayerOrManager_thenDeleteAllInstancesThrowsUnauthorizedWithMessage() {
		IntStream.range(0, 5)
				.mapToObj(i -> restTemplate.postForObject(url + "/iob/instances", testInstance, InstanceBoundary.class))
				.collect(Collectors.toList());

		assertThat(assertThrows(Unauthorized.class,
				() -> restTemplate.delete(url + "/iob/admin/instances?userDomain={domain}&userEmail={email}",
						player.getUserId().getDomain(), player.getUserId().getEmail()))
				.getMessage()).contains("user must be an admin to perform this action");

		assertThat(assertThrows(Unauthorized.class,
				() -> restTemplate.delete(url + "/iob/admin/instances?userDomain={domain}&userEmail={email}",
						manager.getUserId().getDomain(), manager.getUserId().getEmail()))
				.getMessage()).contains("user must be an admin to perform this action");
	}

	void testSearchByNameWithPagination() {
		IntStream
				.range(0,
						10)
				.mapToObj(
						i -> restTemplate
								.postForObject(url + "/iob/instances",
										new InstanceBoundary(null, "", "first name", true, null,
												testInstance.getCreatedBy(), null, null),
										InstanceBoundary.class))
				.collect(Collectors.toList());

		IntStream.range(0, 10)
				.mapToObj(i -> restTemplate.postForObject(url + "/iob/instances",
						new InstanceBoundary(null, "", "first name", false, null, null, null, null),
						InstanceBoundary.class))
				.collect(Collectors.toList());

		IntStream.range(0, 10)
				.mapToObj(i -> restTemplate.postForObject(url + "/iob/instances",
						new InstanceBoundary(null, "", "second name", true, null, null, null, null),
						InstanceBoundary.class))
				.collect(Collectors.toList());

		assertThat(restTemplate.getForObject(url
				+ "/iob/instances/search/byName/{name}?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				InstanceBoundary[].class, "first name", manager.getUserId().getDomain(), manager.getUserId().getEmail(),
				6, 1)).hasSize(6);

		assertThat(restTemplate.getForObject(url
				+ "/iob/instances/search/byName/{name}?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				InstanceBoundary[].class, "first name", player.getUserId().getDomain(), player.getUserId().getEmail(),
				6, 1)).hasSize(4);

		assertThat(assertThrows(Unauthorized.class, () -> restTemplate.getForObject(url
				+ "/iob/instances/search/byName/{name}?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				InstanceBoundary[].class, "first name", admin.getUserId().getDomain(), admin.getUserId().getEmail(), 6,
				1)).getMessage()).contains("user must be either a manager or a player to perform this action");
	}

	void testSearchByTypeWithPagination() {
		IntStream
				.range(0,
						10)
				.mapToObj(
						i -> restTemplate
								.postForObject(url + "/iob/instances",
										new InstanceBoundary(null, "first type", "", true, null,
												testInstance.getCreatedBy(), null, null),
										InstanceBoundary.class))
				.collect(Collectors.toList());

		IntStream
				.range(0,
						10)
				.mapToObj(
						i -> restTemplate
								.postForObject(url + "/iob/instances",
										new InstanceBoundary(null, "first type", "", false, null,
												testInstance.getCreatedBy(), null, null),
										InstanceBoundary.class))
				.collect(Collectors.toList());

		IntStream
				.range(0,
						10)
				.mapToObj(
						i -> restTemplate
								.postForObject(url + "/iob/instances",
										new InstanceBoundary(null, "second type", "", true, null,
												testInstance.getCreatedBy(), null, null),
										InstanceBoundary.class))
				.collect(Collectors.toList());

		assertThat(restTemplate.getForObject(url
				+ "/iob/instances/search/byType/{type}?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				InstanceBoundary[].class, "first type", manager.getUserId().getDomain(), manager.getUserId().getEmail(),
				6, 1)).hasSize(6);

		assertThat(restTemplate.getForObject(url
				+ "/iob/instances/search/byType/{type}?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				InstanceBoundary[].class, "first type", player.getUserId().getDomain(), player.getUserId().getEmail(),
				6, 1)).hasSize(4);

		assertThat(assertThrows(Unauthorized.class, () -> restTemplate.getForObject(url
				+ "/iob/instances/search/byType/{type}?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				InstanceBoundary[].class, "first type", admin.getUserId().getDomain(), admin.getUserId().getEmail(), 6,
				1)).getMessage()).contains("user must be either a manager or a player to perform this action");
	}

	@Test
	void testSearchNearWithPagination() {
		restTemplate.postForObject(url + "/iob/instances",
				new InstanceBoundary(null, "", "", true, null, testInstance.getCreatedBy(), null, null),
				InstanceBoundary.class);
		IntStream.range(0, 4)

				.mapToObj(i -> restTemplate.postForObject(url + "/iob/instances",
						new InstanceBoundary(null, "", "", false, null, testInstance.getCreatedBy(),
								new Location(Math.cos(i * Math.PI), Math.sin(i * Math.PI)), null),
						InstanceBoundary.class))
				.collect(Collectors.toList());

		IntStream.range(0, 4)
				.mapToObj(i -> restTemplate.postForObject(url + "/iob/instances",
						new InstanceBoundary(null, "", "", true, null, testInstance.getCreatedBy(),
								new Location(2 * Math.cos(i * Math.PI), 2 * Math.sin(i * Math.PI)), null),
						InstanceBoundary.class))
				.collect(Collectors.toList());

		assertThat(restTemplate.getForObject(url
				+ "/iob/instances/search/near/{lat}/{lng}/{distance}?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				InstanceBoundary[].class, 0, 0, 2.5, manager.getUserId().getDomain(), manager.getUserId().getEmail(),
				10, 0)).hasSize(9);

		assertThat(restTemplate.getForObject(url
				+ "/iob/instances/search/near/{lat}/{lng}/{distance}?userDomain={domain}&userEmail={email}&size={size}&page={page}",
				InstanceBoundary[].class, 0, 0, 2.5, player.getUserId().getDomain(), player.getUserId().getEmail(), 10,
				0)).hasSize(5);

		assertThat(restTemplate.getForObject(
				url + "/iob/instances/search/near/{lat}/{lng}/{distance}?userDomain={domain}&userEmail={email}",
				InstanceBoundary[].class, 0, 0, 1.5, manager.getUserId().getDomain(), manager.getUserId().getEmail()))
				.hasSize(5);

		assertThat(assertThrows(Unauthorized.class,
				() -> restTemplate.getForObject(
						url + "/iob/instances/search/near/{lat}/{lng}/{distance}?userDomain={domain}&userEmail={email}",
						InstanceBoundary[].class, 0, 0, 1.5, admin.getUserId().getDomain(),
						admin.getUserId().getEmail()))
				.getMessage()).contains("user must be either a manager or a player to perform this action");
	}

}
