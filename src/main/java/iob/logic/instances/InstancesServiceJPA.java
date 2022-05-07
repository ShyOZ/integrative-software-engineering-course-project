package iob.logic.instances;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.InstanceEntity;
import iob.data.UserRole;
import iob.logic.ExtendedInstancesService;
import iob.logic.UnauthorizedUserException;
import iob.logic.users.UserId;
import iob.logic.utility.ConfigProperties;
import iob.mongo_repository.InstanceRepository;

@Service
public class InstancesServiceJPA implements ExtendedInstancesService {
	private InstanceRepository instanceRepository;
	private InstanceConverter instanceConverter;
	private ConfigProperties configProperties;

	@Autowired
	public InstancesServiceJPA(InstanceRepository instanceRepository, InstanceConverter instanceConverter,
			ConfigProperties configProperties) {
		this.instanceRepository = instanceRepository;
		this.instanceConverter = instanceConverter;
		this.configProperties = configProperties;
	}

	@Override
	@Transactional
	public InstanceBoundary createInstance(InstanceBoundary instance) {
		instance.setCreatedTimestamp(new Date());

		Map<String, String> instanceIdMap = new HashMap<String, String>();
		instanceIdMap.put("domain", configProperties.getApplicationDomain());
		instanceIdMap.put("id", UUID.randomUUID().toString());
		instance.setInstanceId(instanceIdMap);

		if (instance.getActive() == null) {
			instance.setActive(true);
		}

		InstanceEntity entity = instanceConverter.toEntity(instance);
		entity = instanceRepository.save(entity);
		return instanceConverter.toBoundary(entity);
	}

	@Override
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update) {
		throw new RuntimeException("deprecated method - use updateInstance with user info instead");
	}

	private InstanceEntity getInstanceEntityById(String instanceDomain, String instanceId) {
		Optional<InstanceEntity> op = instanceRepository
				.findById(instanceConverter.toEntity(instanceDomain, instanceId));

		if (op.isPresent()) {
			InstanceEntity entity = op.get();
			return entity;
		} else {
			throw new InstanceNotFoundException("could not find instance by id: " + instanceDomain + ", " + instanceId);
		}
	}

	private UserRole getUserRoleById(String userDomain, String userId) {
		// UserBoundary user = userService.login(userDomain, userId);
		// return user.getRole();

		return UserRole.MANAGER;
	}

	@Override
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId) {
		throw new RuntimeException("deprecated method - use getSpecificInstance with user info instead");
	}

	@Override
	public List<InstanceBoundary> getAllInstances() {
		throw new RuntimeException("deprecated method - use getAllInstances with user info instead");
	}

	@Override
	@Transactional
	public void deleteAllInstances() {
		throw new RuntimeException("deprecated method - use deleteAllInstances with user info instead");
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> getInstancesByName(String name, String userDomain, String userEmail, int size,
			int page) {
		UserRole userRole = getUserRoleById(userDomain, userEmail);

		switch (userRole) {
		case MANAGER:
			return instanceRepository.findAllByName(name, PageRequest.of(page, size, Direction.ASC, "name")).stream()
					.map(this.instanceConverter::toBoundary).collect(Collectors.toList());
		case PLAYER:
			return instanceRepository
					.findAllByNameAndActive(name, true, PageRequest.of(page, size, Direction.ASC, "name")).stream()
					.map(this.instanceConverter::toBoundary).collect(Collectors.toList());
		default:
			throw new UnauthorizedUserException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> getInstancesByType(String type, String userDomain, String userEmail, int size,
			int page) {
		UserRole userRole = getUserRoleById(userDomain, userEmail);

		switch (userRole) {
		case MANAGER:
			return instanceRepository.findAllByType(type, PageRequest.of(page, size, Direction.DESC, "type")).stream()
					.map(this.instanceConverter::toBoundary).collect(Collectors.toList());
		case PLAYER:
			return instanceRepository
					.findAllByTypeAndActive(type, true, PageRequest.of(page, size, Direction.DESC, "type")).stream()
					.map(this.instanceConverter::toBoundary).collect(Collectors.toList());
		default:
			throw new UnauthorizedUserException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> getInstancesNear(double lat, double lng, double distance, String userDomain,
			String userEmail, int size, int page) {
		UserRole userRole = getUserRoleById(userDomain, userEmail);
		double[] location = new double[] { lat, lng };

		switch (userRole) {
		case MANAGER:
			return instanceRepository
					.findAllNear(location, distance, PageRequest.of(page, size, Direction.ASC, "instanceId")).stream()
					.map(this.instanceConverter::toBoundary).collect(Collectors.toList());
		case PLAYER:
			return instanceRepository
					.findAllNearAndActive(location, distance, true,
							PageRequest.of(page, size, Direction.ASC, "instanceId"))
					.stream().map(this.instanceConverter::toBoundary).collect(Collectors.toList());
		default:
			throw new UnauthorizedUserException();
		}

	}

	@Override
	@Transactional
	public InstanceBoundary updateInstance(String instanceDomain, String instanceId, InstanceBoundary update,
			String userDomain, String userEmail) {
		UserRole userRole = getUserRoleById(userDomain, userEmail);

		if (userRole == UserRole.MANAGER) {
			InstanceEntity entity = getInstanceEntityById(instanceDomain, instanceId);

			if (update.getInstanceId() != null) {
				// do nothing
			}

			if (update.getType() != null) {
				entity.setType(update.getType());
			}

			if (update.getName() != null) {
				entity.setName(update.getName());
			}

			if (update.getActive() != null) {
				entity.setActive(update.getActive());
			}

			if (update.getCreatedTimestamp() != null) {
				// do nothing
			}

			Map<String, UserId> createdBy = update.getCreatedBy();
			if (update.getCreatedBy() != null) {
				UserId userId = createdBy.get("userId");
				if (userId != null) {
					String domain = userId.getDomain();
					if (domain != null) {
						entity.setCreatedByDomain(domain);
					}

					String email = userId.getEmail();
					if (email != null) {
						entity.setCreatedByEmail(email);
					}
				}
			}

			if (update.getLocation() != null) {
				entity.setLocation(instanceConverter.toEntity(update.getLocation()));
			}

			if (update.getInstanceAttributes() != null) {
				entity.setAttributes(update.getInstanceAttributes());
			}

			entity = instanceRepository.save(entity);
			return instanceConverter.toBoundary(entity);

		} else {
			throw new UnauthorizedUserException();
		}

	}

	@Override
	@Transactional(readOnly = true)
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId, String userDomain,
			String userEmail) {
		UserRole userRole = getUserRoleById(userDomain, userEmail);

		InstanceBoundary instance = instanceConverter.toBoundary(getInstanceEntityById(instanceDomain, instanceId));

		switch (userRole) {
		case MANAGER:
			return instance;

		case PLAYER:
			if (instance.getActive() == true) {
				return instance;
			} else {
				throw new InstanceNotFoundException();
			}

		default:
			throw new UnauthorizedUserException();
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<InstanceBoundary> getAllInstances(String userDomain, String userEmail, int size, int page) {
		UserRole userRole = getUserRoleById(userDomain, userEmail);

		switch (userRole) {
		case MANAGER:
			return instanceRepository.findAll(PageRequest.of(page, size, Direction.ASC, "instanceId")).stream()
					.map(instanceConverter::toBoundary).collect(Collectors.toList());
		case PLAYER:
			return instanceRepository.findAllByActive(true, PageRequest.of(page, size, Direction.ASC, "instanceId"))
					.stream().map(this.instanceConverter::toBoundary).collect(Collectors.toList());
		default:
			throw new UnauthorizedUserException();
		}

	}

	@Override
	@Transactional
	public void deleteAllInstances(String userDomain, String userEmail) {
		UserRole userRole = getUserRoleById(userDomain, userEmail);

		if (userRole == UserRole.ADMIN) {
			instanceRepository.deleteAll();
		} else {
			throw new UnauthorizedUserException();
		}

	}

}
