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
        System.out.println(s);
    }

	@Override
    public void error(Object s)
    {
        System.err.println(s);
    }

	@Override
    public void error(Object s, Throwable t)
    {
        System.err.println(s);
        t.printStackTrace(System.err);
    }

	@Override
	public void warn(Object s)
	{
		 System.out.println(s);
	}

	@Override
	public void warn(Object s, Throwable t)
	{
		 System.err.println(s);
		 t.printStackTrace(System.err);
	}

	@Override
	public void debug(Object s)
	{
		 System.out.println(s);

	}

	@Override
	public void debug(Object s, Throwable t)
	{
		 System.err.println(s);
		 t.printStackTrace(System.err);

	}

}
