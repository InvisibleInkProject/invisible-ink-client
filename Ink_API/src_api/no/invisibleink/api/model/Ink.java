package no.invisibleink.api.model;

import org.json.JSONArray;

public class Ink {

	public static final String ENDPOINT = "message/";
	
	public static final String TEXT = "text";
	public static final String RADIUS = "radius";
	public static final String LOCATION_LON = "location_lon";
	public static final String LOCATION_LAT = "location_lat";
	public static final String EXPIRES = "expires";
	
	public interface OAuthHandler {
		public void onFailureUnauthorized();
		public void onFailure(int statusCode);
	}
	
	public interface GetHandler extends OAuthHandler {
		public void onSucess(JSONArray inks);
	}
	
	public interface PostHandler extends OAuthHandler {
		public void onSucess();
	}
	
}
