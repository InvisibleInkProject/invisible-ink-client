package no.invisibleink.view;

import android.location.Location;
import no.invisibleink.core.inks.InkList;

public class Update {

	private InkList inkList;
	private Location location;
	
	public Update(InkList inkList, Location location) {
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
