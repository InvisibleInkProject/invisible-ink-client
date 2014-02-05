package no.invisibleink.listener;

import android.content.Context;

public interface OnListSectionFragmentListener {

	/**
	 * Request inks from server
	 */
    public void onRequestInks(Context context);
    
    
    public void doLocationUpdates(boolean yes);
}
