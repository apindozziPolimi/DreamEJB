package exceptions;
/*
 * 
 * Class that send exception message.
 * 
 */
public class CredentialsException extends Exception {
	private static final long serialVersionUID = 1L;

	public CredentialsException(String message) {
		super(message);
	}
}
