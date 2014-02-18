package no.invisibleink.listener;


public interface OnPostSectionFragmentListener {

	/**
	 * 
	 * @param message
	 *            Message
	 * @param radius
	 *            Visibility radius in meters
	 */
    public void onPostInkForm(String message, int radius);
}
