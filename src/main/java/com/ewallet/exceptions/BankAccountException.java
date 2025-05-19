package com.ewallet.exceptions;

public class BankAccountException extends Exception {

	public BankAccountException(String message) {
		super(message);
	}

	public BankAccountException(Throwable cause) {
		super(cause);
	}

	public BankAccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public BankAccountException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
