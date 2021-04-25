package com.kk.log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SimpleLogging {
	
	public static void main(String[] args) {
		
		String logFileName = "log.txt";
		String configurationFileName = "myLogConfiguration.properties";
		
		logOnFileWithoutUsingConfigurationFile(logFileName);
		logOnFileUsingConfigurationFile(logFileName, configurationFileName);
		
	}
	
	private static void log(Logger logger, String text) {
		//The level you set, logger will log whose level is >= INFO.
		logger.setLevel(Level.INFO);
		
		logger.log(Level.WARNING, "warning message" + " : " + text);
		logger.log(Level.INFO, "info message" + " : " + text);
		
		//In case you want to print stack-trace in logs. Just pass exception in logger.
		try {
			throw new Exception();
		}
		catch(Exception ex) {
			logger.log(Level.SEVERE, "Throwing severe message" + " : " + text, ex);
		}
		
		logger.warning("another way of warning message" + " : " + text);
		logger.info("another way of info message" + " : " + text);
	}

	private static void logOnFileWithoutUsingConfigurationFile(String logFileName) {
		try {
			File file = new File(logFileName);
			//Create log.txt file if doesn't exist
			if(!file.exists())
				file.createNewFile();
			
			FileHandler fh = new FileHandler(logFileName, false); //Not append text in file on re-execution this code on log.txt
			fh.setFormatter(new SimpleFormatter());
			
			Logger logger = Logger.getLogger(SimpleLogging.class.getName());
			logger.addHandler(fh);
			logger.info("/******************************* Without using configuration file ****************************/");
			
			log(logger, "Without using configuration file");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void logOnFileUsingConfigurationFile(String logFileName, String configurationFileName) {
		//In case you have configuration file where logging is configured. This approach can be used.
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream(new File(configurationFileName)));
			String property = LogManager.getLogManager().getProperty("java.util.logging.FileHandler.formatter"); //java.util.logging.XMLFormatter
			System.out.println(property);
			
			Logger globalLogger = Logger.getGlobal();
			FileHandler fh = new FileHandler(logFileName, true); //Append text in file on re-execution this code on log.txt
			globalLogger.addHandler(fh);
			globalLogger.info("/******************************* Using configuration file ****************************/");
			
			log(globalLogger, "Using Configuration file");
			
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}

}
