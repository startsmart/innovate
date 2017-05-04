package sslcertificateresolver.auto;

import java.util.Set;

/**
 * @author Sanjeev S.
 */
public interface IInputReader
{
    public ExecutionContext prepareExecutionContext();
    public Set<CertificateFailureVO> getInput(ExecutionContext context);
}
