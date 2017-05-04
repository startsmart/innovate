package sslcertificateresolver.auto;

/**
 * @author Sanjeev S.
 */
public interface ExecutionContext
{
    public Object getData(String dataKey);
    public void interactToUser();
    public void respondUser();
    public void setData(String dataKey, Object data);
}
