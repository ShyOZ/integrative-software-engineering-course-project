package backend.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import backend.DuetApplication;
import backend.boundaries.InstanceBoundary;
import backend.instances.MusicalTaste;
import backend.instances.UserId;

@RestController
public class MusicalTasteApiController {
	
	@RequestMapping(
			method = RequestMethod.POST,
			path = DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER +"/musicalTaste",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary createInstance(@RequestBody InstanceBoundary boundary) {
		// MOCKUP implementation
		boundary.setCreatedTimeStamp(new Date());
		return boundary;
	}
	
	@RequestMapping(
			method = RequestMethod.PUT,
			path = DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER + "/musicalTaste/{instanceId}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateInstance(@PathVariable("instanceId") String instanceId,
			@RequestBody InstanceBoundary boundary) {
		// MOCKUP implementation
	}
	
	@RequestMapping(
		method = RequestMethod.GET,
		path= DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER + "/musicalTaste",
		produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] getAllMusicalTastes () {
		
		Map<String, String> instanceIdMap = new TreeMap<String, String>();
		instanceIdMap.put("Domain", DuetApplication.DOMAIN);
		instanceIdMap.put("ID", "42");
		
		Map<String, Double> location = new TreeMap<String, Double>();
		location.put("lat", 1.34343);
		location.put("lng", 45.7453);
		
		ArrayList<String> topArtists = new ArrayList<>();
		Collections.addAll(topArtists,"Eminem", "Soja", "2Pac", "Coldplay");
		ArrayList<String> topTracks = new ArrayList<>();
		Collections.addAll(topTracks,"Track1", "Track2", "Track3", "Track4");
		ArrayList<String> topPodcasts = new ArrayList<>();
		Collections.addAll(topPodcasts,"Podcast1", "Podcast2", "Podcast3");
		ArrayList<String> topGenres = new ArrayList<>();
		Collections.addAll(topPodcasts,"Genere1", "Genere2", "Genere3");
		MusicalTaste musicalTaste = new MusicalTaste(topArtists, topTracks, topPodcasts, topGenres);
		
		Map<String, UserId> userMap = new TreeMap<String, UserId>();
		userMap.put("UserId" , new UserId("sorany123@gmail.com"));
		
		InstanceBoundary instanceBoundry = new InstanceBoundary(
				instanceIdMap, "MusicalTaste", "musicalTase_name", new Boolean(true),new Date(), userMap, location, musicalTaste.getInstanceAttributes());
		
		
		InstanceBoundary[] instanceboundryArr = {instanceBoundry};
		return instanceboundryArr;
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path= DuetApplication.API_PREFIX + DuetApplication.INSTANCES_HEADER + "/musicalTaste/{instanceId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
		public InstanceBoundary retriveChat (@PathVariable("instanceId") String instanceId) {
			
			Map<String, String> instanceIdMap = new TreeMap<String, String>();
			instanceIdMap.put("Domain", DuetApplication.DOMAIN);
			instanceIdMap.put("ID", instanceId);
			
			Map<String, Double> location = new TreeMap<String, Double>();
			location.put("lat", 1.34343);
			location.put("lng", 45.7453);
			
			ArrayList<String> topArtists = new ArrayList<>();
			Collections.addAll(topArtists,"Eminem", "Soja", "2Pac", "Coldplay");
			ArrayList<String> topTracks = new ArrayList<>();
			Collections.addAll(topTracks,"Track1", "Track2", "Track3", "Track4");
			ArrayList<String> topPodcasts = new ArrayList<>();
			Collections.addAll(topPodcasts,"Podcast1", "Podcast2", "Podcast3");
			ArrayList<String> topGenres = new ArrayList<>();
			Collections.addAll(topPodcasts,"Genere1", "Genere2", "Genere3");
			MusicalTaste musicalTaste = new MusicalTaste(topArtists, topTracks, topPodcasts, topGenres);
			
			Map<String, UserId> userMap = new TreeMap<String, UserId>();
			userMap.put("UserId" , new UserId("sorany123@gmail.com"));
			
			InstanceBoundary instanceBoundry = new InstanceBoundary(
					instanceIdMap, "Match", "match_name", new Boolean(true),new Date(), userMap, location, musicalTaste.getInstanceAttributes());	
			
			return instanceBoundry;
		}
}
