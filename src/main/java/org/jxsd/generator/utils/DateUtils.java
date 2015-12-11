package org.jxsd.generator.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Date utils class.
 *
 * @author Idriss Neumann <idriss.neumann@capgemini.com>
 */
public class DateUtils {
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATE_HOUR = "yyyy-MM-dd HH:mm";

    /**
     * Convert a String to calendar.
     * 
     * @param str
     * @param lenient
     * @return Calendar
     * @throws ParseException
     */
    public static final Calendar stringToCalendar(final String str, boolean lenient) throws ParseException {
        return stringToCalendar(str, FORMAT_DATE, lenient);
    }

    /**
     * Convert a String to calendar.
     * 
     * @param str
     * @param format
     * @param lenient
     * @return Calendar
     * @throws ParseException
     */
    public static final Calendar stringToCalendar(final String str, String format, boolean lenient) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
        sdf.setLenient(lenient);

        try {
            Date date = sdf.parse(str);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        } catch (ParseException ex) {
            throw new RuntimeException("Unknwown : \"" + format + "\" -> \"" + str + "\"", ex);
        }
    }

    /**
     * Convert a String to calendar.
     * 
     * @param str
     * @return Calendar
     * @throws ParseException
     */
    public static final Calendar stringToCalendar(final String str) throws ParseException {
        return stringToCalendar(str, true);
    }

    /**
     * Convert a String to calendar.
     * 
     * @param str
     * @param format
     * @return Calendar
     * @throws ParseException
     */
    public static final Calendar stringToCalendar(final String str, String format) {
        return stringToCalendar(str, format, true);
    }

    /**
     * Convert a calendar to String.
     * 
     * @param c
     * @param format
     * @return String
     */
    public static final String calendarToString(Calendar c, String format) {
        String rtn = null;

        if (c != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
            rtn = sdf.format(c.getTime());
        }

        return rtn;
    }

    /**
     * Convert a calendar to String.
     * 
     * @param c
     * @return String
     */
    public static final String calendarToString(Calendar c) {
        return calendarToString(c, FORMAT_DATE);
    }

    /**
     * Static class
     */
    private DateUtils() {
    }
}
