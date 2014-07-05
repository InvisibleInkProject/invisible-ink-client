	
	public void onReceivedInkList(InkList inkList, Location oldLocation) {
		if (inkList != null) {
			Log.d(TAG, "received: " + inkList.size() + " inks");

			this.inkList.clear();
			this.inkList.addAll(inkList);
			
			Location location;
			try {
				if (oldLocation == null) {
					location = locationManager.getLocation();
				} else {
					location = oldLocation;
				}
				Log.w(TAG, location.toString());
				inkList.updateVisibility(location);
		    	listSectionFragment.update(inkList, location);
		    	mapSectionFragment.update(inkList);		
			} catch (NoLocationException e) {
				Log.w(TAG, "onReceivedInkList: no location");				
			}
		} else {
			Log.d(TAG, "onReceivedInkList: Receive null inkList");
			
			// TODO: comment
	        InkList tmpInkList = db.getInkList();
	        //for(Ink i : tmpInkList) {
	        //	Log.i(LOG, i.toString());
	        //}
	        if (!tmpInkList.isEmpty()) {
	        	Log.i(TAG, "recover " + tmpInkList.size() + " inks form db");
	        	try {
					onReceivedInkList(tmpInkList, locationManager.getSavedLocation());
				} catch (NoLocationException e) {
		        	Log.d(TAG, "recover " + e.getMessage());
				}
	        }

	        
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see no.invisibleink.listener.OnListSectionFragmentListener#onRequestInks()
	 */
	@Override
	public void onRequestInks() {
		try {
			Location location = locationManager.getLocation();
			serverManager.request(this, location);
		} catch (NoLocationException e) {
			Log.w(TAG, "onRequestInks: " + e.getMessage());
		}		
	}

	/*
	 * (non-Javadoc)
	 * @see no.invisibleink.listener.OnListSectionFragmentListener#doLocationUpdates(boolean)
	 */
	@Override
	public void doLocationUpdates(boolean yes) {
		if (yes) {
			locationManager.startUpdates();
		} else {
			locationManager.stopUpdates();
		}
		
	}


	public void onReceiveInks(InkList inkList) {
		// TODO Auto-generated method stub
		
	}
    
}
