package sslcertificateresolver;

import loggerapi.Logger;

/**
 * @author Sanjeev S.
 */
public class SSLConnectionReporter
{
    private static final String VERSION = "v1.0.0-Manual";

    private SSLConnectionReporter()
    {
    }

    public static void main(String[] args) throws Exception
    {
        String url = null;
        String trust = null;
        String passcode = "changeit";
        if (args.length >= 1)
        {
            url = args[0];
            trust = (args.length > 1) ?  args[1] : trust;
            passcode = (args.length > 2) ? args[2] : passcode;
        }
        if(url == null || trust == null)
        {
            showHelp();
        }
        else
        {
            SSLCertificateResolver r = new SSLCertificateResolver(url, trust, passcode);
            Logger.getLogger().info("Starting...");
            Logger.getLogger().info(r.resolve());
        }
    }

    private static void showHelp()
    {
        StringBuilder str = new StringBuilder();
        str.append("sslcertificateresolver v" + VERSION + " USAGE");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("java -jar sslcertificateresolver.jar [mode] [URL] [trust_store_path] [trust_store_pass_phrase]");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[mode] - Mode inwhich the tool to run - MANUAL, AUTO, MERGE");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[URL] - URL for which SSL certificates to be added | URL causing certificate issue");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[trust_store_path] - The file path of the trust file (jwb.trust)");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[trust_store_pass_phrase] - Passphrase for the trust store, this is optional and if not given java's default passphrease will be considered.");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("EXAMPLE 1" + System.lineSeparator() + "java -jar sslcertificateresolver.jar MANUAL https://sample.com D:\\scripts\\ngwijwbtrust\\src\\main\\resources\\jwb.trust trustpass");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("EXAMPLE 2" + System.lineSeparator() + "java -jar sslcertificateresolver.jar MANUAL https://sample.com D:\\scripts\\ngwijwbtrust\\src\\main\\resources\\jwb.trust");
        Logger.getLogger().info(str);
    }
}
