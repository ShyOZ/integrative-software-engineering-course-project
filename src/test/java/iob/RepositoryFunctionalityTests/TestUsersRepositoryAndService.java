package iob.RepositoryFunctionalityTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import iob.data.UserEntity;
import iob.data.UserEntityId;
import iob.logic.customExceptions.EntityNotFoundException;
import iob.logic.users.NewUserBoundary;
import iob.logic.users.UserBoundary;
import iob.logic.users.UserConverter;
import iob.logic.users.UsersServiceJPA;
import iob.mongo_repository.UserRepository;
import iob.utility.TestProperties;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestUsersRepositoryAndService {

	private @LocalServerPort int port;
	private String url;
	private RestTemplate restTemplate;
	private @Autowired TestProperties testProperties;
	private @Autowired UserConverter userConverter;
	private UserBoundary admin;
	private NewUserBoundary newPlayer;
	private @Autowired UsersServiceJPA userService;
	private @Autowired UserRepository userRepository;

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
	void testLogin() {
		IntStream.range(0, 5).mapToObj(i -> {
			NewUserBoundary newUser = new NewUserBoundary("entity" + i + "@test", newPlayer.getRole(),
					newPlayer.getUsername(), newPlayer.getAvatar());
			return newUser;
		}).map(newUser -> userService.createUser(newUser)).map(user -> {
			System.err.println(user);
			return user;
		}).

				collect(Collectors.toList());

		assertThat(userService.login("test-domain", "entity" + 3 + "@test")).isNotNull();
	}

	@Test
	void testIfUserDoesNotExistThenLoginThrowsEntityNotFoundException() {
		IntStream.range(0, 5).mapToObj(i -> {
			NewUserBoundary newUser = new NewUserBoundary("entity" + i + "@test", newPlayer.getRole(),
					newPlayer.getUsername(), newPlayer.getAvatar());
			return newUser;
		}).map(newUser -> userService.createUser(newUser)).collect(Collectors.toList());

		assertThrows(NotFound.class,
				() -> restTemplate.getForObject(this.url + "/iob/users/login/{userDomain}/{userEmail}",
						UserBoundary.class, "test-domain", "entity" + 10 + "@test"));
	}

	@Test
	void testIfUserDoesNotExistThenUpdateThrowsEntityNotFoundException() {
		IntStream.range(0, 5).mapToObj(i -> {
			NewUserBoundary newUser = new NewUserBoundary("entity" + i + "@test", newPlayer.getRole(),
					newPlayer.getUsername(), newPlayer.getAvatar());
			return newUser;
		}).map(newUser -> userService.createUser(newUser)).collect(Collectors.toList());

		assertThrows(NotFound.class,
				() -> userService.updateUser("test-domain", "entity" + 10 + "@test", new UserBoundary()));
	}

	@Test
	void testFindAllByVersion() {
		IntStream.range(0, 5).mapToObj(i -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setUserId(new UserEntityId("entity" + i + "@test", "test-domain"));
			userEntity.setVersion(1);
			return userEntity;
		}).map(userEntity -> userRepository.save(userEntity)).collect(Collectors.toList());

		IntStream.range(5, 10).mapToObj(i -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setUserId(new UserEntityId("entity" + i + "@test", "test-domain"));
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
			userEntity.setUserId(new UserEntityId("entity" + i + "@test", "test-domain"));
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
			userEntity.setUserId(new UserEntityId("entity" + i + "@test", "test-domain"));
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
				() -> this.restTemplate.postForObject(this.url + "/iob/users", playerWithoutEmail, UserBoundary.class))
				.getMessage()).contains("email is missing");
	}

	@Test
	void ifUserIsMissingRoleThenPostThrowsBadRequestWithRoleIsMissingMessage() {
		NewUserBoundary playerWithoutRole = new NewUserBoundary(newPlayer.getEmail(), null, newPlayer.getUsername(),
				newPlayer.getAvatar());

		assertThat(assertThrows(BadRequest.class,
				() -> this.restTemplate.postForObject(this.url + "/iob/users", playerWithoutRole, UserBoundary.class))
				.getMessage()).contains("role is missing");
	}

	@Test
	void ifUserIsMissingUsernameThenPostThrowsBadRequestWithUsernameIsMissingMessage() {
		NewUserBoundary playerWithoutUsername = new NewUserBoundary(newPlayer.getEmail(), newPlayer.getRole(), null,
				newPlayer.getAvatar());

		assertThat(assertThrows(BadRequest.class, () -> this.restTemplate.postForObject(this.url + "/iob/users",
				playerWithoutUsername, UserBoundary.class)).getMessage()).contains("username is missing");
	}

	@Test
	void ifUserIsMissingAvatarThenPostThrowsBadRequestWithAvatarIsMissingMessage() {
		NewUserBoundary playerWithoutAvatar = new NewUserBoundary(newPlayer.getEmail(), newPlayer.getRole(),
				newPlayer.getUsername(), null);

		assertThat(assertThrows(BadRequest.class,
				() -> this.restTemplate.postForObject(this.url + "/iob/users", playerWithoutAvatar, UserBoundary.class))
				.getMessage()).contains("avatar is missing");
	}
}
