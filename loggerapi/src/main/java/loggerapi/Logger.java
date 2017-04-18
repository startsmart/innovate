package loggerapi;

/**
 * @author Sanjeev S.[527395]
 */
public abstract class Logger
{
    private static Logger logger;

    public static synchronized void registerLogger(Logger loggerA)
    {
        Logger.logger = loggerA;
    }

    public static synchronized Logger getLogger()
    {
        if(Logger.logger == null)
        {
            Logger.logger = new ConsoleLogger();
        }
        return Logger.logger;
    }

    public abstract void info(Object s);
    public abstract void error(Object s);
    public abstract void error(Object s, Throwable t);
    public abstract void warn(Object s);
    public abstract void warn(Object s, Throwable t);
    public abstract void debug(Object s);
    public abstract void debug(Object s, Throwable t);
    public abstract String format(Object s);
}
