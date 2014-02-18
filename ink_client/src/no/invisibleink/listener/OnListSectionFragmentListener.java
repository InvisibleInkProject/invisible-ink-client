package no.invisibleink.listener;

public interface OnListSectionFragmentListener {

	/**
	 * Request inks from server
	 */
    public void onRequestInks();
    
    
    public void doLocationUpdates(boolean yes);
}
