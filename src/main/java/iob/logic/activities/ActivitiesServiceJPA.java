package iob.logic.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.ActivityEntity;
import iob.data.UserRole;
import iob.logic.ExtendedActivitiesService;
import iob.logic.UsersService;
import iob.logic.customExceptions.BadRequestException;
import iob.logic.customExceptions.UnauthorizedRequestException;
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
	@Transactional
	public Object invokeActivity(ActivityBoundary activity, String domain, String email) {
		ActivityEntity entity = new ActivityEntity();
		UserRole userRole = getUserRoleById(domain, email);
		
		if (userRole.equals(UserRole.PLAYER))
		{
			entity.setCreatedTimestamp(new Date());
			entity.setactivityId(new ActivityId(configProperties.getApplicationDomain(), UUID.randomUUID().toString()));
	
			if (activity.getType() != null)
				entity.setType(activity.getType());
			else
				throw new BadRequestException("type is missing");
	
			if (activity.getInstance() != null)
				entity.setInstance(this.activityConverter.toEntity(activity.getInstance()));
			else
				throw new BadRequestException("instance is missing");
	
			if (activity.getInvokedBy() != null)
				entity.setInvokedBy(this.activityConverter.toEntity(activity.getInvokedBy()));
			else
				throw new BadRequestException("invoked by is missing");
	
			if (activity.getActivityAttributes() != null)
				entity.setAttributes(this.activityConverter.toEntity(activity.getActivityAttributes()));
			else
				throw new BadRequestException("attributes is missing");
	
			return this.activityRepository.save(entity);
		}
		else
			throw new UnauthorizedRequestException("User must be a player to perform the action.");

	}

	@Override
	@Transactional(readOnly = true)
	public List<ActivityBoundary> getAllActivities() {
		Iterable<ActivityEntity> iter = this.activityRepository.findAll();
		List<ActivityBoundary> rv = new ArrayList<>();
		for (ActivityEntity activity : iter) {
			rv.add(this.activityConverter.toBoundary(activity));
		}
		return rv;
	}

	@Override
	@Transactional
	public void deleteAllActivities() {
		this.activityRepository.deleteAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ActivityBoundary> getAllActivities(int size, int page, String domain, String email) {
		UserRole userRole = getUserRoleById(domain, email);
		
		if (userRole.equals(UserRole.ADMIN)) {
			return this.activityRepository.findAll(PageRequest.of(page, size, Direction.ASC, "activityId")).stream()
					.map(this.activityConverter::toBoundary)
					.collect(Collectors.toList());
		}
		else {
			throw new UnauthorizedRequestException("User must be an admin to perform the action.");
		}
	}
	

	@Override
	@Transactional(readOnly = true)
	public List<ActivityBoundary> getActivitiesByVersion(int version, int size, int page) {
		return this.activityRepository.findAllByVersion(version, PageRequest.of(page, size, Direction.ASC, "activityId")).stream()
				.map(this.activityConverter::toBoundary).collect(Collectors.toList());
	}
}
