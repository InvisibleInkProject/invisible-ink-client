package no.invisibleink.controller.location;

public class NoLocationException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoLocationException() {
		super("No location");
	}
}