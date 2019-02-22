package pl.polsl.smtp.mailtrap;

/**
 * SMTP response container.
 */
public class Response {
	/**
	 * Response code
	 */
	private int code;
	/**
	 * Response message.
	 */
	private String message;
	/**
	 * New state of the SMTP server once the request has been executed.
	 */
	private SmtpState nextState;

	/**
	 * Constructor.
	 * 
	 * @param code
	 *            response code
	 * @param message
	 *            response message
	 * @param next
	 *            next state of the SMTP server
	 */
	public Response(int code, String message, SmtpState next) {
		this.code = code;
		this.message = message;
		this.nextState = next;
	}

	/**
	 * Get the response code.
	 * 
	 * @return response code
	 */
	public int getCode() {
		return code;
	}

	
	public String getMessage() {
		return message;
	}

	/**
	 * Get the next SMTP server state.
	 * 
	 * @return state
	 */
	public SmtpState getNextState() {
		return nextState;
	}
}
