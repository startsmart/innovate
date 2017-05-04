package sslcertificateresolver;

import java.util.Arrays;

import loggerapi.Logger;
import sslcertificateresolver.auto.AutoSSLConnectionReporter;

/**
 * @author Sanjeev S.
 */
public class App
{
    private static final String[] EMPTY_ARRAY = {};
    private static final String VERSION = "1.0.0";

    public static void main(String[] args) throws Exception
    {
        String mode = "-h";
        if(args.length > 0)
        {
            mode = args[0];
        }
        mode = mode == null ? "AUTO" : mode.toUpperCase();
        switch(mode)
        {
            case "MANUAL":
                SSLConnectionReporter.main(getArgs(args));
                break;
            case "MERGE":
                TrustStoreMerger.main(getArgs(args));
                break;
            case "AUTO":
                AutoSSLConnectionReporter.main(getArgs(args));
                break;
            default:
               showHelp();
        }
    }

    private static void showHelp()
    {
        StringBuilder str = new StringBuilder();
        str.append("sslcertificateresolver v" + VERSION + " USAGE");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("java -jar sslcertificateresolver.jar [mode [-h]] [-h]");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[mode] - Mode in which the tool to run - MANUAL, AUTO, MERGE");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("-h : Display this help text");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("mode -h : Display mode specific help text");
        Logger.getLogger().info(str);
    }


    private static String[] getArgs(String[] args)
    {
        if(args.length < 2)
        {
            return EMPTY_ARRAY;
        }
        else
        {
            return Arrays.copyOfRange(args, 1, args.length);
        }
    }
}
