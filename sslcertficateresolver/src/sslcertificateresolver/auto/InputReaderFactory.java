package sslcertificateresolver.auto;

import java.io.IOException;
import java.util.Properties;

import loggerapi.Logger;


/**
 * @author Sanjeev S.
 */
public class InputReaderFactory
{

   private static Properties readerConfinfguration = null;

    private InputReaderFactory()
    {
    }

    public static IInputReader getInputReader(String option)
    {
        Properties readerConf = readConfiguration();
        String klass = readerConf.getProperty(option);
        IInputReader reader = null;
        try
        {
            reader = (IInputReader) Class.forName(klass).newInstance();
        }
        catch(Exception e)
        {
            Logger.getLogger().error("Unable to instantiate class " + klass, e);
        }
        if(reader == null)
        {
            reader = new CSVReader();
        }
        return reader;
    }

    private static Properties readConfiguration()
    {
        if(readerConfinfguration == null)
        {
            readerConfinfguration = new Properties();
            try
            {
                readerConfinfguration.load(InputReaderFactory.class.getResourceAsStream("inputReaderConfiguration.properties"));
            }
            catch (IOException e)
            {
                Logger.getLogger().error("Unable to read inputReaderConfiguration.properties", e);
            }
        }
        return readerConfinfguration;
    }

}
