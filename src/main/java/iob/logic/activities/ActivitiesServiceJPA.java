package iob.logic.activities;

import java.util.ArrayList;
import java.util.Collections;
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
import iob.logic.users.UserBoundary;
import iob.logic.users.UserConverter;
import iob.logic.users.UserId;
import iob.logic.utility.ConfigProperties;
import iob.mongo_repository.ActivityRepository;
import iob.mongo_repository.UserRepository;

@Service
public class ActivitiesServiceJPA implements ExtendedActivitiesService {

	private ActivityRepository activityRepository;
	private ActivityConverter activityConverter;
	private ConfigProperties configProperties;
	private UsersService userService;
	private UserRepository userRepo;
	private UserConverter userConverter;

	@Autowired
	public ActivitiesServiceJPA(ActivityRepository activityRepository, ActivityConverter activityConverter,
			ConfigProperties configProperties, UsersService userService, UserRepository userRepo, UserConverter userConverter) {
		this.activityRepository = activityRepository;
		this.activityConverter = activityConverter;
		this.configProperties = configProperties;
		this.userService = userService;
		this.userRepo = userRepo;
		this.userConverter = userConverter;
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
		
		try {
			ActivityType type = ActivityType.valueOf(activity.getType());
			switch (type) {
			case LIKE:
				UserBoundary user1 = userService.login(userId.getDomain(), userId.getEmail());
				UserBoundary user2 = getUserLikeTo(activity);
				
				List<ActivityBoundary> likeActivitiesByUser2 = activityRepository.findAllByType(activity.getType())
						.stream().map(activityConverter::toBoundary).collect(Collectors.toList());
				
				boolean match = false;
				for (ActivityBoundary act : likeActivitiesByUser2) {
					if (act.getInvokedBy().getUserId().equals(user2.getUserId())) {
						if (getUserLikeTo(act).getUserId().equals(user1.getUserId())) {
							match = true;
							break;
						}
					}
				}
				
				HashMap<String, Object> likeInfo = new HashMap<String, Object>();
				likeInfo.put(configProperties.getUserId(), user2.getUserId());
				likeInfo.put(configProperties.getMatch(), match);
				
				return likeInfo;
			
			case MATCH:
				int n = 5;
				List<UserBoundary> users = userRepo.findAll().stream() 
						.map(userConverter::toBoundary)
						.collect(Collectors.toList());
				
			    Collections.shuffle(users);
			    if (users.size() < n) {
			    	return users;
			    }
			    else {
			    	 return users.subList(0, n);
			    } 
				
			default:
				break;
			}
		} catch (IllegalArgumentException e) {
			
		}
		return activityConverter.toBoundary(entity);
	}
	
	private UserBoundary getUserLikeTo(ActivityBoundary activity) {
		HashMap<String, Object> likeTo =  (HashMap<String, Object>) activity.getActivityAttributes().get(configProperties.getlikedUser());
		HashMap<String, String> id = (HashMap<String, String>) likeTo.get(configProperties.getUserId());
		String userDomain = id.get(configProperties.getUserDomain());
		String userEmail = id.get(configProperties.getUserEmail());
		
		return userService.login(userDomain, userEmail);
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
