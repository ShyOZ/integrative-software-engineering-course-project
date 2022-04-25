package iob.logic.users;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import iob.data.UserEntity;


//TODO: Replace String with the proper ID object Type.
public interface UsersCrud {//extends PagingAndSortingRepository<UserEntity, String> {

//	public List<UserEntity> findAllByVersion(
//			@Param("version") int version, 
//			Pageable pageable);
//
//	public List<UserEntity> findAllByVersionGreaterThan(
//			@Param("minVersion") int minVersion, 
//			Pageable pageable);
//	
//	
//	public List<UserEntity> findAllByVersionBetween(
//			@Param("minVersion") int minVersion, 
//			@Param("maxVersion") int maxVersion, 
//			Pageable pageable);
}
