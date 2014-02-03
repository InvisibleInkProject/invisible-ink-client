package no.invisibleink.core.server_comm;

public abstract class Settings {

	/**
	 * Server URL for communication
	 */
	protected static final String SERVER_URL = "http://server.invisibleink.no/api/v1/message/";
	
	/**
	 * If the distance between the current location and the last
	 * location is greater as this value, the server will be requested.
	 * 
	 * unit: meters
	 */
	protected static final float REQUEST_DISTANCE_CHANGE = 30;
	
	/**
	 * If the time since the last server request is greater as this
	 * value, the server will be requested.
	 * 
	 * unit: seconds
	 */
	protected static final long REQUEST_TIME_PERIOD = 20;	
	
}
