package no.invisibleink.test_api;

import android.location.Location;

public class Stub {

	public static String username = "sample";
	public static String password = "sample";
	public static String client_id = "7e880aa6a2b8cad31bd1";
	public static String client_secret = "d0b25b572fe1338f6d406ed7edb7372b600787bd";
	public static String access_token = "226b5f1bf60700ba6e8a5126bcf0a66c8562e84f";
	public static String refresh_token = "e9201113453e019ac6cfcefec9ea4992295692ef";
	
	public static Location getLocation() {
       	Location loc = new Location("");
       	loc.setLatitude(59.94);
       	loc.setLongitude(10.72);
       	return loc;
	}

}
