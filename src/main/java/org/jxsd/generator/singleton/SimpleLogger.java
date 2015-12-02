package org.jxsd.generator.singleton;

import javax.inject.Singleton;

/**
 * Console logger.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
@Singleton
public class SimpleLogger {
	private String LOG_TEMPLATE = "[%s][%s] %s";

	/**
	 * Main method for logging.
	 * 
	 * @param crit
	 * @param location
	 * @param message
	 * @param e
	 */
	private void log(String crit, String location, String message, Exception e) {
		System.out.println(String.format(LOG_TEMPLATE, crit, location, message));

		if (null != e) {
			e.printStackTrace();
		}
	}

	/**
	 * Debug logging.
	 * 
	 * @param location
	 * @param message
	 * @param e
	 */
	public void debug(String location, String message, Exception e) {
		log("DEBUG", location, message, e);
	}

	/**
	 * Debug logging.
	 * 
	 * @param location
	 * @param message
	 */
	public void debug(String location, String message) {
		debug(location, message, null);
	}

	/**
	 * Info logging.
	 * 
	 * @param location
	 * @param message
	 * @param e
	 */
	public void info(String location, String message, Exception e) {
		log("INFO", location, message, e);
	}

	/**
	 * Info logging.
	 * 
	 * @param location
	 * @param message
	 */
	public void info(String location, String message) {
		info(location, message, null);
	}

	/**
	 * Notice logging.
	 * 
	 * @param location
	 * @param message
	 * @param e
	 */
	public void notice(String location, String message, Exception e) {
		log("NOTICE", location, message, e);
	}

	/**
	 * Notice logging.
	 * 
	 * @param location
	 * @param message
	 */
	public void notice(String location, String message) {
		notice(location, message, null);
	}

	/**
	 * Warn logging.
	 * 
	 * @param location
	 * @param message
	 * @param e
	 */
	public void warn(String location, String message, Exception e) {
		log("WARN", location, message, e);
	}

	/**
	 * Warn logging.
	 * 
	 * @param location
	 * @param message
	 */
	public void warn(String location, String message) {
		warn(location, message, null);
	}

	/**
	 * Error logging.
	 * 
	 * @param location
	 * @param message
	 * @param e
	 */
	public void error(String location, String message, Exception e) {
		log("ERROR", location, message, e);
	}

	/**
	 * Error logging.
	 * 
	 * @param location
	 * @param message
	 */
	public void error(String location, String message) {
		error(location, message, null);
	}

	/**
	 * Crit logging.
	 * 
	 * @param location
	 * @param message
	 * @param e
	 */
	public void crit(String location, String message, Exception e) {
		log("CRIT", location, message, e);
	}

	/**
	 * Crit logging.
	 * 
	 * @param location
	 * @param message
	 */
	public void crit(String location, String message) {
		crit(location, message, null);
	}
}
