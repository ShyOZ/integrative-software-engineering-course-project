package iob.logic.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.data.ActivityEntity;
import iob.logic.ActivitiesService;

@Service
public class ActivitiesServiceJPA implements ActivitiesService {
	private String configurableActivity;
	private ActivitiesCrud activityCrud;
	private ActivityConverter activityConverter;
	
	@Autowired
	public ActivitiesServiceJPA(ActivitiesCrud activityCrud, ActivityConverter activityConverter) {
		this.activityConverter = activityConverter;
		this.activityCrud = activityCrud;
	}
	
	@Value("${configurable.activity.text:Default activity}")
	public void setConfigurableActivity(String configurableActivity) {
		this.configurableActivity = configurableActivity;
	}
	
	@PostConstruct
	public void init() {
		System.err.println("configurableActivity: " + configurableActivity);
	}
	
	@Override
	public Object invokeActivity(ActivityBoundary activity) {
		activity.setCreatedTimestamp(new Date());
		activity.setActivityId(new ActivityId("2022b.Yaeli.Bar.Gimelshtei", UUID.randomUUID().toString()));
		ActivityEntity entity = this.activityCrud.save(this.activityConverter.toEntity(activity));
		return activityCrud.save(entity);
	}

	@Override
	public List<ActivityBoundary> getAllActivities() {
		Iterable<ActivityEntity> iter = this.activityCrud.findAll();
		List<ActivityBoundary> rv = new ArrayList<>();
		for (ActivityEntity act : iter) {
			rv.add(this.activityConverter.toBoundary(act));
		}
		return rv;
	}

	@Override
	@Transactional
	public void deleteAllActivities() {
		this.activityCrud.deleteAll();
	}
}
