package no.invisibleink.listener;

import android.content.Context;

public interface OnPostSectionFragmentListener {

	/**
	 * 
	 * @param message
	 *            Message
	 * @param radius
	 *            Visibility radius in meters
	 * @param context
	 */
    public void onPostInkForm(String message, int radius, Context context);
}
