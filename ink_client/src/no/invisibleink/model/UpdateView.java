package no.invisibleink.model;

import android.location.Location;

public class UpdateView {

	private InkList inkList;
	private Location location;
	
	public UpdateView(InkList inkList, Location location) {
		this.inkList = inkList;
		this.location = location;
	}

	public InkList getInkList() {
		return inkList;
	}

	public Location getLocation() {
		return location;
	}
}
