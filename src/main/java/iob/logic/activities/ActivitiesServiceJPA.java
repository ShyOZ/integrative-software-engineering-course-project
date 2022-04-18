package iob.logic.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.ActivityEntity;
import iob.logic.ActivitiesService;
import iob.logic.utility.ConfigProperties;

@Service
public class ActivitiesServiceJPA implements ActivitiesService {

	private ActivityBoundary defaultActivityBoundary;
	private ActivitiesCrud activityCrud;
	private ActivityConverter activityConverter;
	private ConfigProperties configProperties;

	@Autowired
	public ActivitiesServiceJPA(ActivitiesCrud activityCrud, ActivityConverter activityConverter,
			ConfigProperties configProperties) {
		this.activityCrud = activityCrud;
		this.activityConverter = activityConverter;
		this.configProperties=configProperties;
	}

	@PostConstruct
	public void init() {
		this.defaultActivityBoundary = configProperties.defaultActivityBoundary();
		System.err.println("Default Activity Boundary: " + this.defaultActivityBoundary);
	}

	@Override
	@Transactional
	public Object invokeActivity(ActivityBoundary activity) {
		activity.setCreatedTimestamp(new Date());
		activity.setActivityId(
				new ActivityId(
						this.defaultActivityBoundary.getActivityId().getDomain(), 
						UUID.randomUUID().toString()));
		ActivityEntity entity = this.activityCrud.save(this.activityConverter.toEntity(activity));
		return this.activityConverter.toBoundary(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ActivityBoundary> getAllActivities() {
		Iterable<ActivityEntity> iter = this.activityCrud.findAll();
		List<ActivityBoundary> rv = new ArrayList<>();
		for (ActivityEntity activity : iter) {
			rv.add(this.activityConverter.toBoundary(activity));
		}
		return rv;
	}

	@Override
	@Transactional
	public void deleteAllActivities() {
		this.activityCrud.deleteAll();
	}
}
