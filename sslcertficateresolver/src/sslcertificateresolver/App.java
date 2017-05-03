package sslcertificateresolver;

import java.util.Arrays;

import sslcertificateresolver.auto.AutoSSLConnectionReporter;

/**
 * @author Sanjeev S. [527395]
 */
public class App
{
    private static final String[] EMPTY_ARRAY = {};

    public static void main(String[] args) throws Exception
    {
        String mode = "AUTO";
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
            default:
                AutoSSLConnectionReporter.main(getArgs(args));
        }
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
