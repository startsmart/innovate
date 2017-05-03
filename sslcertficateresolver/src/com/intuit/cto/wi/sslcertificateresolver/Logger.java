package com.intuit.cto.wi.sslcertificateresolver;

/**
 * @author Sanjeev S.[527395]
 *
 */
public class Logger
{
    private static Logger logger;

    private Logger()
    {
        //private constructor
    }

    public static synchronized void registerLogger(Logger loggerA)
    {
        Logger.logger = loggerA;
    }

    public static synchronized Logger getLogger()
    {
        if(Logger.logger == null)
        {
            Logger.logger = new Logger();
        }
        return Logger.logger;
    }

    public void info(Object s)
    {
        System.out.println(s);
    }
    public void error(Object s)
    {
        System.err.println(s);
    }
    public void error(Object s, Throwable t)
    {
        System.err.println(s);
        t.printStackTrace(System.err);
    }
}
