package no.invisibleink.app.controller;

public abstract class Settings {

	/**
	 * Server URL for communication
	 */
	public static final String API_URL = "http://server.invisibleink.no/api/v1/";
	
	/**
	 * Server URL for oAuth communication
	 */
	public static final String OAUTH_URL = "http://server.invisibleink.no/oauth2/access_token/";
		
	/**
	 * If the distance between the current location and the last
	 * location is greater as this value, the server will be requested.
	 * 
	 * unit: meters
	 */
	public static final float REQUEST_DISTANCE_CHANGE = 30;
	
	/**
	 * If the time since the last server request is greater as this
	 * value, the server will be requested.
	 * 
	 * unit: seconds
	 */
	public static final long REQUEST_TIME_PERIOD = 20;
	
	
	/**
	 * The server returns all inks, which are in this radius.
	 * 
	 * unit: meters
	 */
	public static final float REQUEST_INKS_RADIUS_IN_METERS = 2000;
	
}
