package sslcertificateresolver.auto;

import sslcertificateresolver.SCRReport;

/**
 * @author Sanjeev S.
 */
public class ContextReport
{
    private CertificateFailureVO failureVO;
    private SCRReport report;
    private Throwable executionException;
    /**
     * @return the failureVO
     */
    public CertificateFailureVO getFailureVO() {
        return failureVO;
    }
    /**
     * @param failureVO the failureVO to set
     */
    public void setFailureVO(CertificateFailureVO failureVO) {
        this.failureVO = failureVO;
    }
    /**
     * @return the report
     */
    public SCRReport getReport() {
        return report;
    }
    /**
     * @param report the report to set
     */
    public void setReport(SCRReport report) {
        this.report = report;
    }
    /**
     * @return the executionException
     */
    public Throwable getExecutionException() {
        return executionException;
    }
    /**
     * @param executionException the executionException to set
     */
    public void setExecutionException(Throwable executionException) {
        this.executionException = executionException;
    }

}
