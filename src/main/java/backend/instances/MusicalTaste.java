package backend.instances;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class MusicalTaste implements Instance{
	
	private ArrayList<String> topArtists;
	private ArrayList<String> topTracks;
	private ArrayList<String> topPodcasts;
	private ArrayList<String> topGenres;
	
	public MusicalTaste() {
		
	}

	public MusicalTaste(ArrayList<String> topArtists, ArrayList<String> topTracks, ArrayList<String> topPodcasts,
			ArrayList<String> topGenres) {
		super();
		this.topArtists = topArtists;
		this.topTracks = topTracks;
		this.topPodcasts = topPodcasts;
		this.topGenres = topGenres;
	}

	public ArrayList<String> gettopArtistss() {
		return topArtists;
	}

	public void settopArtists(ArrayList<String> topArtists) {
		this.topArtists = topArtists;
	}

	public ArrayList<String> getTopTracks() {
		return topTracks;
	}

	public void setTopTracks(ArrayList<String> topTracks) {
		this.topTracks = topTracks;
	}

	public ArrayList<String> getTopPodcasts() {
		return topPodcasts;
	}

	public void setTopPodcasts(ArrayList<String> topPodcasts) {
		this.topPodcasts = topPodcasts;
	}

	public ArrayList<String> getTopGenres() {
		return topGenres;
	}

	public void setTopGenres(ArrayList<String> topGenres) {
		this.topGenres = topGenres;
	}
	
	@Override
	public Map<String, Object> getInstanceAttributes() {
		Map<String, Object> attributes = new TreeMap<String, Object>();
		attributes.put("topArtists", this.topArtists);
		attributes.put("topTracks", this.topTracks);
		attributes.put("topPodcasts", this.topPodcasts);
		attributes.put("topGenres", this.topGenres);
		return attributes;
	}

	@Override
	public String toString() {
		return "MusicalTaste [topArtists=" + topArtists + ", topTracks=" + topTracks + ", topPodcasts=" + topPodcasts
				+ ", topGenres=" + topGenres + "]";
	}
	

}
