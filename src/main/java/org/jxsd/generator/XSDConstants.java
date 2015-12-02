package org.jxsd.generator;

/**
 * Constants interface.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public interface XSDConstants {
	String APP_CONFIG_FILE = "application.properties";

	/**
	 * Property file keys.
	 */
	String KEY_DIR_IN = "dir.in";
	String KEY_DIR_OUT = "dir.out";
	String KEY_MAX_LENGTH = "default.max.length";

	/**
	 * Regexp
	 */
	String CSV_FILE = "^.*\\.csv$";

	/**
	 * Specification data types.
	 */
	String CSV_FORMAT_INT = "N";
	String CSV_FORMAT_STRING = "AN";
	String CSV_FORMAT_DOUBLE = "D";
	String CSV_FORMAT_DATE = "Date";
	String CSV_FORMAT_DATETIME = "DateTime";
	String CSV_FORMAT_TIME = "Time";
	String CSV_FORMAT_DURATION = "Duration";

	/**
	 * XSD data types.
	 */
	String XSD_FORMAT_INT = "xs:int";
	String XSD_FORMAT_STRING = "xs:string";
	String XSD_FORMAT_DOUBLE = "xs:decimal";
	String XSD_FORMAT_DATE = "xs:date";
	String XSD_FORMAT_DATETIME = "xs:dateTime";
	String XSD_FORMAT_TIME = "xs:time";
	String XSD_FORMAT_DURATION = "xs:duration";
}
