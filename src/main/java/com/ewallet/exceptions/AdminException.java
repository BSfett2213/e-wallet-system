package com.ewallet.exceptions;

public class AdminException extends Exception {

	public AdminException(String message) {
		super(message);
	}

	public AdminException(Throwable cause) {
		super(cause);
	}

	public AdminException(String message, Throwable cause) {
		super(message, cause);
	}

	public AdminException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public static void main(String[] args) {
	}

}
