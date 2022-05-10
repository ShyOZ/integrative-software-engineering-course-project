package iob.FunctionalityTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.RestTemplate;

import iob.data.UserEntity;
import iob.data.UserEntityId;
import iob.data.UserRole;
import iob.logic.users.NewUserBoundary;
import iob.logic.users.UserBoundary;
import iob.logic.users.UserConverter;
import iob.logic.users.UserId;
import iob.mongo_repository.UserRepository;
import iob.utility.TestProperties;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestUserFunctionality {

	private @LocalServerPort int port;
	private String url;
	private RestTemplate restTemplate;
	private @Autowired TestProperties testProperties;
	private @Autowired UserConverter userConverter;
	private UserBoundary admin, player;
	private NewUserBoundary newPlayer;
	private @Autowired UserRepository userRepository;

	@PostConstruct
	public void init() {
		newPlayer = testProperties.getNewPlayer();
		restTemplate = new RestTemplate();
		url = "http://localhost:" + port;
		}

	@BeforeEach
	void setUp() {
		admin = restTemplate.postForObject(url + "/iob/users", testProperties.getNewAdmin(), UserBoundary.class);
		player = restTemplate.postForObject(url + "/iob/users", testProperties.getNewPlayer(), UserBoundary.class);
	}

	@AfterEach
	void tearDown() {
		restTemplate.delete(url + "/iob/admin/users?userDomain={domain}&userEmail={email}",
				admin.getUserId().getDomain(), admin.getUserId().getEmail());
	}

	@Test
	void testLogin() {
		IntStream.range(0, 5).mapToObj(i -> {
			NewUserBoundary newUser = new NewUserBoundary("entity" + i + "@test", newPlayer.getRole(),
					newPlayer.getUsername(), newPlayer.getAvatar());
			return newUser;
		}).map(newUser -> restTemplate.postForObject(url + "/iob/users", newUser, UserBoundary.class))
				.collect(Collectors.toList());
		UserBoundary rv = restTemplate.getForObject(url + "/iob/users/login/{userDomain}/{userEmail}",
				UserBoundary.class, "test-domain", "entity" + 3 + "@test");
		UserBoundary expected = new UserBoundary(new UserId("entity" + 3 + "@test", "test-domain"), player.getRole(),
				player.getUsername(), player.getAvatar());
		assertThat(rv).isNotNull().usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void ifUserDoesNotExist_thenLoginThrowsNotFoundWithCouldNotFindUserMessage() {
		IntStream.range(0, 5).mapToObj(i -> {
			NewUserBoundary newUser = new NewUserBoundary("entity" + i + "@test", newPlayer.getRole(),
					newPlayer.getUsername(), newPlayer.getAvatar());
			return newUser;
		}).map(newUser -> restTemplate.postForObject(url + "/iob/users", newUser, UserBoundary.class))
				.collect(Collectors.toList());

		assertThat(
				assertThrows(NotFound.class,
						() -> restTemplate.getForObject(url + "/iob/users/login/{userDomain}/{userEmail}",
								UserBoundary.class, "test-domain", "entity" + 10 + "@test"))
						.getMessage())
				.contains("could not find user");
	}

	@Test
	void ifUserDoesNotExist_thenUpdateThrowsNotFoundWithCouldNotFindUserMessage() {
		IntStream.range(0, 5).mapToObj(i -> {
			NewUserBoundary newUser = new NewUserBoundary("entity" + i + "@test", newPlayer.getRole(),
					newPlayer.getUsername(), newPlayer.getAvatar());
			return newUser;
		}).map(newUser -> restTemplate.postForObject(url + "/iob/users", newUser, UserBoundary.class))
				.collect(Collectors.toList());

		assertThat(assertThrows(NotFound.class, () -> restTemplate.put(url + "/iob/users/{userDomain}/{userEmail}",
				new UserBoundary(), "test-domain", "entity" + 10 + "@test")).getMessage())
				.contains("could not find user");
	}

	void testUpdateUser() {
		restTemplate.postForObject(url + "/iob/users", newPlayer, UserBoundary.class);

		UserBoundary updatedPlayer = new UserBoundary(player.getUserId(), UserRole.MANAGER,
				"updated " + player.getUsername(), "updated " + player.getAvatar());

		restTemplate.put(url + "iob/users/{userDomain}/{userEmail}", updatedPlayer, player.getUserId().getDomain(),
				player.getUserId().getEmail());

		assertThat(userRepository.findByDomainAndEmail(player.getUserId().getDomain(), player.getUserId().getEmail()))
				.isPresent().get().usingRecursiveComparison().isEqualTo(userConverter.toEntity(updatedPlayer));
	}

	@Test
	void testGetAllUsers() {
		IntStream.range(0, 5)
				.mapToObj(i -> new NewUserBoundary("entity" + i + "@test", newPlayer.getRole(),
						newPlayer.getUsername() + i, newPlayer.getAvatar() + i))
				.map(user -> restTemplate.postForObject(url + "/iob/users", user, UserBoundary.class))
				.collect(Collectors.toList());

		UserBoundary[] actual = restTemplate.getForObject(
				url + "/iob/admin/users?userDomain={userDomain}&userEmail={userEmail}&page={page}&size={size}",
				UserBoundary[].class, admin.getUserId().getDomain(), admin.getUserId().getEmail(), 0, 10);
		assertThat(actual).hasSize(5 + 2); // 5 inserted users + admin + player
	}

	@Test
	void testFindAllByVersion() {
		IntStream.range(0, 5).mapToObj(i -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setDomain("test-domain");
			userEntity.setEmail("entity" + i + "@test");
			userEntity.setVersion(1);
			return userEntity;
		}).map(userEntity -> userRepository.save(userEntity)).collect(Collectors.toList());

		IntStream.range(5, 10).mapToObj(i -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setDomain("test-domain");
			userEntity.setEmail("entity" + i + "@test");
			userEntity.setVersion(2);
			return userEntity;
		}).map(userEntity -> userRepository.save(userEntity)).collect(Collectors.toList());

		List<UserEntity> actual = userRepository.findAllByVersion(1, PageRequest.of(0, 20, Direction.ASC, "userId"));

		assertThat(actual).hasSize(5);
	}

	@Test
	void testFindAllByVersionGreaterThan() {
		IntStream.range(0, 10).mapToObj(i -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setDomain("test-domain");
			userEntity.setEmail("entity" + i + "@test");
			userEntity.setVersion(i);
			return userEntity;
		}).map(userEntity -> userRepository.save(userEntity)).collect(Collectors.toList());

		List<UserEntity> actual = userRepository.findAllByVersionGreaterThan(4,
				PageRequest.of(0, 20, Direction.ASC, "userId"));

		assertThat(actual).hasSize(5);
	}

	@Test
	void testFindAllByVersionBetween() {
		IntStream.range(0, 10).mapToObj(i -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setDomain("test-domain");
			userEntity.setEmail("entity" + i + "@test");
			userEntity.setVersion(i);
			return userEntity;
		}).map(userEntity -> userRepository.save(userEntity)).collect(Collectors.toList());

		List<UserBoundary> actual = userRepository
				.findAllByVersionBetween(2, 8, PageRequest.of(0, 20, Direction.ASC, "userId")).stream()
				.map(userConverter::toBoundary).collect(Collectors.toList());
		assertThat(actual).hasSize(5);
	}

	@Test
	void ifUserIsMissingEmailThenPostThrowsBadRequestWithEmailIsMissingMessage() {
		NewUserBoundary playerWithoutEmail = new NewUserBoundary(null, newPlayer.getRole(), newPlayer.getUsername(),
				newPlayer.getAvatar());

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/users", playerWithoutEmail, UserBoundary.class))
				.getMessage()).contains("email is missing");
	}

	@Test
	void ifUserIsMissingRoleThenPostThrowsBadRequestWithRoleIsMissingMessage() {
		NewUserBoundary playerWithoutRole = new NewUserBoundary(newPlayer.getEmail(), null, newPlayer.getUsername(),
				newPlayer.getAvatar());

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/users", playerWithoutRole, UserBoundary.class))
				.getMessage()).contains("role is missing");
	}

	@Test
	void ifUserIsMissingUsernameThenPostThrowsBadRequestWithUsernameIsMissingMessage() {
		NewUserBoundary playerWithoutUsername = new NewUserBoundary(newPlayer.getEmail(), newPlayer.getRole(), null,
				newPlayer.getAvatar());

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/users", playerWithoutUsername, UserBoundary.class))
				.getMessage()).contains("username is missing");
	}

	@Test
	void ifUserIsMissingAvatarThenPostThrowsBadRequestWithAvatarIsMissingMessage() {
		NewUserBoundary playerWithoutAvatar = new NewUserBoundary(newPlayer.getEmail(), newPlayer.getRole(),
				newPlayer.getUsername(), null);

		assertThat(assertThrows(BadRequest.class,
				() -> restTemplate.postForObject(url + "/iob/users", playerWithoutAvatar, UserBoundary.class))
				.getMessage()).contains("avatar is missing");
	}

}
