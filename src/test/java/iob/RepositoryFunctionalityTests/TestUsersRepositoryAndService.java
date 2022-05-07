package iob.RepositoryFunctionalityTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import iob.data.UserEntity;
import iob.data.UserEntityId;
import iob.mongo_repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestUsersRepositoryAndService {

	private @Autowired UserRepository userRepository;

	@BeforeEach
	void setUp() {
		userRepository.deleteAll();
	}

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void testFindById() {
		IntStream.range(0, 5).mapToObj(i -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setUserId(new UserEntityId("entity" + i + "@demo", "demo-domain"));
			userEntity.setVersion(1);
			return userEntity;
		}).map(userEntity -> userRepository.save(userEntity)).collect(Collectors.toList());
		
		
		assertThat(userRepository.findById(new UserEntityId("entity" + 3 + "@demo", "demo-domain"))).isNotEmpty();
		
	}

	@Test
	void testFindAllByVersion() {
		IntStream.range(0, 5).mapToObj(i -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setUserId(new UserEntityId("entity" + i + "@demo", "demo-domain"));
			userEntity.setVersion(1);
			return userEntity;
		}).map(userEntity -> userRepository.save(userEntity)).collect(Collectors.toList());

		IntStream.range(5, 10).mapToObj(i -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setUserId(new UserEntityId("entity" + i + "@demo", "demo-domain"));
			userEntity.setVersion(2);
			return userEntity;
		}).map(userEntity -> userRepository.save(userEntity)).collect(Collectors.toList());

		List<UserEntity> actual = userRepository.findAllByVersion(1, PageRequest.of(0, 20, Direction.ASC, "userId"));

		assertThat(actual).hasSize(5);
	}

	@Test
	void findAllByVersionGreaterThan() {
		IntStream.range(0, 10).mapToObj(i -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setUserId(new UserEntityId("entity" + i + "@demo", "demo-domain"));
			userEntity.setVersion(i);
			return userEntity;
		}).map(userEntity -> userRepository.save(userEntity)).collect(Collectors.toList());

		List<UserEntity> actual = userRepository.findAllByVersionGreaterThan(4,
				PageRequest.of(0, 20, Direction.ASC, "userId"));

		assertThat(actual).hasSize(5);
	}

	void findAllByVersionBetween() {
		IntStream.range(0, 10).mapToObj(i -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setUserId(new UserEntityId("entity" + i + "@demo", "demo-domain"));
			userEntity.setVersion(i);
			return userEntity;
		}).map(userEntity -> userRepository.save(userEntity)).collect(Collectors.toList());

		List<UserEntity> actual = userRepository.findAllByVersionBetween(3, 8,
				PageRequest.of(0, 20, Direction.ASC, "userId"));

		assertThat(actual).hasSize(5);
	}
}
