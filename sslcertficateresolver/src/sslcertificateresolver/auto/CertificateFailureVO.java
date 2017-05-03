package sslcertificateresolver.auto;

/**
 * @author Sanjeev S.[527395]
 */
public class CertificateFailureVO
{
    private String urlFailing;
    private String fiId;
    private String scriptName;
    private String cause;
    /**
     * @return the urlFailing
     */
    public String getUrlFailing() {
        return urlFailing;
    }
    /**
     * @param urlFailing the urlFailing to set
     */
    public void setUrlFailing(String urlFailing) {
        this.urlFailing = urlFailing;
    }
    /**
     * @return the fiId
     */
    public String getFiId() {
        return fiId;
    }
    /**
     * @param fiId the fiId to set
     */
    public void setFiId(String fiId) {
        this.fiId = fiId;
    }
    /**
     * @return the scriptName
     */
    public String getScriptName() {
        return scriptName;
    }
    /**
     * @param scriptName the scriptName to set
     */
    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }
    /**
     * @return the cause
     */
    public String getCause() {
        return cause;
    }
    /**
     * @param cause the cause to set
     */
    public void setCause(String cause) {
        this.cause = cause;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fiId == null) ? 0 : fiId.hashCode());
        result = prime * result
                + ((scriptName == null) ? 0 : scriptName.hashCode());
        result = prime * result
                + ((urlFailing == null) ? 0 : urlFailing.hashCode());
        return result;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CertificateFailureVO other = (CertificateFailureVO) obj;
        if (fiId == null) {
            if (other.fiId != null)
                return false;
        } else if (!fiId.equals(other.fiId))
            return false;
        if (scriptName == null) {
            if (other.scriptName != null)
                return false;
        } else if (!scriptName.equals(other.scriptName))
            return false;
        if (urlFailing == null) {
            if (other.urlFailing != null)
                return false;
        } else if (!urlFailing.equals(other.urlFailing))
            return false;
        return true;
    }


}
