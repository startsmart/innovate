/**
 *
 */
package loggerapi;

/**
 * @author 527395
 *
 */
public class ConsoleLogger extends Logger
{
	@Override
	public void info(Object s)
    {
        System.out.println(format(s));
    }

	@Override
    public void error(Object s)
    {
        System.err.println(format(s));
    }

	@Override
    public void error(Object s, Throwable t)
    {
        System.err.println(format(s));
        t.printStackTrace(System.err);
    }

	@Override
	public void warn(Object s)
	{
		 System.out.println(format(s));
	}

	@Override
	public void warn(Object s, Throwable t)
	{
		 System.err.println(format(s));
		 t.printStackTrace(System.err);
	}

	@Override
	public void debug(Object s)
	{
		 System.out.println(format(s));

	}

	@Override
	public void debug(Object s, Throwable t)
	{
		 System.err.println(format(s));
		 t.printStackTrace(System.err);

	}

	@Override
	public String format(Object s)
	{
		return s == null ? null : s.toString();
	}

}
