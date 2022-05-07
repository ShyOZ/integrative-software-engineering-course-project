package iob.mongo_repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import iob.data.InstanceEntity;

@Repository
public interface InstanceRepository extends MongoRepository<InstanceEntity, String> {
	
	public List<InstanceEntity> findAllByName(
			@Param("name") String name,
			Pageable pageable);

	public List<InstanceEntity> findAllByNameAndActive(
			@Param("name") String name, 
			@Param("active") boolean active, 
			Pageable pageable);

	public List<InstanceEntity> findAllByType(
			@Param("type") String type, 
			Pageable pageable);

	public List<InstanceEntity> findAllByTypeAndActive(
			@Param("type") String type, 
			@Param("active") boolean active,
			Pageable pageable);

	public List<InstanceEntity> findAllByActive(
			@Param("active") boolean active, 
			Pageable pageable);
	
	@Query("{location: { $near: ?0, $maxDistance: ?1 }}")
	public List<InstanceEntity> findAllNear(
			double[] location,
			double radius,
			Pageable pageable);
	
	@Query("{location: { $near: ?0, $maxDistance: ?1 }, active: ?2}")
	public List<InstanceEntity> findAllNearAndActive(
			double[] location,
			double radius,
			boolean active,
			Pageable pageable);

}
