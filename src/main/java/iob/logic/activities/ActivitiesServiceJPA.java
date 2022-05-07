package iob.logic.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
		this.configProperties = configProperties;
	}

	@Override
	@Transactional
	public Object invokeActivity(ActivityBoundary activity) {
		ActivityEntity entity = new ActivityEntity();

		entity.setCreatedTimestamp(new Date());
		entity.setactivityId(this.activityConverter
				.toEntity(new ActivityId(configProperties.getApplicationDomain(), UUID.randomUUID().toString())));

		if (activity.getType() != null)
			entity.setType(activity.getType());
		else
			entity.setType(this.defaultActivityBoundary.getType());

		if (activity.getInstance() != null)
			entity.setInstance(this.activityConverter.toEntity(activity.getInstance()));
		else
			entity.setInstance(this.activityConverter.toEntity(this.defaultActivityBoundary.getInstance()));

		if (activity.getInvokedBy() != null)
			entity.setInvokedBy(this.activityConverter.toEntity(activity.getInvokedBy()));
		else
			entity.setInvokedBy(this.activityConverter.toEntity(this.defaultActivityBoundary.getInvokedBy()));

		if (activity.getActivityAttributes() != null)
			entity.setAttributes(this.activityConverter.toEntity(activity.getActivityAttributes()));
		else
			entity.setAttributes(this.activityConverter.toEntity(this.defaultActivityBoundary.getActivityAttributes()));

		return this.activityCrud.save(entity);
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
