package com.bfd.smartqa.etl.exception;

public class TmpTagNotFoundException extends Exception {

	public TmpTagNotFoundException() {
		super("Tmp tag do not exist in system");
	}
}
