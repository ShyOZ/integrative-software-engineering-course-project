package iob.mongo_repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iob.data.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
	
	@Query("{version: ?0}")
	Optional<UserEntity> findUserByVersion(int version);
	
	@Query("{_id: ?0}")
	Optional<UserEntity> findById(String id);

	public List<UserEntity> findAllByVersion(
		@Param("version") int version, 
		Pageable pageable);

	public List<UserEntity> findAllByVersionGreaterThan(
			@Param("minVersion") int minVersion, 
			Pageable pageable);
	
	
	public List<UserEntity> findAllByVersionBetween(
			@Param("minVersion") int minVersion, 
			@Param("maxVersion") int maxVersion, 
			Pageable pageable);

}
