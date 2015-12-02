package org.jxsd.generator.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter to force unmarshal date as Calendar.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class StringToGregorianCalendarAdapter extends XmlAdapter<String, Calendar> {

	private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Calendar unmarshal(String v) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(PATTERN);
		Date date = format.parse(v);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String marshal(Calendar v) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(PATTERN);
		return format.format(v.getTime());
	}
}
