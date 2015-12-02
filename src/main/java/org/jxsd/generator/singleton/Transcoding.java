package org.jxsd.generator.singleton;

import static org.jxsd.generator.XSDConstants.CSV_FORMAT_DATE;
import static org.jxsd.generator.XSDConstants.CSV_FORMAT_DATETIME;
import static org.jxsd.generator.XSDConstants.CSV_FORMAT_DOUBLE;
import static org.jxsd.generator.XSDConstants.CSV_FORMAT_DURATION;
import static org.jxsd.generator.XSDConstants.CSV_FORMAT_INT;
import static org.jxsd.generator.XSDConstants.CSV_FORMAT_STRING;
import static org.jxsd.generator.XSDConstants.CSV_FORMAT_TIME;
import static org.jxsd.generator.XSDConstants.XSD_FORMAT_DATE;
import static org.jxsd.generator.XSDConstants.XSD_FORMAT_DATETIME;
import static org.jxsd.generator.XSDConstants.XSD_FORMAT_DOUBLE;
import static org.jxsd.generator.XSDConstants.XSD_FORMAT_DURATION;
import static org.jxsd.generator.XSDConstants.XSD_FORMAT_INT;
import static org.jxsd.generator.XSDConstants.XSD_FORMAT_STRING;
import static org.jxsd.generator.XSDConstants.XSD_FORMAT_TIME;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

/**
 * Transcoding class.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
@Singleton
public class Transcoding {
	Map<String, String> csvToXsdTypes;

	/**
	 * Init.
	 */
	@PostConstruct
	public void init() {
		csvToXsdTypes = new HashMap<String, String>();
		csvToXsdTypes.put(CSV_FORMAT_DATE.toUpperCase(), XSD_FORMAT_DATE);
		csvToXsdTypes.put(CSV_FORMAT_DOUBLE.toUpperCase(), XSD_FORMAT_DOUBLE);
		csvToXsdTypes.put(CSV_FORMAT_STRING.toUpperCase(), XSD_FORMAT_STRING);
		csvToXsdTypes.put(CSV_FORMAT_INT.toUpperCase(), XSD_FORMAT_INT);
		csvToXsdTypes.put(CSV_FORMAT_DATETIME.toUpperCase(), XSD_FORMAT_DATETIME);
		csvToXsdTypes.put(CSV_FORMAT_TIME.toUpperCase(), XSD_FORMAT_TIME);
		csvToXsdTypes.put(CSV_FORMAT_DURATION.toUpperCase(), XSD_FORMAT_DURATION);
	}

	/**
	 * @return the csvToXsdTypes
	 */
	public Map<String, String> getCsvToXsdTypes() {
		return csvToXsdTypes;
	}
}
