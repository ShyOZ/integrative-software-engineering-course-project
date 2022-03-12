package backend.controllers;

import java.nio.charset.*; // for generating random instance ID
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import backend.DuetApplication;
import backend.boundaries.InstanceBoundary;
import backend.instances.Match;
import backend.instances.UserId;

@RestController
public class MatchApiController {
	
	@RequestMapping(
		method = RequestMethod.GET,
		path= DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER + "match",
		produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] getAllMatches ()
			 {
		
		Map<String, String> instanceId = new TreeMap<String, String>();
		instanceId.put("Domain", DuetApplication.DOMAIN);
		instanceId.put("ID", "42");
		
		Map<String, Double> location = new TreeMap<String, Double>();
		location.put("lat", 1.34343);
		location.put("lng", 45.7453);
		
		int[] test = {34,65};
		Match match = new Match(new Date(),test);
		
		Map<String, UserId> userMap = new TreeMap<String, UserId>();
		userMap.put("UserId" , new UserId("sorany123@gmail.com"));
		
		InstanceBoundary instace = new InstanceBoundary(
				instanceId, "Match", "match_name", new Boolean(true),new Date(), userMap, location, match.getInstanceAttributes());
		
		
		InstanceBoundary[] instanceboundry = {instace};
		return instanceboundry;
	}
}