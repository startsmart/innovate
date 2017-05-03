package sslcertificateresolver;

import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.SSLException;

/**
 * @author Sanjeev S. [527395]
 */
public class SCRReport
{
    private String host;
    private int port;
    private SSLException sslException;
    private Throwable otherException;
    private boolean certificateIssue;
    private Set<String> certificatesAdded;
    private Set<String> certificatesDiscovered;
    private Boolean resolved;
    /**
     * @return the sslException
     */
    public SSLException getSslException()
    {
        return sslException;
    }

    /**
     * @param sslException the sslException to set
     */
    public void setSslException(SSLException sslException)
    {
        this.sslException = sslException;
    }

    public boolean hasSSLException()
    {
        return sslException != null;
    }

    /**
     * @return the otherException
     */
    public Throwable getOtherException()
    {
        return otherException;
    }

    /**
     * @param otherException the otherException to set
     */
    public void setOtherException(Throwable otherException)
    {
        this.otherException = otherException;
    }

    public boolean hasException()
    {
        return otherException != null;
    }


    /**
     * @return the certificateIssue
     */
    public boolean isCertificateIssue()
    {
        return certificateIssue;
    }

    /**
     * @param certificateIssue the certificateIssue to set
     */
    public void setCertificateIssue(boolean certificateIssue)
    {
        this.certificateIssue = certificateIssue;
    }

    /**
     * @return the certificatesAdded
     */
    public Set<String> getCertificatesAdded()
    {
        return certificatesAdded;
    }

    /**
     * @param certificatesAdded the certificatesAdded to set
     */
    public void setCertificatesAdded(Set<String> certificatesAdded)
    {
        this.certificatesAdded = certificatesAdded;
    }


    public void addedCertificate(String certificateAlias)
    {
        if(this.certificatesAdded == null)
        {
            this.certificatesAdded = new HashSet<>();
        }
        this.certificatesAdded.add(certificateAlias);
    }

    /**
     * @return the certificatesDiscovered
     */
    public Set<String> getCertificatesDiscovered()
    {
        return certificatesDiscovered;
    }

    /**
     * @param certificatesDiscovered the certificatesDiscovered to set
     */
    public void setCertificatesDiscovered(Set<String> certificatesDiscovered)
    {
        this.certificatesDiscovered = certificatesDiscovered;
    }

    public void discoverdCertificate(String certificateAlias)
    {
        if(this.certificatesDiscovered == null)
        {
            this.certificatesDiscovered = new HashSet<>();
        }
        this.certificatesDiscovered.add(certificateAlias);
    }

    public int getAddedCertificatesCount()
    {
        return certificatesAdded == null ? 0 : certificatesAdded.size();
    }

    public int getDiscoveredCertificatesCount()
    {
        return certificatesDiscovered == null ? 0 : certificatesDiscovered.size();
    }

    /**
     * @return the resolved
     */
    public Boolean isResolved()
    {
        return resolved;
    }

    /**
     * @param resolved the resolved to set
     */
    public void setResolved(boolean resolved)
    {
        this.resolved = resolved;
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("\nTested host\t:\t");
        builder.append(getHost());
        builder.append("\nTested port\t:\t");
        builder.append(getPort());
        builder.append("\nCertificate issue\t:\t");
        builder.append(isCertificateIssue());
        if(isResolved() != null)
        {
            builder.append("\nIssue Resolved\t:\t");
            builder.append(isResolved());
        }
        builder.append("\nTotal certificates discovered\t:\t");
        builder.append(getDiscoveredCertificatesCount());
        builder.append("\nTotal certificates added\t:\t");
        builder.append(getAddedCertificatesCount());
        builder.append("\nCertificates discovered\t:\t");
        builder.append(getCertificatesDiscovered());
        builder.append("\nCertificates added\t:\t");
        builder.append(getCertificatesAdded());
        if(hasSSLException())
        {
            builder.append("\nSSL Exception\t:\t");
            builder.append(getSslException().getMessage());
        }
        if(hasException())
        {
            builder.append("\nException\t:\t");
            builder.append(getOtherException().getMessage());
        }

        return builder.toString();
    }

    /**
     * @return the host
     */
    public String getHost()
    {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host)
    {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort()
    {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port)
    {
        this.port = port;
    }

}
