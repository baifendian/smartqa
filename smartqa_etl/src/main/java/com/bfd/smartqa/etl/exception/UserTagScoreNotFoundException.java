package com.bfd.smartqa.etl.exception;

public class UserTagScoreNotFoundException extends Exception {

	public UserTagScoreNotFoundException() {
		super("User and regular Tag relation do not exist in system!");
	}
}
