package iob.logic.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import iob.data.ActivityEntity;
import iob.data.UserRole;
import iob.log.LogMethod;
import iob.logic.ExtendedActivitiesService;
import iob.logic.UsersService;
import iob.logic.customExceptions.BadRequestException;
import iob.logic.customExceptions.UnauthorizedRequestException;
import iob.logic.instances.InstanceId;
import iob.logic.users.UserId;
import iob.logic.utility.ConfigProperties;
import iob.mongo_repository.ActivityRepository;

@Service
public class ActivitiesServiceJPA implements ExtendedActivitiesService {

	private ActivityRepository activityRepository;
	private ActivityConverter activityConverter;
	private ConfigProperties configProperties;
	private UsersService userService;

	@Autowired
	public ActivitiesServiceJPA(ActivityRepository activityRepository, ActivityConverter activityConverter,
			ConfigProperties configProperties, UsersService userService) {
		this.activityRepository = activityRepository;
		this.activityConverter = activityConverter;
		this.configProperties = configProperties;
		this.userService = userService;
	}

	private UserRole getUserRoleById(String userDomain, String userId) {
		return userService.login(userDomain, userId).getRole();
	}

	@Override
	@LogMethod
	public Object invokeActivity(ActivityBoundary activity) {
		if (activity.getType() == null)
			throw new BadRequestException("type is missing");

		if (activity.getInstance() == null)
			throw new BadRequestException("instance is missing");

		InstanceId instanceId = activity.getInstance().getInstanceId();

		if (instanceId == null)
			throw new BadRequestException("instance.instanceId is missing");

		if (instanceId.getDomain() == null)
			throw new BadRequestException("instance.instanceId.domain is missing");

		if (instanceId.getId() == null)
			throw new BadRequestException("instance.instanceId.id is missing");

		if (activity.getInvokedBy() == null)
			throw new BadRequestException("invokedBy is missing");

		UserId userId = activity.getInvokedBy().getUserId();

		if (userId == null)
			throw new BadRequestException("invokedBy.userId is missing");

		if (userId.getEmail() == null)
			throw new BadRequestException("invokedBy.userId.email is missing");

		if (userId.getDomain() == null)
			throw new BadRequestException("invokedBy.userId.domain is missing");

		UserRole userRole = getUserRoleById(userId.getDomain(), userId.getEmail());

		if (userRole != UserRole.PLAYER)
			throw new UnauthorizedRequestException("User must be a player to perform the action");

		if (activity.getActivityAttributes() == null)
			activity.setActivityAttributes(new HashMap<String, Object>());

		activity.setCreatedTimestamp(new Date());

		activity.setActivityId(new ActivityId(configProperties.getApplicationDomain(), UUID.randomUUID().toString()));

		ActivityEntity entity = activityConverter.toEntity(activity);
		entity = activityRepository.save(entity);
		return activityConverter.toBoundary(entity);
	}

	@Override
	@LogMethod
	public List<ActivityBoundary> getAllActivities() {
		Iterable<ActivityEntity> iter = this.activityRepository.findAll();
		List<ActivityBoundary> rv = new ArrayList<>();
		for (ActivityEntity activity : iter) {
			rv.add(this.activityConverter.toBoundary(activity));
		}
		return rv;
	}

	@Override
	@LogMethod
	public void deleteAllActivities() {
		throw new RuntimeException("deprecated method - use deleteAllActivities with user info instead");
	}

	@Override
	public void deleteAllActivities(String userDomain, String userEmail) {
		UserRole userRole = getUserRoleById(userDomain, userEmail);

		if (userRole != UserRole.ADMIN)
			throw new UnauthorizedRequestException("user must be an admin to perform this action");

		activityRepository.deleteAll();
	}

	@Override
	@LogMethod
	public List<ActivityBoundary> getAllActivities(int size, int page, String domain, String email) {
		UserRole userRole = getUserRoleById(domain, email);

		if (userRole.equals(UserRole.ADMIN)) {
			return this.activityRepository.findAll(PageRequest.of(page, size, Direction.ASC, "activityId")).stream()
					.map(this.activityConverter::toBoundary).collect(Collectors.toList());
		} else {
			throw new UnauthorizedRequestException("user must be an admin to perform this action");
		}
	}
}
