package com.bfd.smartqa.etl.exception;

public class TagNotFoundException extends Exception {

	public TagNotFoundException() {
		super("TagID cannot found by TagName!");
	}
}
