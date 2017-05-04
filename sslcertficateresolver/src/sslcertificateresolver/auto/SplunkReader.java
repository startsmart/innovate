package sslcertificateresolver.auto;

import java.util.Set;

/**
 * @author Sanjeev S.
 */
public class SplunkReader implements IInputReader
{

    @Override
    public ExecutionContext prepareExecutionContext()
    {
       throw new UnsupportedOperationException();
    }

    @Override
    public Set<CertificateFailureVO> getInput(ExecutionContext context)
    {
        throw new UnsupportedOperationException();
    }

}
