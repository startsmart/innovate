package utility.datetime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Sanjeev S.
 */
public class DateUtility
{

	public static final long MILLI_SEC_IN_A_DAY = 86400000L;
	public static final long MILLI_SEC_IN_A_HOUR = 3600000L;
	public static final long MILLI_SEC_IN_A_MINUTE = 60000L;
	public static final long MILLI_SEC_IN_A_SEC = 1000L;

	public enum DateConvertFormat
	{
		DMY(0,1,2), MDY(1,0,2), YMD(2,1,0);

		int dateIndex;
		int montIndex;
		int yearIndex;

		private DateConvertFormat(int dateIndexA, int montIndexA, int yearIndexA)
		{
			dateIndex = dateIndexA;
			montIndex = montIndexA;
			yearIndex = yearIndexA;
		}
	}

	private DateUtility()
	{
	}

	public static long getDaysElapsed(Date startDate, Date endDate)
	{
		long startMillis = startDate.getTime();
		long endMillis = endDate.getTime();
		long diff = Math.abs(endMillis - startMillis);
		return Math.round(diff / MILLI_SEC_IN_A_DAY);
	}

	public static long getWeeksElapsed(Date startDate, Date endDate)
	{
		return Math.round(getDaysElapsed(startDate, endDate) / 7L);
	}

	public static boolean compareStringDate(String date1, String date2, DateConvertFormat format, String seperator, int compareValue) throws Exception
	{
		if(date1 == null || date2 == null)
		{
			throw new Exception("Dates to compare cannot be null");
		}

		String[] d1Parts = date1.split(seperator);
		String[] d2Parts = date2.split(seperator);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.set(Calendar.DATE, Integer.parseInt(d1Parts[format.dateIndex]));
		c1.set(Calendar.MONTH, Integer.parseInt(d1Parts[format.montIndex]) - 1);
		c1.set(Calendar.YEAR, Integer.parseInt(d1Parts[format.yearIndex]));

		c2.set(Calendar.DATE, Integer.parseInt(d2Parts[format.dateIndex]));
		c2.set(Calendar.MONTH, Integer.parseInt(d2Parts[format.montIndex]) - 1);
		c2.set(Calendar.YEAR, Integer.parseInt(d2Parts[format.yearIndex]));
		int c = c1.compareTo(c2);
		return (compareValue > 0) ? (c > 0) : ((compareValue == 0) ? (c == 0) : (c < 0));
	}

	public static String getMonthIdentifier(Date date)
	{
		SimpleDateFormat format = new SimpleDateFormat("MMM_yyyy");
		return format.format(date).toUpperCase();
	}

	public static String getFormattedCurrentDate(String formatStr)
	{
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.format(new Date()).toUpperCase();
	}

	public static String getHumanReadableTime(long millisArg)
	{
		long millis = millisArg;
		String time = "";
		if(millis > MILLI_SEC_IN_A_DAY)
		{
			long temp = millis %  MILLI_SEC_IN_A_DAY;
			time += ((millis - temp) / MILLI_SEC_IN_A_DAY) + " Day(s) ";
			millis = temp;
		}
		if(millis > MILLI_SEC_IN_A_HOUR)
		{
			long temp = millis %  MILLI_SEC_IN_A_HOUR;
			time += ((millis - temp) / MILLI_SEC_IN_A_HOUR) + " Hour(s) ";
			millis = temp;
		}
		if(millis > MILLI_SEC_IN_A_MINUTE)
		{
			long temp = millis %  MILLI_SEC_IN_A_MINUTE;
			time += ((millis - temp) / MILLI_SEC_IN_A_MINUTE) + " Min(s) ";
			millis = temp;
		}
		if(millis > MILLI_SEC_IN_A_SEC)
		{
			long temp = millis %  MILLI_SEC_IN_A_SEC;
			time += ((millis - temp) / MILLI_SEC_IN_A_SEC) + " Sec(s) ";
			millis = temp;
		}
		if(millis > 0)
		{
			time += millis + " MilliSec(s) ";
		}
		return time;
	}

	public static String safeFormatDate(Date date, String formatStr, String defaultValue)
	{
		if(date == null)
			return defaultValue;
		try
		{
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			return format.format(date).toUpperCase();
		}
		catch(Exception e)
		{
			return defaultValue;
		}
	}
}
