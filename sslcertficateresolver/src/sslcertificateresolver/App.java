package sslcertificateresolver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import loggerapi.Logger;
import sslcertificateresolver.auto.AutoSSLConnectionReporter;

/**
 * @author Sanjeev S.
 */
public class App
{
    private static final String[] EMPTY_ARRAY = {};
    private static final String VERSION = "1.0.0";
    private static final String CERTIFICATE = "Certificate ";
    private static final String APP_NAME = "sslcertificateresolver v";
    private static final String HELP_SWITCH = "-h";

    private App()
    {
    }

    public static void main(String[] args) throws Exception
    {
    	 String mode = HELP_SWITCH;
        if(args.length > 0)
        {
            mode = args[0];
        }
        mode = mode == null ? String.valueOf(HELP_SWITCH) : mode.toUpperCase(); /*String.valueOf method used to avoid warning*/
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
            case "CLEANUP":
                cleanupTrustManager(getArgs(args));
                break;
            case "EXPIRY":
                expiryDetails(getArgs(args));
                break;
            default:
               showHelp();
        }
    }

    private static void showHelp()
    {
        StringBuilder str = new StringBuilder();
        str.append(APP_NAME + VERSION + " USAGE");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("java -jar sslcertificateresolver.jar [mode [-h]] [-h]");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[mode] - Mode in which the tool to run - MANUAL, AUTO, MERGE, EXPIRY, CLEANUP");
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

    private static void cleanupTrustManager(String[] args)
    {
        String trust = null;
        String passcode = "changeit";
        if (args.length >= 1)
        {
            trust = args[0];
            passcode = (args.length > 1) ? args[1] : passcode;
        }
        if(trust == null || trust.trim().equalsIgnoreCase(HELP_SWITCH))
        {
            showCleanUpHelp();
        }
        else
        {
            Logger.getLogger().info("Starting...");
            cleanupTrustManager(trust, passcode);
        }
    }

    private static void cleanupTrustManager(String filePath, String trustPass)
    {
        List<X509Certificate> certStore = new ArrayList<>();
        try(InputStream in = new FileInputStream(filePath);)
        {
            Date currentDate = new Date();
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(in, trustPass.toCharArray());
            in.close();
            Logger.getLogger().info("Key Store size before clean up " + ks.size());
            Enumeration<String> all = ks.aliases();
            while(all.hasMoreElements())
            {
                String alias = all.nextElement();
                X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
                Date notAfter = cert.getNotAfter();
                if(currentDate.compareTo(notAfter) > 0 || certStore.contains(cert))
                {
                    Logger.getLogger().info(CERTIFICATE + alias + " Expired | Duplicate. Validity " + notAfter);
                    ks.deleteEntry(alias);

                }
            }
            Logger.getLogger().info("Key Store size After clean up " + ks.size());
            try(OutputStream out = new FileOutputStream(filePath);)
            {
                ks.store(out, trustPass.toCharArray());
                out.close();
            }
        }
        catch (Exception e)
        {
            Logger.getLogger().error("Exception while cleaning trust store.", e);
        }
    }

    private static void expiryDetails(String filePath, String trustPass, long distance)
    {
        try(InputStream in = new FileInputStream(filePath);)
        {
            long millis = System.currentTimeMillis();
            long distanceMillis = distance * 24 * 60 * 60 * 1000;
            Date currentDate = new Date();
            Date endDate = new Date(millis + distanceMillis);
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(in, trustPass.toCharArray());
            in.close();
            Logger.getLogger().info("Total installed Certificates - " + ks.size());
            Enumeration<String> all = ks.aliases();
            int ecount = 0;
            int count = 0;
            while(all.hasMoreElements())
            {
                String alias = all.nextElement();
                X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
                Date notAfter = cert.getNotAfter();
                if(currentDate.compareTo(notAfter) > 0)
                {
                    Logger.getLogger().info(CERTIFICATE + alias + " has Expired on " + notAfter);
                    ecount++;
                }
                else if(endDate.compareTo(notAfter) >= 0)
                {
                    Logger.getLogger().info(CERTIFICATE + alias + " will Expire on " + notAfter);
                    count++;
                }
            }
            Logger.getLogger().info("Total  Certificates Expired - " + ecount);
            Logger.getLogger().info("Total  Certificates that will Expire in next " + distance + " day(s) - "+ count);
        }
        catch (Exception e)
        {
            Logger.getLogger().error("Exception while testing expiry.", e);
        }
    }

    private static void expiryDetails(String[] args)
    {
        String trust = null;
        Long distance = 7L;
        String passcode = "changeit";
        if (args.length >= 1)
        {
            trust = args[0];
            distance = (args.length > 1) ?  optLong(args[1], distance) : distance;
            passcode = (args.length > 2) ? args[2] : passcode;
        }
        if(trust == null || trust.trim().equalsIgnoreCase(HELP_SWITCH))
        {
            showExpireHelp();
        }
        else
        {
            Logger.getLogger().info("Starting...");
            expiryDetails(trust, passcode, distance);
        }
    }

    private static void showExpireHelp()
    {
        StringBuilder str = new StringBuilder();
        str.append(APP_NAME + VERSION + "-EXPIRY USAGE");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("java -jar sslcertificateresolver.jar EXPIRY [trust_store_path] [duration] [trust_store_pass_phrase]");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[trust_store_path] - The file path of the trust file (jwb.trust)");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[duration] - no. days from today to calculate expiry default 7 days");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[trust_store_pass_phrase] - Passphrase for the trust store, this is optional and if not given java's default passphrease will be considered.");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("EXAMPLE 1" + System.lineSeparator() + "java -jar sslcertificateresolver.jar EXPIRY \"D:\\scripts\\ngwijwbtrust\\src\\main\\resources\\jwb.trust\" 10 \"trustpass\"");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("EXAMPLE 2" + System.lineSeparator() + "java -jar sslcertificateresolver.jar EXPIRY \"D:\\scripts\\ngwijwbtrust\\src\\main\\resources\\jwb.trust\" 10");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("EXAMPLE 3" + System.lineSeparator() + "java -jar sslcertificateresolver.jar EXPIRY \"D:\\scripts\\ngwijwbtrust\\src\\main\\resources\\jwb.trust\"");
        Logger.getLogger().info(str);
    }

    private static void showCleanUpHelp()
    {
        StringBuilder str = new StringBuilder();
        str.append(APP_NAME + VERSION + "-CLEANUP USAGE");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("java -jar sslcertificateresolver.jar CLEANUP [trust_store_path] [trust_store_pass_phrase]");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[trust_store_path] - The file path of the trust file (jwb.trust)");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[trust_store_pass_phrase] - Passphrase for the trust store, this is optional and if not given java's default passphrease will be considered.");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("EXAMPLE 1" + System.lineSeparator() + "java -jar sslcertificateresolver.jar CLEANUP \"D:\\scripts\\ngwijwbtrust\\src\\main\\resources\\jwb.trust\" \"trustpass\"");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("EXAMPLE 2" + System.lineSeparator() + "java -jar sslcertificateresolver.jar CLEANUP \"D:\\scripts\\ngwijwbtrust\\src\\main\\resources\\jwb.trust\"");
        Logger.getLogger().info(str);
    }

    private static Long optLong(String string, Long i)
    {
        try
        {
           return Long.parseLong(string);
        }
        catch(Exception e)
        {
            Logger.getLogger().error("Unable to convert to long - " + string, e);
        }
        return i;
    }
}
