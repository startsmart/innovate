package loggerapi;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sanjeev S.
 */
public class ThreadTimeConsoleLogger extends ConsoleLogger
{
	private static final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Override
	public String format(Object s)
	{

		String formatted = dateformat.format(new Date()) + " [" + Thread.currentThread().getName() + "] ";
		formatted = formatted + super.format(s);
		return formatted;
	}
}
