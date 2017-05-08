package sslcertificateresolver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import loggerapi.Logger;

/**
 * @author Sanjeev S.
 */
public final class TrustStoreMerger
{
    private static final String BEGIN_CERTIFICATE = "-----BEGIN CERTIFICATE-----";
    private static final String END_CERT = "-----END CERTIFICATE-----";
    private static final String VERSION = "1.0.0-MERGE";

    private TrustStoreMerger()
    {
    }

    public static void main(String[] args) throws Exception
    {
        String trust = null;
        String input = null;
        String passcode = "changeit";
        if (args.length >= 1)
        {
            input = args[0];
            trust = (args.length > 1) ?  args[1] : trust;
            passcode = (args.length > 2) ? args[2] : passcode;
        }
        if(input == null || trust == null)
        {
            showHelp();
        }
        else
        {
            Logger.getLogger().info("Starting...");
            TrustStoreMerger.mergeTrustStores(trust, input, passcode);
        }
    }

    private static void showHelp()
    {
        StringBuilder str = new StringBuilder();
        str.append("sslcertificateresolver v" + VERSION + " MERGE USAGE");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("java -jar sslcertificateresolver.jar MERGE [input] [trust_store_path] [trust_store_pass_phrase]");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[input] - input file containing certificate data");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[trust_store_path] - The file path of the trust file (jwb.trust)");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("[trust_store_pass_phrase] - Passphrase for the trust store, this is optional and if not given java's default passphrease will be considered.");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("EXAMPLE 1" + System.lineSeparator() + "java -jar sslcertificateresolver.jar MERGE \"D:\\scripts\\ngwijwbtrust\\sampleinput.txt\" \"D:\\scripts\\ngwijwbtrust\\src\\main\\resources\\jwb.trust\" \"trustpass\"");
        str.append(System.lineSeparator());
        str.append(System.lineSeparator());
        str.append("EXAMPLE 2" + System.lineSeparator() + "java -jar sslcertificateresolver.jar MERGE \"D:\\scripts\\ngwijwbtrust\\sampleinput.txt\" \"D:\\scripts\\ngwijwbtrust\\src\\main\\resources\\jwb.trust\"");
        Logger.getLogger().info(str);
    }

    public static void mergeTrustStores(String sourcePath, String inputPath, String pwd) throws Exception
    {
        mergeTrustStores(new File(sourcePath), new File(inputPath), pwd);
    }

    public static void mergeTrustStores(File source, File input, String pwd) throws Exception
    {
        if(!source.isFile())
        {
            throw new IllegalArgumentException("Source file is invalid");
        }
        if(!input.isFile())
        {
            throw new IllegalArgumentException("Input file is invalid");
        }
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream in = new FileInputStream(source);
        ks.load(in, pwd.toCharArray());

        Logger.getLogger().info("Updating certificates to trust store...");
        int i = 0;
        List<X509Certificate> certificates = parseCertificate(input);
        List<String> dicovered = new ArrayList<>();
        List<String> added = new ArrayList<>();
        for(X509Certificate cert : certificates)
        {
            if(cert != null)
            {
                i++;
                cert.checkValidity();
                String aliasName = cert.getSubjectDN().getName();
                aliasName = aliasName == null ?  "temp_" + i : aliasName;
                String alias = containsIgnoreCase(aliasName, "CN=") ? substringBetween(aliasName, "CN=", ",") : aliasName;
                alias = alias == null ? cert.getSubjectDN().getName().replace("CN=", "") : alias;
                dicovered.add(alias);
                if(ks.getCertificateAlias(cert) == null)
                {
                    ks.setCertificateEntry(alias , cert);
                    added.add(alias);
                }
            }
        }
        Logger.getLogger().info("Copying certificates to " + source.getAbsolutePath() + " ...");
        OutputStream out = new FileOutputStream(source);
        ks.store(out, pwd.toCharArray());
        out.close();
        Logger.getLogger().info("Certiifcates in source: " + dicovered);
        Logger.getLogger().info("Certiifcates in added: " + added);
    }

    public static List<X509Certificate> parseCertificate(File f) throws Exception
    {
        List<X509Certificate> certificates = new ArrayList<>();
        String fileData = java.nio.file.Files.readAllLines(f.toPath()).stream().collect(Collectors.joining(""));
        String[] certs = fileData.split(BEGIN_CERTIFICATE);
        for(String cert : certs)
        {
            cert = cert.trim();
            if(!cert.isEmpty())
            {
                byte [] decoded = Base64.getDecoder().decode(cert.replaceAll(BEGIN_CERTIFICATE, "").replaceAll(END_CERT, ""));
                certificates.add((X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded)));
            }
        }
        return certificates;
    }

    public static boolean containsIgnoreCase(String src, String check)
    {
        if(src == null && check == null)
            return true;
        if(src != null)
        {
            if(check == null)
                return false;
            return src.toUpperCase().contains(check.toUpperCase());
        }
        return false;
    }

    public static String substringBetween(String str, String open, String close) {
        if ((str == null) || (open == null) || (close == null)) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }
}
