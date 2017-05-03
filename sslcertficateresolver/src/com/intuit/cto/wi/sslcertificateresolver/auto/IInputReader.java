package com.intuit.cto.wi.sslcertificateresolver.auto;

import java.util.Set;

/**
 * @author Sanjeev S. [527395]
 */
public interface IInputReader
{
    public ExecutionContext prepareExecutionContext();
    public Set<CertificateFailureVO> getInput(ExecutionContext context);
}
