package no.invisibleink.core.server_comm;

import java.util.Date;

import android.location.Location;

import no.invisibleink.model.Ink;

public class GsonInk {

	private Date created;
	private int id;
	private double location_lat;
	private double location_lon;
	private String resource_uri;
	private String text;
	private Date updated;
	private double radius;

	private Date expires;
	private int user_id;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public Date getExpires() {
		return expires;
	}
	public void setExpires(Date expires) {
		this.expires = expires;
	}	

	
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLocation_lat() {
		return location_lat;
	}
	public void setLocation_lat(double location_lat) {
		this.location_lat = location_lat;
	}
	public double getLocation_lon() {
		return location_lon;
	}
	public void setLocation_lon(double location_lon) {
		this.location_lon = location_lon;
	}
	public String getResource_uri() {
		return resource_uri;
	}
	public void setResource_uri(String resource_uri) {
		this.resource_uri = resource_uri;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	/**
	 * Convert GsonInk object to {@link no.invisibleink.model.Ink} object.
	 * 
	 * @author Fabian
	 * @return Converted Ink object
	 */
	public Ink toInk() {
		Location location = new Location("");
		location.setLatitude(location_lat);
		location.setLongitude(location_lon);
		return new Ink(id, location, radius, "--", text, "--", updated);
	}
	
	@Override
	public String toString() {
		return "GsonInk [created=" + created + ", id=" + id + ", location_lat="
				+ location_lat + ", location_lon=" + location_lon
				+ ", resource_uri=" + resource_uri + ", text=" + text
				+ ", updated=" + updated + ", radius=" + radius + "]";
	}
	
	
}
