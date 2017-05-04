package sslcertificateresolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import loggerapi.Logger;

/**
 * @author Sanjeev S.
 */
public class SSLCertificateResolver
{

    private String host;
    private File trustFile;
    private char[] passphrase;
    private int port;
    private SSLSocket socket;
    private SavingTrustManager tm;
    private KeyStore ks;
    SCRReport report;

    public SSLCertificateResolver(String url) throws MalformedURLException, FileNotFoundException
    {
        this(url, null, null, null);
    }

    public SSLCertificateResolver(String url, String trustStorePath) throws MalformedURLException, FileNotFoundException
    {
        this(url, trustStorePath == null ? null : new File(trustStorePath), null);
    }

    public SSLCertificateResolver(String url, File trustStoreFile) throws MalformedURLException, FileNotFoundException
    {
        this(url, trustStoreFile, null);
    }

    public SSLCertificateResolver(String url, String trustStorePath,String password) throws MalformedURLException, FileNotFoundException
    {
        this(url, trustStorePath == null ? null : new File(trustStorePath), password.toCharArray());
    }

    public SSLCertificateResolver(String url, File trustStoreFile,char[] password) throws MalformedURLException, FileNotFoundException
    {
        this(url, trustStoreFile, password, null);
    }

    private SSLCertificateResolver(String url, File trustStoreFile,char[] password, String dummy) throws MalformedURLException, FileNotFoundException
    {
        report = new SCRReport();
        setDomain(url);
        setTrustFile(trustStoreFile);
        setPassphrase(password);
    }

    public SCRReport resolve()
    {
        try
        {
            SSLException exception = connect();
            Logger.getLogger().info("Analyzing the connection...");
            if( exception == null)
            {
                updateCertificates();
            }
            else if(isCertificateException(exception))
            {
                report.setCertificateIssue(true);
                updateCertificates();
                exception = connect();
                if( exception == null)
                {
                    report.setResolved(true);
                }
                else
                {
                   report.setResolved(false);
                   throw exception;
                }
            }
            else
            {
                report.setCertificateIssue(false);
                throw exception;
            }
        }
        catch(SSLException e)
        {
            report.setSslException(e);
            Logger.getLogger().error("SSL Exception:", e);
        }
        catch(Throwable t)
        {
            report.setOtherException(t);
            Logger.getLogger().error("Exception:", t);
        }
        return report;
    }

    private boolean isCertificateException(SSLException exception)
    {
        String message = null;
        boolean status = exception == null ? false : (message = exception.getMessage()) != null;
        status = status ? ((message.contains("Cert") || message.contains("CRL")) && message.contains("Exception")) : false;
        return status;
    }

    private void updateCertificates() throws Exception
    {
        Logger.getLogger().info("Updating certificates to trust store...");
        X509Certificate[] chain = tm.chain;
        if (chain == null)
        {
            throw new Exception("Could not obtain server certificate chain");
        }
        for (int i = 0; i < chain.length; i++)
        {
            X509Certificate cert = chain[i];
            if(cert != null)
            {
                cert.checkValidity();
                String aliasName = cert.getSubjectDN().getName();
                aliasName = aliasName == null ? host + "_" + i : aliasName;
                String alias = containsIgnoreCase(aliasName, "CN=") ? substringBetween(aliasName, "CN=", ",") : aliasName;
                alias = alias == null ? cert.getSubjectDN().getName().replace("CN=", "") : alias;
                report.discoverdCertificate(alias);
                if(ks.getCertificateAlias(cert) == null)
                {
                    ks.setCertificateEntry(alias , cert);
                    report.addedCertificate(alias);
                }
            }
        }
        Logger.getLogger().info("Copying certificates to " + trustFile.getAbsolutePath() + " ...");
        OutputStream out = new FileOutputStream(trustFile);
        ks.store(out, passphrase);
        out.close();
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


    private SSLException connect() throws Exception
    {
        try
        {
            createSocket();
            Logger.getLogger().info("Connecting to " + host + ":" + port + "...");
            socket.startHandshake();
            socket.close();
            return null;
        }
        catch (SSLException e)
        {
            return e;
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    private void createSocket() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException
    {
        Logger.getLogger().info("Creating socket to " + host + ":" + port + "...");
        InputStream in = new FileInputStream(trustFile);
        ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(in, passphrase);
        in.close();
        Logger.getLogger().info("Read trust store from " + trustFile.getAbsolutePath());
        SSLContext context = SSLContext.getInstance("TLS");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
        tm  = new SavingTrustManager(defaultTrustManager);
        context.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory factory = context.getSocketFactory();

        socket = (SSLSocket) factory.createSocket(host, port);
        socket.setSoTimeout(10000);
    }

    private void setDomain(String uri) throws MalformedURLException
    {
        URL url = new URL(uri);
        host =  url.getHost();
        port = url.getPort();
        port = port == -1 ? 443 : port;
        report.setHost(host);
        report.setPort(port);
    }

    private void setTrustFile(File trustStoreFile) throws FileNotFoundException
    {
        if (trustStoreFile == null) {
            char sep = File.separatorChar;
            File dir = new File(System.getProperty("java.home") + sep
                    + "lib" + sep + "security");
            trustFile = new File(dir, "jssecacerts");
            if (!trustFile.isFile()) {
                trustFile = new File(dir, "cacerts");
            }
        }
        else
        {
            trustFile = trustStoreFile;
        }
        if(!trustFile.isFile())
            throw new FileNotFoundException(trustFile.getAbsolutePath());
    }

    private void setPassphrase(char[] password)
    {
        passphrase = password == null ?  "changeit".toCharArray() : password;
    }

    private static class SavingTrustManager implements X509TrustManager {

        private final X509TrustManager tm;
        private X509Certificate[] chain;

        SavingTrustManager(X509TrustManager tm) {
            this.tm = tm;
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException
        {
            //Empty implementation
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException
        {
            this.chain = chain;
            tm.checkServerTrusted(chain, authType);
        }
    }
}
