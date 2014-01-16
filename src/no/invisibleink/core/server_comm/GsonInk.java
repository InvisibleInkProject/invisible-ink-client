package no.invisibleink.core.server_comm;

import java.util.Date;

public class GsonInk {

	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
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
	private Date created;
	private double distance;
	private int id;
	private double location_lat;
	private double location_lon;
	private String resource_uri;
	private String text;
	private Date updated;
	@Override
	public String toString() {
		return "GsonInk [created=" + created + ", distance=" + distance
				+ ", id=" + id + ", location_lat=" + location_lat
				+ ", location_lon=" + location_lon + ", resource_uri="
				+ resource_uri + ", text=" + text + ", updated=" + updated
				+ "]";
	}
	
	
	
}
