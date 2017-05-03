package com.intuit.cto.wi.sslcertificateresolver.auto;

/**
 * @author Sanjeev S.[527395]
 */
public interface ExecutionContext
{
    public Object getData(String dataKey);
    public void interactToUser();
    public void respondUser();
    public void setData(String dataKey, Object data);
}
