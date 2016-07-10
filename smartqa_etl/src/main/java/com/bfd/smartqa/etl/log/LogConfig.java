package com.bfd.smartqa.etl.log;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;

public class LogConfig {
	public static void init() {
		String configPath = System.getProperty("user.dir") + File.separator + "config";
		
		PropertyConfigurator.configure(configPath + File.separator + "log4j.properties");
	}
}
