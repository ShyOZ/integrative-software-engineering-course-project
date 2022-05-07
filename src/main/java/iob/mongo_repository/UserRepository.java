package iob.mongo_repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iob.data.UserEntity;
import iob.data.UserEntityId;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, UserEntityId> {

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
