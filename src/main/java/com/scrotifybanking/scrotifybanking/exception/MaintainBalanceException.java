package com.scrotifybanking.scrotifybanking.exception;

/**
 * custom exception
 *
 * @author mahendran
 * @version 1.0
 * @since 12 /05/2019
 */
public class MaintainBalanceException extends CustomException {
	/**
	 * Constructs a new exception with the specified detail message. The cause is
	 * not initialized, and may subsequently be initialized by a call to
	 * {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for later
	 *                retrieval by the {@link #getMessage()} method.
	 */
	public MaintainBalanceException(String message) {
		super(message);
	}
}
